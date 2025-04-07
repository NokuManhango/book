Feature: Book API End-to-End Test

  Scenario: Create a book and retrieve it
    Given url 'http://localhost:8280/books'
    And request { "id": 1, "title": "Test Book", "author": "Test Author" }
    When method post
    Then status 200
    And match response.title == 'Test Book'
    And match response.author == 'Test Author'

  Scenario: Retrieve all books
    Given url 'http://localhost:8280/books'
    When method get
    Then status 200

  Scenario: Retrieve a specific book by ID
    Given url 'http://localhost:8280/books/1'
    When method get
    Then status 200
    And match response.id == 1
    And match response.title == 'Test Book'
    And match response.author == 'Test Author'

  Scenario: Delete a book by ID
    Given url 'http://localhost:8280/books/1'
    When method delete
    Then status 200
