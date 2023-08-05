package com.example.FinalProject1.controllers;

import com.example.FinalProject1.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;


}
