package com.example.FinalProject1.controllers;

import com.example.FinalProject1.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/deal")
public class DealController {

    @Autowired
    DealService dealService;


}
