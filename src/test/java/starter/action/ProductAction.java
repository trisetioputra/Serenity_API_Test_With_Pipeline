package starter.action;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import starter.object.CategoryObject;
import starter.object.ProductObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.*;

public class ProductAction {
    protected static String url= "https://altashop-api.fly.dev/api/products";

    protected static Integer id;
    protected static Response response;
    public String setGetEndpoint(){
        return url;
    }
    public void getHttpRequest(){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()).then().extract().response();
    }

    public void getByIdHttpRequest(){
        this.updatePostResultId();
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()+"/"+this.id).then().extract().response();
    }
    public void getProductRatingByIdHttpRequest(){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()+"/"+this.id+"/ratings").then().extract().response();
    }
    public void getProductCommentByIdHttpRequest(){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()+"/"+this.id+"/comments").then().extract().response();
    }
    public void deleteByIdHttpRequest(){
        this.updatePostResultId();
        response= SerenityRest.given()
                .when()
                .delete(setGetEndpoint()+"/"+this.id).then().extract().response();
    }
    public void getByIdHttpRequest(String ids){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()+"/"+ids).then().extract().response();
    }
    public void postHttpRequest(String name, String price, String description, int[] categories){
        JsonObject jsonObject = new JsonObject();
        if(!name.equals("null")){
            jsonObject.set("name",name);
        }
        if(!price.equals("null")){
            jsonObject.set("price",Integer.parseInt(price));
        }
        if(!description.equals("null")){
            jsonObject.set("description",description);
        }

        JsonArray jsonArray = new JsonArray();
        for(int val:categories){
            jsonArray.add(val);
        }
        jsonObject.set("categories", jsonArray);
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
    }

    public void postHttpRequestForRating(String rating, String type, String token){
        this.updatePostResultId();
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("count", Integer.parseInt(rating));
        if(type.equals("no token")){
            response= SerenityRest.given()
                    .contentType("application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .when()
                    .post(setGetEndpoint()+"/"+this.id+"/ratings").then().extract().response();
        }
        else{
            String sentToken= type.equals("wrong token")?"testToken":token;
            response= SerenityRest.given()
                    .contentType("application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + sentToken)
                    .body(jsonObject.toString())
                    .when()
                    .post(setGetEndpoint()+"/"+this.id+"/ratings").then().extract().response();
        }
    }

    public void postHttpRequestForComment(String comment, String type, String token, boolean isTrue){
        this.updatePostResultId();
        JsonObject jsonObject = new JsonObject();
        if(isTrue==false){
            jsonObject.set("content", Integer.parseInt(comment));
        }
        else{
            jsonObject.set("content", comment);
        }
        if(type.equals("no token")){
            response= SerenityRest.given()
                    .contentType("application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .when()
                    .post(setGetEndpoint()+"/"+this.id+"/comments").then().extract().response();
        }
        else{
            String sentToken= type.equals("wrong token")?"testToken":token;
            response= SerenityRest.given()
                    .contentType("application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + sentToken)
                    .body(jsonObject.toString())
                    .when()
                    .post(setGetEndpoint()+"/"+this.id+"/comments").then().extract().response();
        }
    }

    public void postHttpRequestForRatingInvalidId(String rating, String type, String token){
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("count", Integer.parseInt(rating));
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()+"/"+1+"/ratings").then().extract().response();
    }

    public void updatePostResultId(){
        ProductObject productObject = response.jsonPath().getObject("data", ProductObject.class);
        if(productObject!=null){
            this.id=productObject.getId();
        }
    }

    public void mutationPostHttpRequest(String mutation, String name, String price, String description, int[] categories){
        JsonObject jsonObject = new JsonObject();
        if(mutation.equals("name")){
            jsonObject.set("name",Integer.parseInt(name));
            jsonObject.set("price",Integer.parseInt(price));
            jsonObject.set("description",description);
            JsonArray jsonArray = new JsonArray();
            for(int val:categories){
                jsonArray.add(val);
            }
            jsonObject.set("categories", jsonArray);
        }
        if(mutation.equals("price")){
            jsonObject.set("name",name);
            jsonObject.set("price",price);
            jsonObject.set("description",description);
            JsonArray jsonArray = new JsonArray();
            for(int val:categories){
                jsonArray.add(val);
            }
            jsonObject.set("categories", jsonArray);
        }
        if(mutation.equals("description")){
            jsonObject.set("name",name);
            jsonObject.set("price",price);
            jsonObject.set("description",Integer.parseInt(description));
            JsonArray jsonArray = new JsonArray();
            for(int val:categories){
                jsonArray.add(val);
            }
            jsonObject.set("categories", jsonArray);
        }
        if(mutation.equals("categories")){
            jsonObject.set("name",name);
            jsonObject.set("price",price);
            jsonObject.set("description",description);
            JsonArray jsonArray = new JsonArray();
            for(int val:categories){
                jsonArray.add(String.valueOf(val));
            }
            jsonObject.set("categories", jsonArray);
        }
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
    }

    public void receiveValidStatus(String code){
        restAssuredThat(response->response.statusCode(Integer.parseInt(code)));
    }
    public void receiveArrayOfObjects(){
        restAssuredThat(response->response.body("data",isA(List.class)));
        restAssuredThat(response->response.body("data[0]",isA(Object.class)));
    }
    public void receiveCorrectAttributesOnArray(){
        List<ProductObject> allProduct = response.jsonPath().getList("data", ProductObject.class);
        //due to the amount of data retrieved, checking only applies to 10
        for(int i=0;i<10;i++) {
            assertAttributeProduct(allProduct.get(i));
        }
    }

    public void receiveMatchingValues(String name, Double price, String description, int[] categories){
        ProductObject productObject= response.jsonPath().getObject("data",ProductObject.class);
        Assert.assertEquals(productObject.getName(),name.equals("null")?"":name);
        Assert.assertEquals(productObject.getPrice(),price);
        Assert.assertEquals(productObject.getDescription(),description.equals("null")?"":description);

        int counter=0;
        for(CategoryObject categoryObject:productObject.getCategories()){
            int actualId=categoryObject.getID();
            int expectedId=categories[counter];
            Assert.assertEquals(actualId,expectedId);
            counter++;
        }
    }

    public void assertAttributeProduct(ProductObject productObject){
        Assert.assertEquals(productObject.getId().getClass(),Integer.class);
        Assert.assertEquals(productObject.getName().getClass(),String.class);
        Assert.assertEquals(productObject.getPrice().getClass(),Double.class);
        Assert.assertEquals(productObject.getDescription().getClass(),String.class);
        Assert.assertEquals(productObject.getRatings().getClass(),Integer.class);
        Assert.assertEquals(productObject.getCategories().getClass(), ArrayList.class);
    }

    public void receiveId(){
        restAssuredThat(response->response.body("data.ID",is(notNullValue())));
    }
    public void receiveErrorMessage(){
        restAssuredThat(response->response.body("error",is(notNullValue())));
    }
    public void receiveCorrectRating(String rating){
        restAssuredThat(response->response.body("data.Ratings",equalTo(Integer.parseInt(rating))));
    }

    public void receiveCorrectComment(String comment){
        restAssuredThat(response->response.body("data.Content",equalTo(comment)));
    }

    public void receiveCorrectRatingViaGet(String rating){
        restAssuredThat(response->response.body("data",equalTo(Integer.parseInt(rating))));
    }
    public void receiveCorrectCommentViaGet(String comment){
        restAssuredThat(response->response.body("data[0].Content",equalTo(comment)));
    }

    public List<ProductObject> fetchTwoRandomProducts(){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()).then().extract().response();

        ProductObject firstProductObject= response.jsonPath().getObject("data[0]", ProductObject.class);
        ProductObject secondProductObject= response.jsonPath().getObject("data[1]", ProductObject.class);

        List<ProductObject> productObjectsList=new ArrayList<>();
        productObjectsList.add(firstProductObject);
        productObjectsList.add(secondProductObject);
        return productObjectsList;
    }
}
