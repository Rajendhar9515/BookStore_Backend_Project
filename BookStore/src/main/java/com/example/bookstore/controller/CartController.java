package com.example.bookstore.controller;

import com.example.bookstore.dto.CartDTO;
import com.example.bookstore.dto.ResponseDTO;
import com.example.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService iCartService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody CartDTO cartDTO){
        ResponseDTO responseDTO = new ResponseDTO("Saved data successfully", iCartService.saveAll(cartDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
    @GetMapping("/cartdata")
    public ResponseEntity<ResponseDTO> display(){
        ResponseDTO responseDTO = new ResponseDTO("Get call data successfully", iCartService.displayAll());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("find/{id}")
    public ResponseEntity<ResponseDTO> findCartId(@PathVariable int id){
        ResponseDTO responseDTO = new ResponseDTO("Get call by Id successfully", iCartService.findById(id));
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
    @PutMapping("/edit/{cartId}/{bookQuantity}")
    public ResponseEntity<ResponseDTO> update(@PathVariable int cartId, @PathVariable int bookQuantity){
        ResponseDTO responseDTO = new ResponseDTO("Update data successfully", iCartService.updateCartByQuantity(cartId, bookQuantity));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping("remove/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable int id){
        ResponseDTO responseDTO = new ResponseDTO("Deleted data successfully", iCartService.delete(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
