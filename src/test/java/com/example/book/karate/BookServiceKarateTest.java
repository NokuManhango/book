package com.example.book.karate;

import com.intuit.karate.junit5.Karate;

public class BookServiceKarateTest {

    @Karate.Test
    Karate testBookApi() {
        return Karate.run("book-api").relativeTo(getClass());
    }
}
