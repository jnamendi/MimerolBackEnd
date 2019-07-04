package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.OrderDAO;
import bmbsoft.orderfoodonline.dao.OrderPaymentDAO;
import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.model.OrderViewModel;
import bmbsoft.orderfoodonline.model.shared.OrderRequest;
import bmbsoft.orderfoodonline.model.shared.PaymentRequest;
import bmbsoft.orderfoodonline.model.shared.PaymentResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.util.Constant;

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
