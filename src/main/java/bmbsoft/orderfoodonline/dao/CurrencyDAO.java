package bmbsoft.orderfoodonline.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import bmbsoft.orderfoodonline.entities.Currency;
import bmbsoft.orderfoodonline.entities.CurrencyRate;
import bmbsoft.orderfoodonline.model.shared.CurrencyResponse;

@Repository(value = "currencyDAO")
@Transactional(rollbackOn = Exception.class)
public class CurrencyDAO {
	public static final Logger logger = LoggerFactory.getLogger(CurrencyDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Boolean create(final Currency c) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return (null == session.save(c)) ? false : true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean update(final Currency c) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(c);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Currency getById(final long id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			return session.get(Currency.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}
 
	public CurrencyResponse getByDefault() {
		Session s = this.sessionFactory.getCurrentSession();
		CurrencyResponse cr = null;
		try {
			float rate = 1;
			List<Currency> cs = s.createQuery("FROM Currency c where c.isDefault= 1", Currency.class).getResultList();
			if (cs != null && cs.size() > 0) {
				cr = new CurrencyResponse();
				Currency c = cs.get(0);
				cr.setCode(c.getCode());
				cr.setCurrencyId(c.getCurrencyId());
				cr.setName(c.getName());
				cr.setRoundDecimal(c.getRoundDecimal());
				cr.setSymbolLeft(c.getSymbolLeft());
				cr.setSymbolRight(c.getSymbolRight());

				Set<CurrencyRate> scr = c.getCurrencyRates();
				if (scr != null && scr.size() > 0) {
					for (CurrencyRate cra : scr) {
						rate = cra.getRate();
						break;
					}
				}
				cr.setRate(rate);
			}
			return cr;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public void delete(final Currency Currency) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(Currency);
	}

	public List<Currency> getAll() {
		Session s = this.sessionFactory.getCurrentSession();
		try {
			List<Currency> cs = s.createQuery("FROM Currency", Currency.class).getResultList();
			return cs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

}
