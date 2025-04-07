package com.example.book.intergration.controller;

import com.example.book.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAndGetBook() {
        Book book = new Book(10L, "Integration Test Book", "Author");
        ResponseEntity<Book> response = restTemplate.postForEntity("/books", book, Book.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Book result = restTemplate.getForObject("/books/10", Book.class);
        assertEquals("Integration Test Book", result.getTitle());
    }

    @Test
    public void testBulkCreateBooks() {
        List<Book> books = Arrays.asList(
                new Book(20L, "Book One", "Author A"),
                new Book(21L, "Book Two", "Author B")
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Book>> request = new HttpEntity<>(books, headers);

        ResponseEntity<Book[]> response = restTemplate.postForEntity("/books/bulk", request, Book[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }
}