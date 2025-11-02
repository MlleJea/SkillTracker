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

    Scenario: Récupérer un skill par son Id
    #On crée d'abord un skill
    Given path apiPath
    And request {name: 'React'}
    When method post
    Then status 201
    * def skillId = response.id

    #On le cherche par ID
    Given path apiPath + '/' + skillId
    When method get
    Then status 200
    And match response.id == skillId
    And match response.name == 'React'

    Scenario: Mettre à jour la progression d'un skill
    #On crée d'abord un skill
    Given path apiPath
    And request {name: 'Karate'}
    When method post
    Then status 201
    * def skillId = response.id

    #On ajoute la progression
    Given path apiPath + '/' + skillId + '/progress'
    And request {progress : 30 }
    When method put
    Then status 200
    And match response.progress == 30

    #On vérifie que c'est OK
    Given path apiPath + '/' + skillId
    When method get
    Then status 200
    And response.progress == 30

    Scenario: Mettre à jour la progression au delà de 100
    #On crée un skill
    Given path apiPath
    And request {name: 'Next'}
    When method post
    Then status 201
    * def skillId = response.id

    #On met a jour sa progression à 150
    Given path apiPath + '/' + skillId + '/progress'
    And request {progress : 150}
    When method put
    Then status 200
    And match response.progress == 100

    Scenario: Supprimer un skill
    #On crée un skill
    Given path apiPath
    And request {name: 'TS'}
    When method post
    Then status 201
    * def skillId = response.id

    #On le supprime
    Given path apiPath + '/' + skillId
    When method delete
    Then status 204

    #On le cherche
    Given path apiPath + '/' + skillId
    When method get
    Then status 404

    Scenario: Créer un skill avec un nom vide (devrait échouer)
    Given path apiPath
    And request {name: ''}
    When method post
    Then status 400

    Scenario: Mettre à jour un skill inexistant (devrait échouer)
    Given path apiPath + '/242424/progress'
    And request { progress : 40 }
    When method put
    Then status 404

    Scenario: Supprimer un skill inexistant (devrait échouer)
    Given path apiPath + '252525'
    When method delete
    Then status 404