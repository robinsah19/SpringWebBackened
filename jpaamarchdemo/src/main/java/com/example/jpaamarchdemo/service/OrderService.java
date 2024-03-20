package com.example.jpaamarchdemo.service;

import com.example.jpaamarchdemo.dto.OrderDTO;
import com.example.jpaamarchdemo.model.Order;
import com.example.jpaamarchdemo.model.PlaceOrderRequest;
import com.example.jpaamarchdemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Order getOrderByOrderId(String orderId) {
		OrderDTO orderDTO = orderRepository.findByOrderId(orderId);

		Order order = new Order();
		order.setOrderId(orderDTO.getOrderId());
		order.setMobileNumber(orderDTO.getCustomerMobileNumber());
		return order;
	}
	

	public List<Order> listOrders() {
		List<OrderDTO> orderDTOS = orderRepository.findAll();

		List<Order> orders = new ArrayList<>();
		for (OrderDTO orderDTO : orderDTOS) {
			Order order = new Order();
			order.setOrderId(orderDTO.getOrderId());
			order.setMobileNumber(orderDTO.getCustomerMobileNumber());
			orders.add(order);
		}
		return orders;
	}
	public List<Order> listOrdersByPageNumber(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("DESC")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<OrderDTO> pageResponse = orderRepository.findAll(pageRequest);

        List<Order> orders = new ArrayList<>();
        for (
                OrderDTO orderDTO : pageResponse.getContent()) {
            Order order = new Order();
            order.setOrderId(orderDTO.getOrderId());
            order.setMobileNumber(orderDTO.getCustomerMobileNumber());
            orders.add(order);
        }
        return orders;
    }

    public Order placeOrder(PlaceOrderRequest placeOrderRequest) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setCustomerMobileNumber(placeOrderRequest.getCustomerMobileNumber());

        orderDTO = orderRepository.save(orderDTO);

        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setMobileNumber(orderDTO.getCustomerMobileNumber());
        return order;
    }

    /*
    Pagination

    100
    Example 1: pageNumber=1, pageSize=20 -> 0th index to 19th index
    Example 2: pageNumber=3, pageSize=15 -> 30th index to 45th index
    Example 3: pageNumber=5, pageSize=10 -> 40th index to 49th index
    Example 4: pageNumber 3, pageSize=40 -> 79th index to 99th index

    0-9, 10-19, 20-29, 30-39, 40-49
     */
}
