package starter.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.action.CategoryAction;
import starter.action.ProductAction;

public class ProductStep {

    @Steps
    ProductAction productController;

    @Steps
    CategoryAction categoryAction;

    static String token;

    @Given("I set {string} endpoints")
    public void i_set_endpoints(String string) {
        productController.setGetEndpoint();
    }
    @When("I send GET HTTP request")
    public void i_send_get_http_request() {
        productController.getHttpRequest();
    }
    @Then("I receive valid HTTP response code {string}")
    public void i_receive_valid_http_response_code(String code) {
        productController.receiveValidStatus(code);
    }
    @And("I receive array type for the retrieved data")
    public void i_receive_array_type_for_the_retrieved_data() {
        productController.receiveArrayOfObjects();
    }
    @And("I receive the correct attributes for object within array")
    public void i_receive_the_correct_attributes_for_object_within_array() {
        productController.receiveCorrectAttributesOnArray();
    }

    @When("I send POST HTTP request with data as:{string}, {string}, {string}, and {string}")
    public void iSendPOSTHTTPRequestWithDataAsAnd(String name, String price, String description, String categories) {
        int[] random_categories=new int[2];
        if(!categories.equals("null")){
            random_categories= new int[]{categoryAction.getRandomExistingCategoryId(0),categories.equals("half-valid")?1:categoryAction.getRandomExistingCategoryId(1)};
        }
        productController.postHttpRequest(name, price, description, random_categories);
    }

    @And("I receive matching response with the given input: {string}, {string}, {string}, and {string}")
    public void iReceiveMatchingResponseWithTheGivenInputAnd(String name, String price, String description, String categories) {
        int[] random_categories=new int[2];
        if(!categories.equals("null")){
            random_categories= new int[]{categoryAction.getRandomExistingCategoryId(0),categories.equals("half-valid")?1:categoryAction.getRandomExistingCategoryId(1)};
        }
        productController.receiveMatchingValues(name, price.equals("null")?0.0:Double.parseDouble(price), description, random_categories);
    }

    @And("I receive the newly created ID")
    public void iReceiveTheNewlyCreatedID() {
        productController.receiveId();
    }

    @And("I receive error message on response")
    public void iReceiveErrorMessageOnResponse() {
        productController.receiveErrorMessage();
    }

    @When("I send POST HTTP request with mutation on {string} and data as:{string}, {string}, {string}, and {string}")
    public void iSendPOSTHTTPRequestWithMutationOnAndDataAsAnd(String mutation, String name, String price, String description, String category) {
        int[] random_categories= new int[]{categoryAction.getRandomExistingCategoryId(0)};
        productController.mutationPostHttpRequest(mutation, name, price, description, random_categories);
    }

    @And("I send GET By ID HTTP request for the previously created product ID")
    public void iSendGETByIDHTTPRequestForThePreviouslyCreatedProductID() {
        productController.getByIdHttpRequest();
    }

    @When("I send GET By ID HTTP request for non-existing ID: {string}")
    public void iSendGETByIDHTTPRequestForNonExistingID(String id) {
        productController.getByIdHttpRequest(id);
    }

    @And("I send DELETE By ID HTTP request for the previously created product ID")
    public void iSendDELETEByIDHTTPRequestForThePreviouslyCreatedProductID() {
        productController.deleteByIdHttpRequest();
    }

    @And("I will receive the same rating {string} in response body")
    public void iWillReceiveTheSameRatingInResponseBody(String rating) {
        productController.receiveCorrectRating(rating);
    }

    @When("I send POST HTTP request for product rating with data as: {string} and given {string} token")
    public void iSendPOSTHTTPRequestForProductRatingWithDataAsAndGivenToken(String rating, String tokenType) {
        productController.postHttpRequestForRating(rating, tokenType, this.token);
    }

    @When("I send GET HTTP request for product rating for the same product")
    public void iSendGETHTTPRequestForProductRatingForTheSameProduct() {
        productController.getProductRatingByIdHttpRequest();
    }

    @Then("I will see the data matching with {string}")
    public void iWillSeeTheDataMatchingWith(String count) {
        productController.receiveCorrectRatingViaGet(count);
    }

    @When("I send POST HTTP request for product comment with data as: {string} and given {string} token")
    public void iSendPOSTHTTPRequestForProductCommentWithDataAsAndGivenToken(String comment, String type) {
        productController.postHttpRequestForComment(comment,type,this.token, true);
    }

    @And("I will receive the same comment {string} in response body")
    public void iWillReceiveTheSameCommentInResponseBody(String comment) {
        productController.receiveCorrectComment(comment);
    }

    @When("I send GET HTTP request for product comment for the same product")
    public void iSendGETHTTPRequestForProductCommentForTheSameProduct() {
        productController.getProductCommentByIdHttpRequest();
    }

    @Then("I will see the comment data matching with {string}")
    public void iWillSeeTheCommentDataMatchingWith(String comment) {
        productController.receiveCorrectCommentViaGet(comment);
    }

    @When("I send POST HTTP request for product comment with invalid data as: {string} and given {string} token")
    public void iSendPOSTHTTPRequestForProductCommentWithInvalidDataAsAndGivenToken(String comment, String type) {
        productController.postHttpRequestForComment(comment,type,this.token, false);
    }

    @When("I send POST HTTP request for invalid product's rating with valid data as: {string} and given {string} token")
    public void iSendPOSTHTTPRequestForInvalidProductSRatingWithValidDataAsAndGivenToken(String rating, String type) {
        productController.postHttpRequestForRatingInvalidId(rating,type,this.token);
    }
}
