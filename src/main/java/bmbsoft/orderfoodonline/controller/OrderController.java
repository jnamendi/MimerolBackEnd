package bmbsoft.orderfoodonline.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.entities.OrderHistory;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.model.OrderResponse;
import bmbsoft.orderfoodonline.model.OrderViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.model.shared.OrderLiteRequest;
import bmbsoft.orderfoodonline.model.shared.PaymentRequest;
import bmbsoft.orderfoodonline.model.shared.PaymentResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import bmbsoft.orderfoodonline.util.Constant.PaymentMethod;
import jlibs.core.util.regex.TemplateMatcher;

@RestController
@CrossOrigin
public class OrderController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	@Autowired
	CountryService countryService;

	@Autowired
	OrderPaymentService ops;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ContentEmailService ce;

	@Autowired
	private UserService userService;

	Gson mapper = new Gson();

	@RequestMapping(value = "/api/order/getAll/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "orderCode", required = false) String orderCode,
			@RequestParam(value = "restaurantName", required = false) String restaurantName,
			@RequestParam(value = "status", required = false) Integer status) {
		ResponseGetPaging responseGetPaging = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.getAll)) {
				responseGetPaging.setStatus(7);
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGetPaging.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
			}
			responseGetPaging = orderService.getAll(pageIndex, pageSize, orderCode, restaurantName, status);
			if (responseGetPaging == null) {
				responseGetPaging.setStatus(0);
				responseGetPaging.setMessage("Could not found items.");
				responseGetPaging.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
			}
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGetPaging.setStatus(1);
			responseGetPaging.setMessage(e.toString());
			responseGetPaging.setContent(null);
			return new ResponseEntity<ResponseGetPaging>(responseGetPaging, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/order/getById/{orderId}/{orderCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long orderId, @PathVariable String orderCode) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			OrderViewModel c = orderService.getById(orderId, orderCode);
			if (c == null) {
				rs.setStatus(0);
				rs.setMessage("Order not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/order/get-full-info-by-id/{orderId}/{orderCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getFullInfoById(@PathVariable long orderId, @PathVariable String orderCode) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.getFullInfo)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			OrderResponse c = orderService.getFullById(orderId, orderCode);
			if (c == null) {
				rs.setStatus(0);
				rs.setMessage("Order not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/order/payment", method = RequestMethod.POST)
	public ResponseEntity<?> payment(@RequestBody @Validated PaymentRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Order, Constant.Action.payment)) {
//				rs.setStatus(7);
//				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				rs.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
//			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			// TODO validate

			if (req.getUserId() == null) {
				boolean b = userService.checkExistEmail(req.getEmail(), Constant.Provider.NORMAL.getValue(),
						Constant.AccountType.Anonymous.getValue());
				if (b) {
					rs.setStatus(2);
					rs.setMessage("Exist an email.");
					rs.setErrorType(Constant.ErrorTypeCommon.EMAIL_EXISTS);
					return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
				}
			}

			if(req.getAddressId() != null) {
				AddressViewModel model = addressService.getById(req.getAddressId());
				req.setAddress(model.getAddress() == null ? "" : model.getAddress());
				req.setDistrict(model.getDistrict() == null ? "" : model.getDistrict());
				req.setCity(model.getCity() == null ? "" : model.getCity());
				req.setAddressDesc(model.getAddressDesc() == null ? "" : model.getCity());
			}

			PaymentResponse ps = ops.create(req);
			if (ps != null && ps.getErrMsg().isEmpty()) {
				// send email
				try {
					logger.info("------------Send mail -- payment");
					// String appUrl = request.getScheme() + "://" + request.getServerName();
					String appUrl = environment.getProperty("fontend.url");
					String emailFrom = environment.getProperty("email.from");
					String siteTitle = environment.getProperty("site.title");
					String displayEmailName = environment.getProperty("display.email.name");

					ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.Payment.getValue(),
							req.getLanguageCode());
					if (cm != null) {
						TemplateMatcher matcher = new TemplateMatcher("${", "}");
						Map<String, String> vars = new HashMap<String, String>();
						vars.put("orderCode", ps.getorderCode() == null ? "" : ps.getorderCode());
						vars.put("restaurantName", ps.getName() == null ? "" : ps.getName());
						vars.put("restaurantAddress", ps.getAddressLine() == null ? "" : ps.getAddressLine());
						vars.put("restaurantPhone", ps.getPhone1() == null ? "" : ps.getPhone1());
						vars.put("deliveryCost", req.getDeliveryCost() == null ? "" : req.getDeliveryCost().toString());
						vars.put("totalPrice", req.getOrderItem().getTotalPrice() == null ? "" : req.getOrderItem().getTotalPrice().toString());
						vars.put("userName", req.getName() == null ? "" : req.getName());
						vars.put("userAddress", req.getAddress() == null ? "" : req.getAddress());
						vars.put("userDistrict", req.getDistrict() == null ? "" : req.getDistrict());
						vars.put("userCity", req.getCity() == null ? "" : req.getCity());
						vars.put("userNumber",
								req.getNumber() != null && !req.getNumber().isEmpty() ? req.getNumber() : "");
						vars.put("deliveryTime", req.getTime() == null ? "" : req.getTime());
						vars.put("remarks", req.getRemarks() == null ? "" : req.getRemarks());
						vars.put("symbolLeft", req.getSymbolLeft() == null && req.getSymbolLeft().isEmpty() ? ""
								: req.getSymbolLeft());
						vars.put("paymentType", PaymentMethod.valueOf(req.getPaymentType()).toString());

						StringBuilder sb = new StringBuilder();
						if (req.getOrderItem().getOrderItemsRequest() != null
								&& req.getOrderItem().getOrderItemsRequest().size() > 0) {

							req.getOrderItem().getOrderItemsRequest().forEach(o -> {
								sb.append("<tr style='width:100%;font-family:Arial;font-size:13px'>");
								sb.append("<td style='width:62px;font-family:Arial;font-size:13px' valign='top'>");
								sb.append("<span style='font-family:Arial;font-size:13px'>" + o.getQuantity()
										+ "</span>");
								sb.append("</td>");
								sb.append("<td style='font-family:Arial;font-size:13px'>");
								sb.append("<span style='font-family:Arial;font-size:13px'>" + o.getMenuItemName()
										+ "</span>");
								sb.append("<br>");
								sb.append("<span style='font-family:Arial;font-size:11px;font-style:italic'></span>");
								sb.append("</td>");
								sb.append(
										"<td style='width:70px;text-align:right;font-family:Arial;font-size:13px' valign='top'>");
								sb.append("<span style='font-family:Arial;font-size:13px'>" + o.getTotalPrice() + " "
										+ req.getSymbolLeft() + "</span>");
								sb.append("</td>");
								sb.append("</tr>");
							});

						}
						vars.put("orderLineItems", sb.toString());

						String body = matcher.replace(cm.getBody(), vars);

						// title
						TemplateMatcher title = new TemplateMatcher("${", "}");
						Map<String, String> t = new HashMap<String, String>();
						t.put("orderCode", ps.getorderCode() == null ? "" : ps.getorderCode());
						t.put("restaurantName", ps.getName() == null ? "" : ps.getName());
						String trpc = title.replace(cm.getSubject(), t);

						// receive voucher

						Executors.newSingleThreadExecutor().execute(new Runnable() {
							public void run() {
								try {
									logger.info("------------send to " + req.getEmail());
									emailService.sendBccMessage(emailFrom, req.getEmail(), cm.getBcc(), trpc, body,
											displayEmailName);

									// email voucher
									if (req.isReceiveVoucher()) {
										ContentEmaiLViewModel pro = ce.getByType(
												Constant.EmailType.Promotion.getValue(), req.getLanguageCode());
										if (pro != null) {
											logger.info("------------send promotion to " + req.getEmail());
											emailService.sendMessage(emailFrom, req.getEmail(), pro.getSubject(),
													pro.getBody(), displayEmailName);
										}
									}
								} catch (MessagingException | IOException e) {
									logger.error(e.toString());
								}
							}
						});
					}
					rs.setStatus(0);
					rs.setMessage("OK");
					rs.setContent(new HashMap() {
						{
							put("orderId", ps.getOrderId() == null ? "" : ps.getOrderId());
							put("invoiceCode", ps.getorderCode() == null ? "" : ps.getorderCode());
						}
					});

					return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);

				} catch (Exception e) {
					logger.error(e.toString());
				}

				rs.setStatus(ps.getStatusCode());
				rs.setMessage(ps.getErrMsg());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			rs.setStatus(ps.getStatusCode());
			rs.setMessage(ps.getErrMsg());
			rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/order/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			Order c = orderService.getBaseById(id);
			if (null == c) {
				rs.setStatus(0);
				rs.setMessage("Order not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			if (orderService.delete(c)) {
				rs.setStatus(0);
				rs.setMessage("delete success");

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/order/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Ids is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				Order c = orderService.getBaseById(id);
				if (c != null) {
					orderService.delete(c);
				} else {
					s.append("Could not found item: " + id);
				}
			}
			if (s.toString().isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Delete success.");
				rs.setErrorType(Constant.ErrorTypeCommon.OK);
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(6);
			rs.setMessage(s.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.DELETE_MANY);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/order/getByUser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByUser(@PathVariable Long userId) {
		ResponseGet rs = new ResponseGet();
		List<OrderResponse> c = null;
		try {
//			// permission
//			if (!permission(Constant.Module.Order, Constant.Action.getByUser)) {
//				rs.setStatus(7);
//				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				rs.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
//			}
			if (userId == null) {
				rs.setStatus(7);
				rs.setMessage("userId is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			c = this.orderService.getOderByUser(userId);
			if (null == c || c.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setContent(null);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/order/get-by-ower/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByOwer(@PathVariable Long userId) {
		ResponseGet rs = new ResponseGet();
		List<OrderResponse> c = null;
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.getByOwner)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (userId == null) {
				rs.setStatus(7);
				rs.setMessage("userId is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			c = this.orderService.getOderByUser(userId);
			if (null == c || c.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setContent(null);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/order/get-order-payment/{orderId}/{orderCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getByOrderId(@PathVariable @Validated Long orderId,
			@PathVariable @Validated String orderCode) {
		ResponseGet rs = new ResponseGet();
		try {
//			// permission
//			if (!permission(Constant.Module.Order, Constant.Action.getOrderPayment)) {
//				rs.setStatus(7);
//				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
//				rs.setMessage("Access Denied!");
//				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
//			}
			if (orderId == null) {
				rs.setStatus(7);
				rs.setMessage("orderId is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (orderCode.isEmpty()) {
				rs.setStatus(7);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			PaymentResponse ps = this.orderService.getOderPaymentById(orderId, orderCode);
			if (ps == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			rs.setStatus(0);
			rs.setContent(ps);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setContent(null);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/order/order-restaurant-review/{orderId}/{orderCode}", method = RequestMethod.GET)
	public ResponseEntity<?> orderRestaurantReview(@PathVariable @Validated Long orderId,
			@PathVariable @Validated String orderCode) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.orderRestaurantReview)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (orderId == null) {
				rs.setStatus(7);
				rs.setMessage("orderId is field required.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (orderCode.isEmpty()) {
				rs.setStatus(7);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			PaymentResponse ps = this.orderService.getOderPaymentById(orderId, orderCode);
			if (ps == null) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			rs.setStatus(0);
			rs.setContent(ps);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setContent(null);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/order/update-order-to", method = RequestMethod.POST)
	public ResponseEntity<?> updateDelivery(@RequestBody @Validated OrderLiteRequest req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.updateOrderBy)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			// get order
			OrderViewModel vm = orderService.getById(req.getOrderId(), req.getOrderCode());
			if (vm == null || vm.getStatus() == Constant.Order.Complete.getValue()) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			// get
			String fw = req.getOrderId() + "_" + req.getOrderCode();
			boolean isTrue = CommonHelper.CheckPw(fw, vm.getCheckSum());
			if (!isTrue) {
				rs.setStatus(0);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			Order o = vm.getOrder();
			if (req.getStatus() == Constant.Order.Done.getValue()) {
				o.setStatus(Constant.Order.Complete.getValue());
			} else {
				o.setStatus(req.getStatus());
			}

			boolean so = orderService.updateBase(o);
			// create history

			OrderHistory oh = new OrderHistory();
			vm.setOrder(null);
			String obj = mapper.toJson(vm);
			oh.setOrderObj(obj);
			oh.setCreatedDate(new Date());

			boolean sh = orderService.saveHis(oh);

			if (so && sh) {
				// send mail
				if (req.getStatus() == Constant.Order.Deliveried.getValue() && !vm.getEmail().isEmpty()) {
					try {
						logger.info("------------Send mail -- coment");
						// String appUrl = request.getScheme() + "://" + request.getServerName();
						String appUrl = environment.getProperty("fontend.url");
						String emailFrom = environment.getProperty("email.from");
						String siteTitle = environment.getProperty("site.title");
						String displayEmailName = environment.getProperty("display.email.name");

						Executors.newSingleThreadExecutor().execute(new Runnable() {
							public void run() {
								try {
									ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.Delivered.getValue(),
											req.getLanguageCode());
									if (cm != null) {
										TemplateMatcher matcher = new TemplateMatcher("${", "}");
										Map<String, String> map = new HashMap<String, String>();
										map.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										map.put("restaurantName", vm.getRestaurantName() == null ? "" : vm.getRestaurantName());
										map.put("openTime", vm.getOpenTime() == null ? "" : vm.getOpenTime());
										map.put("closeTime", vm.getCloseTime() == null ? "" : vm.getCloseTime());

										String body = matcher.replace(cm.getBody(), map);

										// title
										TemplateMatcher title = new TemplateMatcher("${", "}");
										Map<String, String> t = new HashMap<String, String>();
										t.put("orderCode", vm.getOrderCode());
										String trpc = title.replace(cm.getSubject(), t);

										logger.info("------------send delivered to " + vm.getEmail());
										emailService.sendMessage(emailFrom, vm.getEmail(), trpc, body,
												displayEmailName);
									}

									// review

									ContentEmaiLViewModel review = ce.getByType(Constant.EmailType.Review.getValue(),
											req.getLanguageCode());
									if (review != null) {
										TemplateMatcher matcher = new TemplateMatcher("${", "}");
										Map<String, String> map = new HashMap<String, String>();
										map.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										map.put("restaurantName", vm.getRestaurantName() == null ? "" : vm.getRestaurantName());
										map.put("fullName", vm.getUserName() == null ? "" : vm.getUserName());

										String body = matcher.replace(review.getBody(), map);

										// title
										TemplateMatcher title = new TemplateMatcher("${", "}");
										Map<String, String> t = new HashMap<String, String>();
										t.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										t.put("restaurantName", vm.getRestaurantName() == null ? "" : vm.getRestaurantName());
										String trpc = title.replace(review.getSubject(), t);

										logger.info("------------send review to " + vm.getEmail());
										emailService.sendMessage(emailFrom, vm.getEmail(), trpc, body,
												displayEmailName);
									}

								} catch (MessagingException | IOException e) {
									logger.error(e.toString());
								}
							}
						});

					} catch (Exception e) {
						logger.error(e.toString());
					}
				}
				// done

				if (req.getStatus() == Constant.Order.Done.getValue() && !vm.getEmail().isEmpty()) {
					try {
						logger.info("------------Send mail -- coment");
						// String appUrl = request.getScheme() + "://" + request.getServerName();
						String appUrl = environment.getProperty("fontend.url");
						String emailFrom = environment.getProperty("email.from");
						String siteTitle = environment.getProperty("site.title");
						String displayEmailName = environment.getProperty("display.email.name");

						Executors.newSingleThreadExecutor().execute(new Runnable() {
							public void run() {
								try {
									ContentEmaiLViewModel cm = ce.getByType(Constant.EmailType.Delivered.getValue(),
											req.getLanguageCode());
									if (cm != null) {
										TemplateMatcher matcher = new TemplateMatcher("${", "}");
										Map<String, String> map = new HashMap<String, String>();
										map.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										map.put("restaurantName", vm.getRestaurantName() == null ? "" : vm.getRestaurantName());
										map.put("openTime", vm.getOpenTime() == null ? "" : vm.getOpenTime());
										map.put("closeTime", vm.getCloseTime() == null ? "" : vm.getCloseTime());

										String body = matcher.replace(cm.getBody(), map);

										// title
										TemplateMatcher title = new TemplateMatcher("${", "}");
										Map<String, String> t = new HashMap<String, String>();
										t.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										String trpc = title.replace(cm.getSubject(), t);

										logger.info("------------send delivered to " + vm.getEmail());
										emailService.sendMessage(emailFrom, vm.getEmail(), trpc, body,
												displayEmailName);
									}

									// review

									ContentEmaiLViewModel review = ce.getByType(Constant.EmailType.Review.getValue(),
											req.getLanguageCode());
									if (review != null) {
										TemplateMatcher matcher = new TemplateMatcher("${", "}");
										Map<String, String> map = new HashMap<String, String>();
										map.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										map.put("restaurantName", vm.getRestaurantName() == null ? "" : vm.getRestaurantName());
										map.put("fullName", vm.getUserName() == null ? "" : vm.getUserName());

										String body = matcher.replace(review.getBody(), map);

										// title
										TemplateMatcher title = new TemplateMatcher("${", "}");
										Map<String, String> t = new HashMap<String, String>();
										t.put("orderCode", vm.getOrderCode() == null ? "" : vm.getOrderCode());
										t.put("restaurantName", vm.getRestaurantName() == null ? "" : vm.getRestaurantName());
										String trpc = title.replace(review.getSubject(), t);

										logger.info("------------send review to " + vm.getEmail());
										emailService.sendMessage(emailFrom, vm.getEmail(), trpc, body,
												displayEmailName);
									}

								} catch (MessagingException | IOException e) {
									logger.error(e.toString());
								}
							}
						});

					} catch (Exception e) {
						logger.error(e.toString());
					}
				}

				rs.setStatus(0);
				rs.setContent("");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			rs.setStatus(5);
			rs.setMessage("Error when process the data");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setContent(null);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/order/get-by-restaurant/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "status", required = false) Long status,
			@RequestParam(value = "restaurantId", required = true) Long restaurantId) {
		ResponseGetPaging rs = new ResponseGetPaging();
		try {
			// permission
			if (!permission(Constant.Module.Order, Constant.Action.getByRestaurant)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			rs = this.orderService.getByRestaurant(pageIndex, pageSize, restaurantId, status);
			if (rs.getStatus() != 0)
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
		}
	}

}