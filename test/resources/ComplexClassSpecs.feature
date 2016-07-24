# Created by jcespinoza at 7/24/2016
Feature: Tests for a complex class
  In order to make sure my class works correctly
  As a the developer who created it
  I want to be able to use its methods and check the results

  Scenario: Totally correct Sum of integers
    Given an integer "a" with value 5
    And an integer "b" with value 8
    When I calculate the sum of "a" and "b"
    Then it should return 13