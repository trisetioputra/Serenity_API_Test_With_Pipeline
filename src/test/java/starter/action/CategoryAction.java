package starter.action;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import starter.object.CategoryObject;
import starter.object.ProductObject;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.*;

public class CategoryAction {
    protected static String url= "https://altashop-api.fly.dev/api/categories";

    protected static Response response;

    protected static Integer id;
    public String setGetEndpoint(){
        return url;
    }
    public void getHttpRequest(){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()).then().extract().response();
    }

    public void getByIdHttpRequest(){
        this.updateCurrentId();
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()+"/"+this.id).then().extract().response();
    }
    public void deleteByIdHttpRequest(){
        this.updateCurrentId();
        response= SerenityRest.given()
                .when()
                .delete(setGetEndpoint()+"/"+this.id).then().extract().response();
    }
    public void getByIdHttpRequestMutation(String id){
        response= SerenityRest.given()
                .when()
                .get(setGetEndpoint()+"/"+id).then().extract().response();
    }
    public void receiveArrayOfObjects(){
        restAssuredThat(response->response.body("data",isA(List.class)));
        restAssuredThat(response->response.body("data[0]",isA(Object.class)));
    }
    public int getRandomExistingCategoryId(int id){
        getHttpRequest();
        CategoryObject categoryObject= response.jsonPath().getObject(String.format("data[%s]",String.valueOf(id)), CategoryObject.class);
        return categoryObject.getID();
    }

    public void updateCurrentId(){
        CategoryObject categoryObject= response.jsonPath().getObject("data", CategoryObject.class);
        if(categoryObject!=null){
            this.id=categoryObject.getID();
        }
    }
    public void postHttpRequest(String name, String description){
        JsonObject jsonObject = new JsonObject();
        if(!name.equals("null")){
            jsonObject.set("name",name);
        }
        if(!description.equals("null")){
            jsonObject.set("description",description);
        }
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
    }

    public void postHttpRequestWithMutation(String mutator, String name, String description){
        JsonObject jsonObject = new JsonObject();
        if(mutator.equals("name")){
            jsonObject.set("name",Integer.parseInt(name));
            jsonObject.set("description",description);
        }
        if(mutator.equals("description")){
            jsonObject.set("name",name);
            jsonObject.set("description",Integer.parseInt(description));
        }
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
    }

    public void receiveMatchingInput(String name, String description){
        CategoryObject categoryObject= response.jsonPath().getObject("data",CategoryObject.class);
        Assert.assertEquals(categoryObject.getName(),name);
        Assert.assertEquals(categoryObject.getDescription(),description.equals("null")?"":description);
    }

    public void receiveCorrectAttribute(){
        CategoryObject categoryObject= response.jsonPath().getObject("data[0]",CategoryObject.class);
        Assert.assertEquals(categoryObject.getName().getClass(),String.class);
        Assert.assertEquals(categoryObject.getDescription().getClass(), String.class);
    }
}
