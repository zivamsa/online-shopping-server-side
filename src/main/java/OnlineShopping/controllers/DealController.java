package OnlineShopping.controllers;

import OnlineShopping.dto.CheckoutRequest;
import OnlineShopping.models.Deals;
import OnlineShopping.services.AuthenticationService;
import OnlineShopping.services.DealService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/deal")
public class DealController {

    @Autowired
    DealService dealService;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/deal/{id}")
    public Deals getDealById(@PathVariable("id") Long id) {
        return dealService.getDealById(id);
    }

    @PostMapping("/")
    public void createDeal(@RequestBody List<CheckoutRequest> purchases,
                           HttpServletRequest request) {
        var user = authenticationService.getUserByRequest(request);
        dealService.createDeal(purchases, user);
    }

}
