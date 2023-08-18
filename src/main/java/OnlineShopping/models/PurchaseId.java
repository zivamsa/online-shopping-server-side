package OnlineShopping.models;

import java.io.Serializable;

public class PurchaseId implements Serializable {
    private Long deal;
    private Long product;

    public PurchaseId(Long deal, Long product) {
        this.deal = deal;
        this.product = product;
    }
    public PurchaseId() {}
}
