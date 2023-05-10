package starter.action;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import starter.object.UserObject;

public class RegistrationAction {
    protected static String url= "https://altashop-api.fly.dev/api/auth/register";

    protected static Response response;

    protected String email;

    public void generateRandomEmail(){
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        this.email= generatedString+"@gmail.com";
    }

    public String fetchCurrentEmail(){
        return this.email;
    }

    public void postRegisterHttpRequest(String status, String email, String password, String fullname){
        generateRandomEmail();
        JsonObject jsonObject=new JsonObject();
        jsonObject.set("email",email.equals("null")?null:this.email);
        jsonObject.set("password",password.equals("null")?null:password);
        jsonObject.set("fullname",fullname.equals("null")?null:fullname);
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type","application/json")
                .body(jsonObject.toString())
                .when()
                .post("https://altashop-api.fly.dev/api/auth/register").then().extract().response();
    }

    public void postRegisterHttpRequestForAlreadyRegistered(String password, String fullname){
        JsonObject jsonObject=new JsonObject();
        jsonObject.set("email",this.email);
        jsonObject.set("password",password);
        jsonObject.set("fullname",fullname);
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type","application/json")
                .body(jsonObject.toString())
                .when()
                .post("https://altashop-api.fly.dev/api/auth/register").then().extract().response();
    }

    public void receiveMatchingValue(String email, String password, String fullname){
        UserObject userObject= response.jsonPath().getObject("data",UserObject.class);
        Assert.assertEquals(userObject.getEmail(),this.email);
        Assert.assertEquals(userObject.getPassword(),password);
        Assert.assertEquals(userObject.getFullName(),fullname);
    }

}
