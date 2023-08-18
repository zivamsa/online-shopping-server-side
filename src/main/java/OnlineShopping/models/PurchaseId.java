package OnlineShopping.models;

public class PurchaseId {
    private Long deal;
    private Long product;

    public PurchaseId(Long deal, Long product) {
        this.deal = deal;
        this.product = product;
    }

    public Long getDeal() {
        return deal;
    }

    public void setDeal(Long deal) {
        this.deal = deal;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }
}
