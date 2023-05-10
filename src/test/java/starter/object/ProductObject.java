package starter.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.cucumber.java.eo.Do;

import java.util.List;
public class ProductObject {
    int ID;
    String Name;
    String Description;
    Double Price;
    int Ratings;
    List<CategoryObject> Categories;

    public Integer getId() {
        return ID;
    }
    @JsonProperty("ID")
    public void setId(Integer id) {
        this.ID = id;
    }

    public String getName() {
        return Name;
    }
    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }
    @JsonProperty("Description")
    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrice() {
        return Price;
    }
    @JsonProperty("Price")
    public void setPrice(Double price) {
        Price = price;
    }

    public Integer getRatings() {
        return Ratings;
    }
    @JsonProperty("Ratings")
    public void setRatings(Integer ratings) {
        Ratings = ratings;
    }

    public List<CategoryObject> getCategories() {
        return Categories;
    }
    @JsonProperty("Categories")
    public void setCategories(List<CategoryObject> categories) {
        Categories = categories;
    }
}

