package com.example.jpaamarchdemo.repository;

import com.example.jpaamarchdemo.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDTO, Long> {
	OrderDTO findByOrderId(String orderId);

	OrderDTO findByOrderIdAndCustomerMobileNumber(String orderId, String customerMobileNumber);	
}
