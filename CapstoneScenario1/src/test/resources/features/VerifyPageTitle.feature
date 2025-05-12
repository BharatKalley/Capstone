Feature: Verify Page Title and Image Carousel

  Scenario: Verify the title of the page and image carousel functionality
    Given I launch the application "http://webdriveruniversity.com/index.html"
    Then I verify the title of the page is "WebdriverUniversity.com (New Approach To Learning)"
    When I click on the "IFRAME" link
    Then I switch to the new tab
    And I verify the image is present
    When I click on the right arrow button
    Then I verify the images are changing accordingly

