package com.example.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	private String orderTrackingNum;

	private String razorPayOrderId; // payment initiated

	private String email;

	private String orderStatus;

	private double totalPrice;

	private int totalQuantity;

	private String razorPayPaymentId; // payment completed

	private String invoiceUrl;

	private LocalDate deliveryDate;

	private LocalDate dateCreated;

	private LocalDate lastUpdated;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;;

}
