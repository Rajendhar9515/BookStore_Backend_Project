package com.example.bookstore.repository;

import com.example.bookstore.model.CartDetails;
import com.example.bookstore.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderDetails, Integer> {

}
