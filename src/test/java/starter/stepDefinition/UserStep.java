package starter.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.action.LoginAction;
import starter.action.RegistrationAction;
import starter.object.ProductObject;

public class UserStep {

    @Steps
    LoginAction loginController;

    @Steps
    RegistrationAction registrationController;

    @And("I send POST HTTP request for {string} Login with credentials as: {string}, {string}")
    public void iSendPOSTHTTPRequestForLoginWithCredentialsAs(String status, String email, String password) {
        loginController.getHttpRequest(status, email,password);
    }

    @Then("I receive valid token")
    public void iReceiveValidToken() {
        ProductStep.token= loginController.receiveValidToken();
        OrderStep.token=loginController.receiveValidToken();
    }

    @And("I send POST HTTP request for {string} Registration with credentials as: {string}, {string}, and {string}")
    public void iSendPOSTHTTPRequestForRegistrationWithCredentialsAsAnd(String status, String email, String password, String fullname) {
        registrationController.postRegisterHttpRequest(status, email, password, fullname);
    }
    @And("I receive matching user data on response as: {string}, {string}, and {string}")
    public void iReceiveMatchingUserDataOnResponseAsAnd(String email, String password, String fullname) {
        registrationController.receiveMatchingValue(email,password,fullname);
    }

    @And("I send POST HTTP request given already registered email with: {string} and {string}")
    public void iSendPOSTHTTPRequestGivenAlreadyRegisteredEmailWithAnd(String password, String fullname) {
        registrationController.postRegisterHttpRequestForAlreadyRegistered(password,fullname);
    }

    @And("I receive token on response")
    public void iReceiveTokenOnResponse() {
        loginController.receiveValidToken();
    }

    @And("I send POST HTTP request for {string} Login for the previously created as: {string}, {string}")
    public void iSendPOSTHTTPRequestForLoginForThePreviouslyCreatedAs(String type, String email, String password) {
        String previousEmail = registrationController.fetchCurrentEmail();
        loginController.getHttpRequestWithoutRegistration(type, type.equals("missing attributes")?email:previousEmail,password);
    }

    @When("I send GET HTTP request for info API given {string} token")
    public void iSendGETHTTPRequestForInfoAPIGivenToken(String token) {
        loginController.getHTTPRequestUserData(token);
    }
}
