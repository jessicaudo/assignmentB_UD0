Feature: Gmail

  Scenario: Sending email with one manually attached image
    Given I am on Gmail compose window
    When I press "Add files"
    And I select the image I want to attach from my computer directory
    And I click “Open”
    Then the image should be uploaded to my email file
    And when I press “Send”, the image should be sent to the recipient


  Scenario: Sending email with drag-and-drop attached image
    Given I am signed into my Gmail account and in the “Compose” window
    And I have an image already attached to my email
    When I press “Attach files” icon
    I can drag an image I want to attach from my computer directory
    And drop image in my email window
    Then the image should be uploaded to my email file
    And when I press “Send”, the image should be sent to the recipient


  Scenario: Sending email with greater than one attached image
    Given I am signed into my Gmail account and in the “Compose” window
    And I have an image already attached to my email
    When I press “Attach files” icon
    And I select the image I want to attach from my computer directory
    Then the image should be uploaded to my email file
    And both images should be shown in my email
    And when I press “Send”, the image should be sent to the recipient.


  Scenario: Not attaching an image
    Given I am signed into my Gmail account and in the “Compose” window
    When I press “Attach files” icon
    And I do not select an image I want to attach from my computer directory
    And I click “Open”
    Then no action occurs
    I can press “Cancel”
    Then return to the Compose window
    When I press “Send” email is sent without attachment

