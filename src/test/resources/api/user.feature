Feature: user

  Scenario Outline: [POSITIVE] [AUT-31] GET - Check GET Login for valid new user
    Given I set "POST" endpoints
    And I send POST HTTP request for "successful" Registration with credentials as: "randomized email", "<password>", and "<full name>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "successful" Login for the previously created as: "randomized email", "<password>"
    Then I receive valid HTTP response code "200"
    And I receive token on response
    When I send GET HTTP request for info API given "valid" token
    Then I receive valid HTTP response code "200"
    And I receive matching user data on response as: "randomized email", "<password>", and "<full name>"
    Examples:
      | password    | full name      |
      | testing123  | Testing man    |

  Scenario Outline: [NEGATIVE] [AUT-32] GET - Check GET Login for valid new user
    Given I set "GET" endpoints
    When I send GET HTTP request for info API given "<type>" token
    Then I receive valid HTTP response code "401"
    And I receive error message on response
    Examples:
      | type          |
      | wrong-token   |
      | missing-token |