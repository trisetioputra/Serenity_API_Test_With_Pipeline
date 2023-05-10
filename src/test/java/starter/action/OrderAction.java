package starter.action;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import starter.object.OrderObject;
import starter.object.ProductObject;
import starter.object.UserObject;

import java.util.ArrayList;
import java.util.List;

public class OrderAction {
    protected static String url= "https://altashop-api.fly.dev/api/orders";

    protected static String urlUser= "https://altashop-api.fly.dev/api/auth/info";


    protected static Response response;

    protected static List<OrderObject> orderObjectList=new ArrayList<>();

    protected static UserObject userObject;

    public String setGetEndpoint(){
        return url;
    }
    public void postOrderHttpRequest(List<ProductObject> productObjectList, String token, String quantity){
        JsonArray jsonArray = new JsonArray();
        for(ProductObject productObject:productObjectList){
            JsonObject jsonObject = new JsonObject();
            jsonObject.set("product_id", productObject.getId());
            jsonObject.set("quantity",Integer.parseInt(quantity));
            jsonArray.add(jsonObject);
        }
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(jsonArray.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
        syncOrderObject();
    }

    public void postOrderHttpRequestInvalidProducts(String token, String quantity){
        JsonArray jsonArray = new JsonArray();
        for(int i=1;i<=2;i++){
            JsonObject jsonObject = new JsonObject();
            jsonObject.set("product_id", i);
            jsonObject.set("quantity",Integer.parseInt(quantity));
            jsonArray.add(jsonObject);
        }
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(jsonArray.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
        syncOrderObject();
    }

    public void getOrderByIdHttpRequest(String token){
        String sentToken= token.equals("invalid")?"":token;
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + sentToken)
                .when()
                .get(setGetEndpoint()+"/"+String.valueOf(this.orderObjectList.get(0).getID())).then().extract().response();
        syncSingleOrderObject();
    }

    public void getOrderByIdWithInvalidId(String token){
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(setGetEndpoint()+"/1").then().extract().response();
    }
    public void getOrderByUser(String token){
        String sentToken= token.equals("invalid")?"":token;
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + sentToken)
                .when()
                .get(setGetEndpoint()).then().extract().response();
    }
    public void syncOrderObject(){
        this.orderObjectList.clear();
        for(int i=0;i<2;i++){
            OrderObject orderObject= response.jsonPath().getObject(String.format("data[%s]",String.valueOf(i)),OrderObject.class);
            this.orderObjectList.add(orderObject);
        }
    }

    public void syncSingleOrderObject(){
        this.orderObjectList.clear();
        OrderObject orderObject= response.jsonPath().getObject("data",OrderObject.class);
        this.orderObjectList.add(orderObject);
    }

    public void syncUserData(String token){
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(urlUser).then().extract().response();

        userObject= response.jsonPath().getObject("data",UserObject.class);
    }

    public void checkMatchingResponse(String quantity, List<ProductObject> productObjectList){
        int index=0;
        Integer expectedQuantity=Integer.parseInt(quantity);
        for(OrderObject orderObject:orderObjectList){
            Assert.assertEquals(orderObject.getQuantity(),expectedQuantity);

            Assert.assertEquals(orderObject.getProduct().getName(),productObjectList.get(index).getName());
            Assert.assertEquals(orderObject.getProduct().getPrice(),productObjectList.get(index).getPrice());
            Assert.assertEquals(orderObject.getProduct().getId(),productObjectList.get(index).getId());


            Assert.assertEquals(orderObject.getUser().getEmail(),this.userObject.getEmail());
            Assert.assertEquals(orderObject.getUser().getFullName(),this.userObject.getFullName());
            Assert.assertEquals(orderObject.getUser().getPassword(),this.userObject.getPassword());

            index++;
        }
    }
    public void checkMatchingResponseNullProduct(String quantity){
        int index=0;
        Integer expectedQuantity=Integer.parseInt(quantity);
        for(OrderObject orderObject:orderObjectList){
            Assert.assertEquals(orderObject.getQuantity(),expectedQuantity);

            Assert.assertEquals(orderObject.getProduct(),null);

            Assert.assertEquals(orderObject.getUser().getEmail(),this.userObject.getEmail());
            Assert.assertEquals(orderObject.getUser().getFullName(),this.userObject.getFullName());
            Assert.assertEquals(orderObject.getUser().getPassword(),this.userObject.getPassword());

            index++;
        }
    }
}
