package com.example.bookstore.service;

import com.example.bookstore.dto.CartDTO;
import com.example.bookstore.exceptionalHandler.BookStoreException;
import com.example.bookstore.model.BookDetails;
import com.example.bookstore.model.CartDetails;
import com.example.bookstore.model.UserDetails;
import com.example.bookstore.repository.BookRepo;
import com.example.bookstore.repository.CartRepo;
import com.example.bookstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    CartRepo cartRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    UserRepo userRepo;

    public CartDetails saveAll(CartDTO cartDTO) {
        Optional<UserDetails> userData = userRepo.findById(cartDTO.getUser_id());
        Optional<BookDetails> bookData = bookRepo.findById(cartDTO.getBook_id());
        if (userData.isPresent() && bookData.isPresent() && bookData.get().getQuantity() >= cartDTO.getQuantity()) {
            List<CartDetails> cartData = cartRepo.findAll();
            for (CartDetails cartDetails : cartData) {
                if (cartDetails.getBookDetails().getId().equals(cartDTO.getBook_id())) {
                    throw new BookStoreException("This is duplicate value");
                }
            }
            double totalBookPrice = cartDTO.getQuantity() * bookData.get().getPrice();
            CartDetails cartDetails = new CartDetails(userData.get(), bookData.get(), cartDTO.getQuantity(), totalBookPrice);
            return cartRepo.save(cartDetails);
        } else {
            throw (new BookStoreException("provided details are not correct"));
        }

    }

    public List<CartDetails> displayAll() {
        return cartRepo.findAll();
    }

    public CartDetails findById(int id) {
        return cartRepo.findById(id).orElseThrow(() ->
                new BookStoreException("Employee with id " + id + " does not exist in database"));
    }

    public String updateCartByQuantity(int cartId, int bookQuantity) {
        if (cartRepo.findById(cartId).isPresent()) {
            int bookId = cartRepo.findById(cartId).get().getBookDetails().getId();
            if (bookRepo.findById(bookId).get().getQuantity() >= bookQuantity) {
                double totalBookPrice = bookQuantity * bookRepo.findById(bookId).get().getPrice();
                cartRepo.updateCartByQuantity(cartId, bookQuantity, totalBookPrice);
                return "Updated data by quantity successfully ";
            } else {
                throw new BookStoreException("book Quantity is more then available quantity");
            }
        } else {
            throw new BookStoreException("This id is not present");
        }
    }

    public String delete(int id) {
        Optional<CartDetails> cartId = cartRepo.findById(id);
        if (cartId.isPresent()) {
            cartRepo.deleteById(id);
            return "data deleted successfully";

        } else {
            throw new BookStoreException("This id not preset");
        }

    }

}
