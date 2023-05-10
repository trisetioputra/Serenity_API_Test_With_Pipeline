package starter.action;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.isA;

public class LoginAction {
    protected static String url= "https://altashop-api.fly.dev/api/auth/login";

    protected static String urlUser= "https://altashop-api.fly.dev/api/auth/info";

    protected static Response response;

    protected static String token;

    public String setGetEndpoint(){
        return url;
    }
    public void getHttpRequest(String status, String email, String password){
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("email",email);
        jsonObject.set("password",password);
        jsonObject.set("fullname","testing");
        if(status.equals("successful")){
            SerenityRest.given()
                    .contentType("application/json")
                    .header("Content-Type","application/json")
                    .body(jsonObject.toString())
                    .when()
                    .post("https://altashop-api.fly.dev/api/auth/register");
        }
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
    }

    public String generateRandomEmail(){
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString+"@gmail.com";
    }

    public void getHttpRequestWithoutRegistration(String type, String email, String password){
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("email",type.equals("unregistered")?generateRandomEmail():email.equals("null")?null:email);
        jsonObject.set("password",type.equals("wrong password")?"testSalah":password.equals("null")?null:password);
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .post(setGetEndpoint()).then().extract().response();
    }

    public void getHTTPRequestUserData(String type){
        String sentToken= type.equals("wrong-token")?"testing": type.equals("missing-token")?"":this.token;
        response= SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + sentToken)
                .when()
                .get(urlUser).then().extract().response();
    }

    public String receiveValidToken(){
        String token = response.jsonPath().getString("data");
        restAssuredThat(response->response.body("data",isA(String.class)));
        this.token=token;
        return token;
    }
}
