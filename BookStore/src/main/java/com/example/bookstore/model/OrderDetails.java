package com.example.bookstore.model;

import com.example.bookstore.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue()
    private Integer orderId;

    int userid;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @org.hibernate.annotations.ForeignKey(name = "none")
    public List<CartDetails> cartDetails;


    String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime orderDate = LocalDateTime.now();


    public OrderDetails(List<CartDetails> cartData, String address, int userId) {
        this.cartDetails = cartData;
        this.address = address;
        this.orderDate = LocalDateTime.now();
        this.userid = userId;
    }
}
