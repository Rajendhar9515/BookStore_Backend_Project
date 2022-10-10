package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService iOrderService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody OrderDTO orderDTO){
        ResponseDTO responseDTO = new ResponseDTO("saved data successfully", iOrderService.placeOrder(orderDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/orderdata")
    public ResponseEntity<ResponseDTO> display(){
        ResponseDTO responseDTO = new ResponseDTO("Get call successfully", iOrderService.orderDetails());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int id){
        ResponseDTO responseDTO = new ResponseDTO("Deleted data successfully", iOrderService.cancelOrder(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
