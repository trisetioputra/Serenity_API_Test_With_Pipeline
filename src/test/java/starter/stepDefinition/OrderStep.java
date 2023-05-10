package starter.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.action.LoginAction;
import starter.action.OrderAction;
import starter.action.ProductAction;
import starter.object.ProductObject;
import starter.object.UserObject;

import java.util.ArrayList;
import java.util.List;

public class OrderStep {

    @Steps
    ProductAction productController;

    @Steps
    OrderAction orderController;

    static String token;

    private List<ProductObject> productObjectList=new ArrayList<>();

    @When("I fetch two product ID from GET all product API")
    public void iFetchTwoProductIDFromGETAllProductAPI() {
        productObjectList=productController.fetchTwoRandomProducts();
    }

    @When("I send POST HTTP request for order with quantity as: {string} and given {string} token")
    public void iSendPOSTHTTPRequestForOrderWithQuantityAsAndGivenToken(String quantity, String token) {
        orderController.syncUserData(this.token);
        orderController.postOrderHttpRequest(productObjectList,token.equals("invalid")?"":this.token,quantity);
    }

    @And("I will receive the same quantity {string}, user data, and product ID on response")
    public void iWillReceiveTheSameQuantityUserDataAndProductIDOnResponse(String quantity) {
        orderController.checkMatchingResponse(quantity,productObjectList);
    }

    @When("I send GET Order By ID request for the newly created order")
    public void iSendGETOrderRequestForTheNewlyCreatedOrder() {
        orderController.getOrderByIdHttpRequest(this.token);
    }

    @Then("I will see the data matching with {string}, user data, and product ID on response")
    public void iWillSeeTheDataMatchingWithUserDataAndProductIDOnResponse(String quantity) {
        orderController.checkMatchingResponse(quantity,productObjectList);
    }

    @When("I send GET All Order for a user using the matching token")
    public void iSendGETAllOrderForAUserUsingTheMatchingToken() {
        orderController.getOrderByUser(this.token);
    }

    @When("I send POST HTTP request for order with quantity as: {string} and given {string} token and invalid product ID")
    public void iSendPOSTHTTPRequestForOrderWithQuantityAsAndGivenTokenAndInvalidProductID(String quantity, String token) {
        orderController.postOrderHttpRequestInvalidProducts(this.token,quantity);
    }

    @And("I will receive the same quantity {string}, user data, and null product on response")
    public void iWillReceiveTheSameQuantityUserDataAndNullProductOnResponse(String quantity) {
        orderController.checkMatchingResponseNullProduct(quantity);
    }

    @When("I send GET All Order for a user using invalid token")
    public void iSendGETAllOrderForAUserUsingInvalidToken() {
        orderController.getOrderByUser("invalid");
    }

    @When("I send GET Order By ID request for the newly created order with invalid token")
    public void iSendGETOrderByIDRequestForTheNewlyCreatedOrderWithInvalidToken() {
        orderController.getOrderByIdHttpRequest("invalid");
    }

    @When("I send GET Order By ID request for the invalid order ID")
    public void iSendGETOrderByIDRequestForTheInvalidOrderID() {
        orderController.getOrderByIdWithInvalidId(this.token);
    }
}
