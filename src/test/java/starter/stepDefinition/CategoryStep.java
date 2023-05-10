package starter.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.action.CategoryAction;

public class CategoryStep {
    @Steps
    CategoryAction categoryController;
    @When("I send POST Category HTTP request with data as:{string}, {string}")
    public void iSendPOSTCategoryHTTPRequestWithDataAs(String name, String description) {
        categoryController.postHttpRequest(name,description);
    }

    @And("I receive matching category response with the given input: {string}, {string}")
    public void iReceiveMatchingCategoryResponseWithTheGivenInput(String name, String description) {
        categoryController.receiveMatchingInput(name,description);
    }

    @When("I send POST Category HTTP request with mutation on {string} and data as:{string}, {string}")
    public void iSendPOSTCategoryHTTPRequestWithMutationOnAndDataAs(String mutator, String name, String description) {
        categoryController.postHttpRequestWithMutation(mutator,name,description);
    }

    @When("I send GET Category By ID HTTP request given previously created ID")
    public void iSendGETCategoryByIDHTTPRequestGivenPreviouslyCreatedID() {
        categoryController.getByIdHttpRequest();
    }

    @Then("I will see the same category data as: {string} and {string}")
    public void iWillSeeTheSameCategoryDataAsAnd(String name, String description) {
        categoryController.receiveMatchingInput(name, description);
    }

    @When("I send GET Category By ID HTTP request given invalid ID as {string}")
    public void iSendGETCategoryByIDHTTPRequestGivenInvalidIDAs(String id) {
        categoryController.getByIdHttpRequestMutation(id);
    }

    @When("I send DELETE Category By ID HTTP request given previously created ID")
    public void iSendDELETECategoryByIDHTTPRequestGivenPreviouslyCreatedID() {
        categoryController.deleteByIdHttpRequest();
    }

    @When("I send GET All Category HTTP request")
    public void iSendGETAllCategoryHTTPRequest() {
        categoryController.getHttpRequest();
    }

    @And("I receive the correct attributes for category object within array")
    public void iReceiveTheCorrectAttributesForCategoryObjectWithinArray() {
        categoryController.receiveCorrectAttribute();
    }
}
