package com.example.book.unit.service;

import com.example.book.model.Book;
import com.example.book.repository.BookRepository;
import com.example.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAndFind() {
        Book book = new Book(1L, "Test Title", "Test Author");
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.save(book);
        Book found = bookService.findById(1L);

        assertNotNull(found);
        assertEquals("Test Title", found.getTitle());
    }

    @Test
    public void testDelete() {
        Long id = 2L;
        doNothing().when(bookRepository).deleteById(id);

        bookService.delete(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSaveAll() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Title1", "Author1"),
                new Book(2L, "Title2", "Author2")
        );
        when(bookRepository.saveAll(books)).thenReturn(books);

        List<Book> savedBooks = bookService.saveAll(books);

        assertEquals(2, savedBooks.size());
        verify(bookRepository, times(1)).saveAll(books);
    }
}