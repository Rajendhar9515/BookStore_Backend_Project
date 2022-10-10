package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.email.EmailService;
import com.example.bookstore.exceptionalHandler.BookStoreException;
import com.example.bookstore.model.CartDetails;
import com.example.bookstore.model.OrderDetails;
import com.example.bookstore.model.UserDetails;
import com.example.bookstore.repository.BookRepo;
import com.example.bookstore.repository.CartRepo;
import com.example.bookstore.repository.OrderRepo;
import com.example.bookstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    BookRepo bookRepo;

    @Autowired
    EmailService emailService;


    List<CartDetails> cartList = new ArrayList<>();
    public OrderDetails placeOrder(OrderDTO orderDTO) {
        List<CartDetails> cartData = cartRepo.getCartDetails(orderDTO.getUser_id());
        List<UserDetails> userData = userRepo.getUserId(orderDTO.getUser_id());
        if (cartData.isEmpty() || userData.isEmpty()) {
            throw new BookStoreException("This id is not present");
        } else {
            UserDetails userDetails = userRepo.findById(orderDTO.getUser_id()).get();
            OrderDetails orderDetails = new OrderDetails(cartData, orderDTO.getAddress(), orderDTO.getUser_id());
//            emailService.sendEmail(userDetails.getEmail(), "your order placed successfully", "your ordered books successfully");
            orderRepo.save(orderDetails);
            List<CartDetails> cartDetails = orderDetails.getCartDetails();
            for (CartDetails cartDetail : cartDetails) {
                int quantity = cartDetail.getBookDetails().getQuantity() - cartDetail.getQuantity();
                int bookId = cartDetail.getBookDetails().getId();
                bookRepo.updateBookQuantity(bookId, quantity);
                cartList.add(cartDetail);
            }
            cartRepo.deleteCartDetails(orderDTO.getUser_id());
            return orderDetails;
        }
    }


    public List<OrderDetails> orderDetails() {
        return orderRepo.findAll();
    }

    public String cancelOrder(int id) {
        System.out.println(cartList);
        Optional<OrderDetails> orderId = orderRepo.findById(id);
        if (orderId.isPresent()){
            List<CartDetails> cartDetails = cartList;
            for (CartDetails cartDetail : cartDetails){
                int quantity = cartDetail.getBookDetails().getQuantity();
                int bookId = cartDetail.getBookDetails().getId();
                bookRepo.updateBookQuantity(bookId, quantity);
            }
            orderRepo.deleteById(id);
            return "data Deleted by id successfully";
        }else {
            throw new BookStoreException("This id is not present");
        }
    }
}

