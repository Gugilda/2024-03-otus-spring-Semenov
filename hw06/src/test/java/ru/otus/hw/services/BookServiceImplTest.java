package ru.otus.hw.services;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl subj;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        List<Book> bookList = new ArrayList<>();
        Book book1 = new Book();
        bookList.add(book1);

        Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        List<Book> actual = subj.findAll();

        assertEquals(bookList.size(), actual.size());
    }

}