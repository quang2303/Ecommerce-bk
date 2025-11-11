package com.ecommerce.be.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

import com.ecommerce.be.Constant.OrderStatus;
import com.ecommerce.be.Dto.OrderForBuyerPageDTO;

import java.util.List;



@RestController
public class HomeController {


    private OrderService orderService;

    public HomeController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/home") 
    public ResponseEntity<String> entry(HttpServletRequest request) {
        try {
            request.logout();
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("Logout");
    }
}
