package com.example.jpaamarchdemo.controller;

import com.example.jpaamarchdemo.model.Order;
import com.example.jpaamarchdemo.model.PlaceOrderRequest;
import com.example.jpaamarchdemo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/order/action/get-by-id")
	public ResponseEntity<?> getOrderById(@RequestParam String orderId) {
		Order order = orderService.getOrderByOrderId(orderId);
		if (order != null) {
			return ResponseEntity.ok(order);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
		}
	}

	@GetMapping(value = "/order/action/list-orders")
	public ResponseEntity<?> listOrders() {
		List<Order> orderList = orderService.listOrders();
		return ResponseEntity.ok(orderList);
	}
	
	@GetMapping(value = "/order/action/list-orders-by-pagination")
    public ResponseEntity<?> listOrdersByPagination(@RequestParam Integer pageNumber, @RequestParam Integer pageSize
            , @RequestParam String sortBy, @RequestParam String sortOrder) {
        List<Order> orderList = orderService.listOrdersByPageNumber(pageNumber, pageSize, sortBy, sortOrder);
        ResponseEntity responseEntity = new ResponseEntity<>(orderList, HttpStatus.OK);
        return responseEntity;
    }

	@PostMapping(value = "/order/action/place-order")
	public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
		Order newOrder = orderService.placeOrder(placeOrderRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
	}

}
