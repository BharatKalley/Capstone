Feature: Verify Page Title and Image Carousel

  Scenario: Verify the title of the page and image carousel functionality
    Given I launch the application "http://webdriveruniversity.com/index.html"
    Then I verify the title of the page is "WebdriverUniversity.com (New Approach To Learning)"
    # When I click on the "IFRAME" link
    # Then I switch to the new tab
    # And I verify the image is present
    # When I click on the right arrow button
    # Then I verify the images are changing accordingly

  Scenario: When clicked on IFRAME link verify I switched to new tab
    Given I launch the application "http://webdriveruniversity.com/index.html"
    When I click on the "IFRAME" link
    Then I switch to the new tab

  Scenario: When switched to new tab verify the image is present
   Given I launch the application "http://webdriveruniversity.com/index.html"
    When I click on the "IFRAME" link
    Then I switch to the new tab
    And I verify the image is present

  Scenario: When clicked on next button in new tab verify the image is changing
    Given I launch the application "http://webdriveruniversity.com/index.html"
    When I click on the "IFRAME" link
    Then I switch to the new tab
    And I verify the image is present
    When I click on the right arrow button
    Then I verify the images are changing accordingly



