Feature: Product

  Scenario: [POSITIVE] [AUT-1] GET - Check GET All Products API
    Given I set "GET" endpoints
    When I send GET HTTP request
    Then I receive valid HTTP response code "200"
    And I receive array type for the retrieved data
    And I receive the correct attributes for object within array

  Scenario Outline: [POSITIVE] [AUT-2] POST- Check POST Products API given all valid attributes
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I receive matching response with the given input: "<name>", "<price>", "<description>", and "<categories>"
    And I receive the newly created ID
    Examples:
    | name                 | price   | description                                         | categories                 |
    | testing product      | 10000   | this is the product description                     | random existing category   |


  Scenario Outline: [POSITIVE] [AUT-3] POST- Check POST Products API given missing non-required attributes
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I receive matching response with the given input: "<name>", "<price>", "<description>", and "<categories>"
    And I receive the newly created ID
    Examples:
      | name                 | price   | description                                         | categories                 |
      | testing product      | null    | this is the product description                     | random existing category   |
      | testing product      | 1000    | null                                                | random existing category   |
      | testing product      | 10000   | this is the product description                     | null                       |

  Scenario Outline: [POSITIVE] [AUT-4] POST- Check POST Products API given half non-existing ID on categories attributes
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I receive matching response with the given input: "<name>", "<price>", "<description>", and "<categories>"
    And I receive the newly created ID
    Examples:
      | name                 | price   | description                                         | categories                 |
      | testing product      | 1000    | this is the product description                     | half-valid                 |

  Scenario Outline: [NEGATIVE] [AUT-5] POST- Check POST Products API will return error given empty required attributes
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "500"
    And I receive error message on response
    Examples:
      | name                 | price   | description                                         | categories                 |
      | null                 | 1000    | this is the product description                     | random existing category   |

  Scenario Outline: [NEGATIVE] [AUT-6] POST- Check POST Products API will return error given data type mutation on request
    Given I set "POST" endpoints
    When I send POST HTTP request with mutation on "<mutation>" and data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | name                 | price   | description                                         | categories                 | mutation    |
      | 12                   | 1000    | this is the product description                     | random existing category   | name        |
      | test                 | satu    | this is the product description                     | random existing category   | price       |
      | test                 | 1000    | 123123                                              | random existing category   | description |
      | test                 | 1000    | this is the product description                     | random existing category   | categories  |

  Scenario Outline: [POSITIVE] [AUT-7] GET - Check GET Products By ID API for a recently created product
    Given I set "GET" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    And I send GET By ID HTTP request for the previously created product ID
    Then I receive valid HTTP response code "200"
    Then I receive matching response with the given input: "<name>", "<price>", "<description>", and "<categories>"
    Examples:
      | name                 | price   | description                                         | categories                 |
      | testing product      | 10000   | this is the product description                     | random existing category   |

  Scenario: [NEGATIVE] [AUT-8] GET - Check GET Products By ID API for a non-existing ID
    Given I set "GET" endpoints
    When I send GET By ID HTTP request for non-existing ID: "1"
    Then I receive valid HTTP response code "404"
    And I receive error message on response

  Scenario Outline: [POSITIVE] [AUT-9] DELETE - Check DELETE Products By ID API for a recently created product
    Given I set "DELETE" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    And I send DELETE By ID HTTP request for the previously created product ID
    Then I receive valid HTTP response code "200"
    When I send GET By ID HTTP request for the previously created product ID
    Then I receive valid HTTP response code "404"
    Examples:
      | name                 | price   | description                                         | categories                 |
      | testing product      | 10000   | this is the product description                     | random existing category   |


  Scenario Outline: [POSITIVE] [AUT-10] POST - Check POST and GET Products Rating for a recently created product
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for product rating with data as: "<count>" and given "valid" token
    Then I receive valid HTTP response code "200"
    And I will receive the same rating "<count>" in response body
    When I send GET HTTP request for product rating for the same product
    Then I will see the data matching with "<count>"
    Examples:
      | name                 | price   | description                                         | categories                 | email                 | password    | count   |
      | testing product      | 10000   | this is the product description                     | random existing category   | testingdev@gmail.com  | testing123  | 5       |


  Scenario Outline: [NEGATIVE] [AUT-11] POST - Check POST Products Rating will fail given invalid count
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for product rating with data as: "<count>" and given "valid" token
    Then I receive valid HTTP response code "500"
    And I receive error message on response
    Examples:
      | name                 | price   | description                                         | categories                 | email                 | password    | count   |
      | testing product      | 10000   | this is the product description                     | random existing category   | testingdev@gmail.com  | testing123  | 6       |


  Scenario Outline: [NEGATIVE] [AUT-12] POST - Check POST Products Rating will fail given invalid authorization
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    When I send POST HTTP request for product rating with data as: "<count>" and given "<type>" token
    Then I receive valid HTTP response code "401"
    And I receive error message on response
    Examples:
      | name                 | price   | description                                         | categories                 | count   | type         |
      | testing product      | 10000   | this is the product description                     | random existing category   | 4       | no token     |
      | testing product      | 10000   | this is the product description                     | random existing category   | 4       | wrong token  |

  Scenario Outline: [NEGATIVE] [AUT-15] POST - Check POST Products Rating will fail given invalid product ID
    Given I set "POST" endpoints
    When I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for invalid product's rating with valid data as: "<rating>" and given "valid" token
    Then I receive valid HTTP response code "500"
    And I receive error message on response
    Examples:
      | email                 | password    | rating    |
      | testingdev@gmail.com  | testing123  | 1         |


  Scenario Outline: [POSITIVE] [AUT-13] POST - Check POST and GET Products Comments for a recently created product
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for product comment with data as: "<comment>" and given "valid" token
    Then I receive valid HTTP response code "200"
    And I will receive the same comment "<comment>" in response body
    When I send GET HTTP request for product comment for the same product
    Then I will see the comment data matching with "<comment>"
    Examples:
      | name                 | price   | description                                         | categories                 | email                 | password    | comment                                                                           |
      | testing product      | 10000   | this is the product description                     | random existing category   | testingdev@gmail.com  | testing123  | the games are great including Gran Turismo 7 but sadly GT4 is much better         |

  Scenario Outline: [NEGATIVE] [AUT-14] POST - Check POST Products Comment will fail given invalid content
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    And I send POST HTTP request for "successful" Login with credentials as: "<email>", "<password>"
    Then I receive valid token
    When I send POST HTTP request for product comment with invalid data as: "<comment>" and given "valid" token
    Then I receive valid HTTP response code "500"
    And I receive error message on response
    Examples:
      | name                 | price   | description                                         | categories                 | email                 | password    | comment                                                                           |
      | testing product      | 10000   | this is the product description                     | random existing category   | testingdev@gmail.com  | testing123  | 15                                                                                |


  Scenario Outline: [NEGATIVE] [AUT-16] POST - Check POST Products Comments will fail given invalid authorization
    Given I set "POST" endpoints
    When I send POST HTTP request with data as:"<name>", "<price>", "<description>", and "<categories>"
    Then I receive valid HTTP response code "200"
    When I send POST HTTP request for product comment with data as: "<comment>" and given "<type>" token
    Then I receive valid HTTP response code "401"
    And I receive error message on response
    Examples:
      | name                 | price   | description                                         | categories                 | comment                                                                           | type        |
      | testing product      | 10000   | this is the product description                     | random existing category   | the games are great including Gran Turismo 7 but sadly GT4 is much better         | no token    |
      | testing product      | 10000   | this is the product description                     | random existing category   | the games are great including Gran Turismo 7 but sadly GT4 is much better         | wrong token |

