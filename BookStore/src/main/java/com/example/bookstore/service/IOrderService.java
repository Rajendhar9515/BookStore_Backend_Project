package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;

public interface IOrderService {
    Object placeOrder(OrderDTO orderDTO);

    Object orderDetails();

    Object cancelOrder(int id);


}
