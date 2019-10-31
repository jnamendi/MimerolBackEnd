package bmbsoft.orderfoodonline.dao;

import bmbsoft.orderfoodonline.entities.*;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.model.shared.*;
import bmbsoft.orderfoodonline.service.*;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import bmbsoft.orderfoodonline.util.RandomStringHelper;
import com.google.gson.Gson;
import jlibs.core.util.regex.TemplateMatcher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Repository(value = "orderPaymentDAO")
@Transactional(rollbackOn = Exception.class)
public class OrderPaymentDAO {
	public static final Logger logger = LoggerFactory.getLogger(OrderPaymentDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDAO ud;

	@Autowired
	private MenuItemDAO mid;

	@Autowired
	private ExtraItemDAO eid;

	@Autowired
	VoucherDAO v;

	@Autowired
	public Environment environment;

	@Autowired
	private ContentEmailService ce;

	@Autowired
	private EmailService emailService;

	@Autowired
	private RoleService rs;

	@Autowired
	private AddressService ads;

	@Autowired
	private ZoneService zs;

	@Autowired
	private MenuService menuService;

	private Gson mapper = new Gson();

	private boolean isOk = true;
	private String msg = "";
	private String emailOwner = "";

	public PaymentResponse create(PaymentRequest req) {
		Session session = this.sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		PaymentResponse ps = new PaymentResponse();
		try {
			// check restaurant

			Restaurant r = session.get(Restaurant.class, req.getRestaurantId());
			if (r == null) {
				ps.setStatusCode(7);
				ps.setErrMsg("Restaurant not exist.");
				return ps;
			}

//			if (!CommonHelper.checkBetweenTime(r.getOpenTime(), r.getCloseTime())) {
//				ps.setStatusCode(7);
//				ps.setErrMsg("Restaurant is closed.");
//				return ps;
//			}

			ps.setName(r.getName());
			ps.setPhone1(r.getPhone1());
			ps.setPhone2(r.getPhone2());
			ps.setDileverTime(r.getEstimateDeliveryTime());
			ps.setAddressLine(r.getAddressLine());
			ps.setImageUrl(r.getImageUrl());
			ps.setLatitude(r.getLatitude());
			ps.setLongitude(r.getLongitude());
			ps.setRestaurantId(r.getRestaurantId());

			if (r.getUserRestaurants() != null && r.getUserRestaurants().size() > 0) {
				List<UserRestaurant> lst = new ArrayList<>(r.getUserRestaurants());
				if (lst.size() > 0) {
					emailOwner = lst.get(0).getUser().getEmail();
				}
			}

			User u;
			boolean isExistUser = false;
			RandomStringHelper gen = new RandomStringHelper(8, ThreadLocalRandom.current());
			String token = gen.nextString();
			String hpw = CommonHelper.HasPw(token);
			if (req.getUserId() == null) {
				//check email
				User user = ud.getUserByEmailAndStatus(req.getEmail(), Constant.Status.Publish.getValue());
				isExistUser = user != null;
				if(isExistUser) {
					u = user;
				} else {
					u = new User();

					u.setUserHash(hpw);
					u.setUserSalt(hpw );
					u.setProvider(Constant.Provider.NORMAL.getValue());
					u.setUserName(req.getName());
					u.setFullName(req.getName());
					u.setPhone(req.getNumber());
					u.setEmail(req.getEmail());
					u.setCreatedDate(new Date());
					u.setStatus(Constant.Status.Publish.getValue());
					u.setReceiveNewsletter(true);
					u.setAccountType(Constant.AccountType.Anonymous.getValue());
					u.setIsLock(false);

					session.save(u);

					// assign role guest
					UserRole ur = new UserRole();
					ur.setUser(u);
					ur.setRole(rs.getRoleByCode(Constant.ROLE_CODE_GUEST));

					session.save(ur);
					// create user anomyus
				}

			} else {
				// check user
				u = ud.findById(req.getUserId());
			}

			if (u == null) {
				ps.setStatusCode(7);
				ps.setErrMsg("User not exist.");
				return ps;
			}

			// save order
			Order o = new Order();
			gen = new RandomStringHelper(8, ThreadLocalRandom.current());
			String uu = gen.nextString();

			o.setCurrencyCode(req.getCurrencyCode());
			o.setOrderDate(new Date());
			o.setUser(u);
			o.setRestaurant(r);
			o.setStatus(Constant.Order.New.getValue());
			o.setOrderCode(uu.toUpperCase());
			o.setRestaurantName(r.getName());
			o.setPaymentWith(req.getPaymentWith());
			o.setDiscount(req.getDiscount());
			o.setChargeFee(calculateChargeForOrder(req.getOrderItem().getOrderItemsRequest(), req.getDiscount()));
			o.setDeliveryCost(req.getDeliveryCost());

			if (req.getOrderItem() == null) {
				ps.setStatusCode(7);
				ps.setErrMsg("Menu item not exist.");
				return ps;
			}
			OrderItem oi = req.getOrderItem();
			o.setTaxTotal(oi.getTaxTotal());
			o.setTotalPrice(oi.getTotalPrice());

			String json1 = mapper.toJson(req);
			o.setOrderReq(json1);
			session.save(o);

			// checksum
			String cs = o.getOrderId() + "_" + o.getOrderCode();
			o.setCheckSum(CommonHelper.HasPw(cs));
			session.update(o);

			// save orderline item
			if (oi.getOrderItemsRequest() != null && oi.getOrderItemsRequest().size() > 0) {
				oi.getOrderItemsRequest().forEach(ol -> {
					OrderLineItem oli = new OrderLineItem();
					oli.setCreatedDate(new Date());
					oli.setOrder(o);
					MenuItem mi = mid.getById(ol.getMenuItemId());
					if (mi != null) {
						oli.setMenuItem(mi);
					} else {
						isOk = false;
						msg = "Menu item is not exist.";
					}
					oli.setUnitPrice(ol.getPriceRate());
					oli.setQuantity(ol.getQuantity());
					oli.setStatus(Constant.Status.Publish.getValue());
					oli.setMenuItemName(ol.getMenuItemName());
					// oli.setDiscountTotal(ol.getDiscount());
					oli.setTotal(ol.getTotalPrice());

					session.save(oli);

					// save extraItem
					if (ol.getMenuExraItems() != null && ol.getMenuExraItems().size() > 0) {
						ol.getMenuExraItems().forEach(ex -> {
							MenuExtraItem extraItem = eid.getByExtraItemId(ex.getMenuExtraItemId());
							if (extraItem != null) {
								if (ex.getExtraitems() != null && ex.getExtraitems().size() > 0) {
									ex.getExtraitems().forEach(eti -> {
										ExtraItem exi = eid.getById(eti.getExtraItemId());

										if (exi != null) {
											OrderExtraItem ei = new OrderExtraItem();
											ei.setMenuItemId(Objects.requireNonNull(mi).getMenuItemId());
											ei.setMenuExtraItemId(extraItem.getMenuExtraItemId());
											ei.setMenuExtraItemId(exi.getExtraItemId());
											ei.setTotalPrice(eti.getPriceRate());
											ei.setUnitPrice(eti.getPrice());

											session.save(ei);
										} else {
											isOk = false;
											msg = "extra item is not exist.";
										}

									});
								}
							}
//							else {
//								isOk = false;
//								msg = "Menu extra item is not exist.";
//							}
						});
					}
				});
			}

			// save order info
			OrderInfo oin = new OrderInfo();

			// save address
			if (req.getAddressId() != null) {
				// get address
				Address add = ads.getBaseById(req.getAddressId());
				if (add != null) {
					oin.setAddress(add.getAddress());
					Zone z = add.getZone();
					if (z != null) {
						oin.setZone(z.getName());
						oin.setDistrict(z.getDistrict().getName());
						City c = z.getDistrict().getCity();
						if (c != null) {
							oin.setCity(c.getCityName());
						}
					}
				}
			} else {
				// create address profile
				Address address = new Address();
				Zone z = zs.getBaseById(req.getZoneId());
				if (z == null) {
					msg = "Zone is null.";
					isOk = false;
				} else {
					address.setDistrict(z.getDistrict());
					oin.setDistrict(z.getDistrict().getName());
					oin.setZone(z.getName());
				}
				address.setCreatedDate(new Date());
				address.setUser(u);
				// address.setPhoneNumber(req.getP);
				// address.setWard(req.getWard());
				address.setAddress(req.getAddress());
				address.setIsStatus(Constant.Status.Publish.getValue());
				address.setAddressDesc(req.getAddressDesc());

				session.save(address);
				// ass
				oin.setAddress(req.getAddress());
				oin.setCity(req.getCity());
			}

			oin.setCompanyName(req.getCompanyName());
			oin.setInfoEmail(req.getEmail());
			oin.setInfoName(req.getName());
			oin.setInfoNumber(req.getNumber());
			oin.setOrder(o);
			oin.setRemark(req.getRemarks());
			oin.setTime(req.getTime());
			oin.setAddressDesc(req.getAddressDesc());

			session.save(oin);

			// save payment: TODO check payment: visa, paypal
			OrderPayment op = new OrderPayment();
			op.setCreatedDate(new Date());
			op.setOrder(o);
			op.setOrderPaymentType(req.getPaymentType());
			session.save(op);

			// voucher
			if (req.getVoucherId() != null && req.getVoucherCode() != null && req.getVoucherCode().isEmpty()) {
				boolean vc = v.getByIdAndCode(req.getVoucherId(), req.getVoucherCode());
				if (!vc) {
					isOk = false;
					msg = "Voucher is not exist.";
				}

				// update
				String qrc = "UPDATE voucher_lineitem set status=:st WHERE code=:code and voucher_id=:vId";
				int n = session.createNativeQuery(qrc).setParameter("st", Constant.Status.Used.getValue())
						.setParameter("vId", req.getVoucherId()).setParameter("code", req.getVoucherCode())
						.executeUpdate();

			}

			if (isOk) {
				t.commit();
				ps.setErrMsg("");
				ps.setOrderCode(o.getOrderCode());
				ps.setOrderId(o.getOrderId());

				// send mail
				if (req.getUserId() == null && !isExistUser && req.getLanguageCode() != null && !req.getLanguageCode().isEmpty()) {
					try {
						String appUrl = environment.getProperty("frontend.url");
						String emailFrom = environment.getProperty("email.from");
						String siteTitle = environment.getProperty("site.title");
						String displayEmailName = environment.getProperty("display.email.name");

						ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.CreateNewUser.getValue(),
								req.getLanguageCode());
						if (cm != null) {
							TemplateMatcher matcher = new TemplateMatcher("${", "}");
							Map<String, String> vars = new HashMap<String, String>();
							vars.put("url", appUrl);
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
							String str_date = dateFormat.format(new Date());
							vars.put("year", str_date);
							vars.put("fullName", u.getFullName().isEmpty() ? u.getUserName() : u.getFullName());
							vars.put("userName", u.getUserName());
							vars.put("password", token);
							vars.put("siteName", siteTitle);

							String body = matcher.replace(cm.getBody(), vars);

							String trpc = cm.getSubject();
							Executors.newSingleThreadExecutor().execute(new Runnable() {
								public void run() {
									try {
										emailService.sendCcMessage(emailFrom, req.getEmail(), emailOwner, trpc, body,
												displayEmailName);
									} catch (MessagingException | IOException e) {
										logger.error(e.toString());
									}
								}
							});
						}

					} catch (Exception e) {
						logger.error(e.toString());
					}
				}

				return ps;

			}
			ps.setErrMsg(msg);
			return ps;
		} catch (Exception e) {
			logger.error(e.toString());
			t.rollback();
			ps.setStatusCode(1);
			ps.setErrMsg(e.toString());
			return ps;
		} finally {
			session.close();
		}
	}

	public boolean update(final Order Order) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(Order);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Order getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(Order.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public void delete(final Order Order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Order);
	}

	public List<Order> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			return s.createQuery("FROM Order", Order.class).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private double calculateChargeForOrder(List<MenuItemLiteResponse> liteResponses, Long discount) {
		double charge = 0d;
		if(liteResponses != null && !liteResponses.isEmpty()) {
			for (MenuItemLiteResponse item : liteResponses) {
				Double rate = menuService.getById(item.getMenuId()).getRate();
				Double priceOfItem = item.getPriceOriginal();
				if(item.getMenuExraItems() != null && !item.getMenuExraItems().isEmpty()) {
					for (MenuExtraItemLiteResponse menuItem: item.getMenuExraItems()) {
						if(menuItem.getExtraitems() != null && !menuItem.getExtraitems().isEmpty()) {
							for(ExtraItemLiteResponse extraItemLiteResponse : menuItem.getExtraitems())	priceOfItem += extraItemLiteResponse.getPrice();
						}
					}
				}
				charge += priceOfItem * item.getQuantity() * rate / 100d;
			}
		}
		if(discount > 0) {
			charge = charge * (100d - discount.doubleValue()) / 100d;
		}
		return charge;
	}

}
