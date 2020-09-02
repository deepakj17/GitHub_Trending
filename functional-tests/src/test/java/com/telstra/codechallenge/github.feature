# See
# https://github.com/intuit/karate#syntax-guide
# for how to write feature scenarios
Feature: As an api user I want to retrieve trending repos from GitHub

  Scenario: Get Trending Repos
    Given url microserviceUrl
    And path '/gitHub/trendingRepos?repoCount=3'
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    # see https://github.com/intuit/karate#schema-validation
    And match response == 
    """
    { 
      {
        language : '#string',
        description : '#string',
        name : '#string',
        html_url : '#string',
        watchers_count : '#number'
      }
    }
    """