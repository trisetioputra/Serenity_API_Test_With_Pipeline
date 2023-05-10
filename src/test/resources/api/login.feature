Feature: Login

  Scenario Outline: [POSITIVE] [AUT-27] POST - Check POST Login for valid new user
    Given I set "POST" endpoints
    And I send POST HTTP request for "successful" Registration with credentials as: "randomized email", "<password>", and "<full name>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "successful" Login for the previously created as: "randomized email", "<password>"
    Then I receive valid HTTP response code "200"
    And I receive token on response
    Examples:
      | password    | full name      |
      | testing123  | Testing man    |

  Scenario Outline: [NEGATIVE] [AUT-28] POST - Check POST Login for invalid credential
    Given I set "POST" endpoints
    And I send POST HTTP request for "successful" Registration with credentials as: "randomized email", "<password>", and "<full name>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "wrong password" Login for the previously created as: "randomized email", "<password>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | password    | full name      |
      | testing123  | Testing man    |

  Scenario Outline: [NEGATIVE] [AUT-29] POST - Check POST Login for unregistered email
    Given I set "POST" endpoints
    And I send POST HTTP request for "unregistered" Login for the previously created as: "randomized email", "<password>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | password    |
      | testing123  |

  Scenario Outline: [NEGATIVE] [AUT-30] POST - Check POST Login for missing attributes
    Given I set "POST" endpoints
    And I send POST HTTP request for "missing attributes" Login for the previously created as: "<email>", "<password>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | password    | email           |
      | testing123  | null            |
      | null        | test@gmail.com  |