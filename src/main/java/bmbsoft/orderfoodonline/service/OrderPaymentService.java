package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.OrderPaymentDAO;
import bmbsoft.orderfoodonline.model.shared.PaymentRequest;
import bmbsoft.orderfoodonline.model.shared.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderPaymentService {
	public static final Logger logger = LoggerFactory.getLogger(OrderPaymentService.class);

	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
 
	@Transactional
	@Async
	public PaymentResponse create(final PaymentRequest req) {
		return orderPaymentDAO.create(req);
	}
 
}
