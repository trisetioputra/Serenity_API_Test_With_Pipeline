Feature: Category

  Scenario Outline: [POSITIVE] [AUT-17] POST- Check POST Category API given valid required attributes
    Given I set "POST" endpoints
    When I send POST Category HTTP request with data as:"<name>", "<description>"
    Then I receive valid HTTP response code "200"
    And I receive matching category response with the given input: "<name>", "<description>"
    And I receive the newly created ID
    Examples:
    | name                 | description                                         |
    | testing product      | this is the product description                     |
    | testing product      | null                                                |

  Scenario Outline: [NEGATIVE] [AUT-18] POST- Check POST Category API given missing required attributes
    Given I set "POST" endpoints
    When I send POST Category HTTP request with data as:"<name>", "<description>"
    Then I receive valid HTTP response code "500"
    And I receive error message on response
    Examples:
      | name                 | description                                         |
      | null                 | this is the product description                     |

  Scenario Outline: [NEGATIVE] [AUT-19] POST- Check POST Category API given missing required attributes
    Given I set "POST" endpoints
    When I send POST Category HTTP request with mutation on "<mutation>" and data as:"<name>", "<description>"
    Then I receive valid HTTP response code "400"
    And I receive error message on response
    Examples:
      | name                 | description                                         |  mutation        |
      | 15                   | this is the product description                     |  name            |
      | testing              | 15                                                  |  description     |

 Scenario Outline: [POSITIVE] [AUT-20] GET- Check GET Category By ID API given valid product ID
    Given I set "GET" endpoints
    When I send POST Category HTTP request with data as:"<name>", "<description>"
    Then I receive valid HTTP response code "200"
    When I send GET Category By ID HTTP request given previously created ID
    Then I will see the same category data as: "<name>" and "<description>"
    Examples:
    | name                 | description                                         |
    | testing product      | this is the product description                     |

   Scenario Outline: [NEGATIVE] [AUT-21] GET- Check GET Category By ID API will fail given invalid product ID
      Given I set "GET" endpoints
      When I send GET Category By ID HTTP request given invalid ID as "<invalid ID>"
      Then I receive valid HTTP response code "404"
      And I receive error message on response
      Examples:
         | invalid ID |
         | 1          |

 Scenario Outline: [POSITIVE] [AUT-22] DELETE- Check DELETE Category By ID API given valid product ID
    Given I set "DELETE" endpoints
    When I send POST Category HTTP request with data as:"<name>", "<description>"
    Then I receive valid HTTP response code "200"
    When I send DELETE Category By ID HTTP request given previously created ID
    Then I receive valid HTTP response code "200"
    When I send GET Category By ID HTTP request given previously created ID
    Then I receive valid HTTP response code "404"
    Examples:
    | name                 | description                                         |
    | testing product      | this is the product description                     |

   Scenario: [POSITIVE] [AUT-23] GET - Check GET All Category API
    Given I set "GET" endpoints
    When I send GET All Category HTTP request
    Then I receive valid HTTP response code "200"
    And I receive array type for the retrieved data
    And I receive the correct attributes for category object within array