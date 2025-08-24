package com.example.service;

import java.util.List;

import com.example.dto.PaymentCallBackDto;
import com.example.entities.Order;
import com.example.request.PurchaseOrderRequest;
import com.example.response.PurchaseOrderResponse;

public interface OrderService {
	
	public PurchaseOrderResponse createOrder (PurchaseOrderRequest orderRequest);
	
	public List<Order> getOrdersByEmail(String email);
	
	public PurchaseOrderResponse updateOrder(PaymentCallBackDto paymentCallBackDTo);
	
	

}
