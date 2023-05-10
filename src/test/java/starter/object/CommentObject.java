package starter.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentObject {
    Integer ID;
    String Content;
    String User;
    String Product;
    String Comment;

    public Integer getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getContent() {
        return Content;
    }
    @JsonProperty("Content")
    public void setContent(String content) {
        Content = content;
    }

    public String getUser() {
        return User;
    }
    @JsonProperty("User")
    public void setUser(String user) {
        User = user;
    }

    public String getProduct() {
        return Product;
    }
    @JsonProperty("Product")
    public void setProduct(String product) {
        Product = product;
    }
    @JsonProperty("Comment")

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
