package OnlineShopping.controllers;

import OnlineShopping.models.Deals;
import OnlineShopping.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/deal")
public class DealController {

    @Autowired
    DealService dealService;

    @GetMapping("/deal/{id}")
    public Deals getDealById(@PathVariable("id") Long id) {
        return dealService.getDealById(id);
    }
}
