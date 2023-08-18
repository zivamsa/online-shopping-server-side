package OnlineShopping.services;

import OnlineShopping.models.Deals;
import OnlineShopping.repository.DealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {
    @Autowired
    DealsRepository repository;

    public Deals getDealById(Long id) {
        return repository.findById(id).get();
    }

    public List<Deals> getAllDeals(){
        List<Deals> deals = new ArrayList<Deals>();
        repository.findAll().forEach(deal -> deals.add(deal));
        return deals;
    }
    public void saveOrUpdate(Deals deal) {
        repository.save(deal);
    }
}
