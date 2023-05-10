package starter.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderObject {
    Integer ID;
    UserObject User;
    ProductObject Product;
    Integer Quantity;

    public Integer getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public UserObject getUser() {
        return User;
    }
    @JsonProperty("User")
    public void setUser(UserObject user) {
        User = user;
    }

    public ProductObject getProduct() {
        return Product;
    }
    @JsonProperty("Product")
    public void setProduct(ProductObject product) {
        Product = product;
    }

    public Integer getQuantity() {
        return Quantity;
    }
    @JsonProperty("Quantity")
    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }
}
