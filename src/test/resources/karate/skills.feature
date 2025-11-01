Feature: Skill Tracker API Test

    Background:
    * url 'http://localhost:8080'
    * def apiPath = '/api/skills'

    Scenario: Créér un nouveau skill
    Given path apiPath
    And request { name: 'Karate Testing'}
    When method post
    Then status 201
    And match response.name == 'Karate Testing'
    And match response.progress == 0
    And match response.id == '#notnull'

    Scenario: Récupérer tous les skills
    #On crée d'abord un skill
    Given path apiPath
    And request {name: 'React'}
    When method post
    Then status 201
    And def skillId = response.id

    #On récupère la liste
    Given path apiPath
    When method get
    Then status 200
    And match response == '#array'
    And match response[*].name contains 'React'