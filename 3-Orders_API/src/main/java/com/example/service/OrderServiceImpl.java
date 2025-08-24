package com.example.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.AddressDto;
import com.example.dto.CustomerDto;
import com.example.dto.OrderDto;
import com.example.dto.OrderItemDto;
import com.example.dto.PaymentCallBackDto;
import com.example.entities.Address;
import com.example.entities.Customer;
import com.example.entities.Order;
import com.example.entities.OrderItem;
import com.example.repo.AddressRepo;
import com.example.repo.CustomerRepo;
import com.example.repo.OrderItemRepo;
import com.example.repo.OrderRepo;
import com.example.request.PurchaseOrderRequest;
import com.example.response.PurchaseOrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private RazorPayService razorPayService;

	@Override
	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderRequest) {
		OrderDto orderDto = orderRequest.getOrderDto();
		AddressDto addressDto = orderRequest.getAddressdto();
		CustomerDto customerDto = orderRequest.getCustomerDto();
		List<OrderItemDto> orderItemsDto = orderRequest.getOrderItemsDto();

		Customer c = customerRepo.findByEmail(customerDto.getEmail());

		// check customer presence in DB & save if required
		if (c == null) {
			c = new Customer();
			c.setCustomerName(customerDto.getCustomerName());
			c.setEmail(customerDto.getEmail());
			c.setPhoneNo(customerDto.getPhoneNo());
			customerRepo.save(c);
		}

		// save address
		Address address = new Address();
		address.setHouseNum(addressDto.getHouseNum());
		address.setStreet(addressDto.getStreet());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setZipCode(addressDto.getZipCode());
		address.setCustomer(c);
		addressRepo.save(address);

		// save order

		Order newOrder = new Order();
		String orderTrackingNumber = generateOrderTrackingNumber();
		newOrder.setOrderTrackingNum(orderTrackingNumber);
		
		// create razoray order & get order details
		com.razorpay.Order paymentOrder = razorPayService.createPaymentOrder(orderDto.getTotalPrice());
		
		newOrder.setRazorPayOrderId(paymentOrder.get("id"));
		newOrder.setOrderStatus(paymentOrder.get("status"));
		newOrder.setTotalPrice(orderDto.getTotalPrice());
		newOrder.setTotalQuantity(orderDto.getTotalQuantity());
		newOrder.setEmail(c.getEmail());

		newOrder.setCustomer(c); // association mapping
		newOrder.setAddress(address); // association mapping

		orderRepo.save(newOrder);

		// save order items
		for (OrderItemDto itemDto : orderItemsDto) {
			OrderItem item = new OrderItem();
			BeanUtils.copyProperties(itemDto, item);
			item.setOrder(newOrder);
			orderItemRepo.save(item);
		}

		// prepare & return reaponse
		/*
		 * PurchaseOrderResponse response = new PurchaseOrderResponse();
		 * response.setRazorPayOrderId(paymentOrder.get("id"));
		 * response.setOrderTrackingNumber(orderTrackingNumber);
		 * response.setOrderStatus(orderTrackingNumber);
		 */

		return PurchaseOrderResponse.builder()
				                    .razorPayOrderId(paymentOrder.get("id"))
				                    .orderTrackingNumber(orderTrackingNumber)
				                    .orderStatus(paymentOrder.get("status"))
				                    .build();
	}

	@Override
	public List<Order> getOrdersByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrderResponse updateOrder(PaymentCallBackDto paymentCallBackDTo) {
		// TODO Auto-generated method stub
		return null;
	}

	private String generateOrderTrackingNumber() {

		return null;
	}

}
