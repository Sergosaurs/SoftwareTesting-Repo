#shopping cart scenario

Feature: Cart actions

  Scenario Outline: Adding items to the cart and removing items from the cart

    When go to the main page of the store
    And add first item to the cart <number of times> times
    And go to the cart page
    And delete all the items from the shopping cart
    Then shopping cart must be empty

    # для эксперимента
    Examples:
      | number of times |
      | 1               |
      | 3               |