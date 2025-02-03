package suchai125.amazon.project125.models;

import suchai125.amazon.project125.entities.Product;

public interface CartItem {
    int getQuantity();
    void setQuantity(int quantity);
    double getUnitPrice();
    double getTotal();
    String getTotalString();
    double getPercentDiscount();
    Product getProduct();
}