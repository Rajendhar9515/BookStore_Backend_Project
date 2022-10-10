package com.example.bookstore.service;

import com.example.bookstore.dto.CartDTO;

public interface ICartService {
    Object saveAll(CartDTO cartDTO);

    Object displayAll();

    Object findById(int id);

    Object updateCartByQuantity(int id, int bookQuantity);

    Object delete(int id);


}
