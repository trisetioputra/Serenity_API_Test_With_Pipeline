Feature: : Order

  Scenario Outline: [POSITIVE] [AUT-33] POST - Check POST and GET Order for a recently created order
    Given I set "POST" endpoints
    When I fetch two product ID from GET all product API
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for order with quantity as: "<quantity>" and given "valid" token
    Then I receive valid HTTP response code "200"
    And I will receive the same quantity "<quantity>", user data, and product ID on response
    When I send GET All Order for a user using the matching token
    Then I receive valid HTTP response code "200"
    Then I will receive the same quantity "<quantity>", user data, and product ID on response
    When I send GET Order By ID request for the newly created order
    Then I receive valid HTTP response code "200"
    Then I will see the data matching with "<quantity>", user data, and product ID on response
    Examples:
      | email                 | password    | quantity   |
      | testingdev@gmail.com  | testing123  | 15         |


  Scenario Outline: [POSITIVE] [AUT-34] POST - Check POST order for a invalid product id
    Given I set "POST" endpoints
    When I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for order with quantity as: "<quantity>" and given "valid" token and invalid product ID
    Then I receive valid HTTP response code "200"
    And I will receive the same quantity "<quantity>", user data, and null product on response
    Examples:
      | email                 | password    | quantity   |
      | testingdev@gmail.com  | testing123  | 15         |

  Scenario Outline: [NEGATIVE] [AUT-35] POST - Check POST Order given invalid authorization
    Given I set "GET" endpoints
    When I fetch two product ID from GET all product API
    When I send POST HTTP request for order with quantity as: "<quantity>" and given "invalid" token
    Then I receive valid HTTP response code "401"
    And I receive error message on response
    Examples:
      | quantity   |
      | 15         |

  Scenario: [NEGATIVE] [AUT-36] GET - Check GET All order given invalid authorization
    Given I set "POST" endpoints
    When I send GET All Order for a user using invalid token
    Then I receive valid HTTP response code "401"
    And I receive error message on response

  Scenario Outline: [NEGATIVE] [AUT-37] GET - Check GET order by ID given invalid authorization
    Given I set "GET" endpoints
    When I fetch two product ID from GET all product API
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for order with quantity as: "<quantity>" and given "valid" token
    Then I receive valid HTTP response code "200"
    And I will receive the same quantity "<quantity>", user data, and product ID on response
    When I send GET Order By ID request for the newly created order with invalid token
    Then I receive valid HTTP response code "401"
    And I receive error message on response
    Examples:
      | email                 | password    | quantity   |
      | testingdev@gmail.com  | testing123  | 15         |

  Scenario Outline: [NEGATIVE] [AUT-38] GET - Check GET order by ID given invalid order ID
    Given I set "GET" endpoints
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send GET Order By ID request for the invalid order ID
    Then I receive valid HTTP response code "404"
    And I receive error message on response
    Examples:
      | email                 | password    |
      | testingdev@gmail.com  | testing123  |

