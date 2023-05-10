Feature: Registration

  Scenario Outline: [POSITIVE] [AUT-24] POST - Check POST Registration for valid new user
    Given I set "POST" endpoints
    And I send POST HTTP request for "successful" Registration with credentials as: "randomized email", "<password>", and "<full name>"
    Then I receive valid HTTP response code "200"
    And I receive matching user data on response as: "randomized email", "<password>", and "<full name>"
    Examples:
      | password    | full name      |
      | testing123  | Testing man    |

  Scenario Outline: [NEGATIVE] [AUT-25] POST - Check POST Registration given empty values on required attributes
    Given I set "POST" endpoints
    And I send POST HTTP request for "failed" Registration with credentials as: "<email>", "<password>", and "<full name>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | email       | password    | full name       |
      | email       | testing123  | null            |
      | email       | null        | Testing man     |
      | null        | testing123  | Testing man     |

  Scenario Outline: [NEGATIVE] [AUT-26] POST - Check POST Registration for already registered user
    Given I set "POST" endpoints
    And I send POST HTTP request for "successful" Registration with credentials as: "randomized email", "<password>", and "<full name>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request given already registered email with: "<password>" and "<full name>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | password    | full name      |
      | testing123  | Testing man    |



