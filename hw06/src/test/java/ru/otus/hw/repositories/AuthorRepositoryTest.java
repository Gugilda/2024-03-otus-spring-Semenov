package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AuthorRepositoryImpl.class})
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepositoryImpl authorRepository;
    @Autowired
    private TestEntityManager entityManager;

    private static final long FIRST_AUTHOR_ID = 1L;

    @Test
    void findByIdTest() {
        var actualAuthor = authorRepository.findById(FIRST_AUTHOR_ID);
        var expectedAuthor = entityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isPresent()
                .get()
                .isEqualTo(expectedAuthor);
    }

    @Test
    void findAllTest() {
        var actualAuthors = authorRepository.findAll();
        var expectedAuthors = entityManager
                .getEntityManager()
                .createQuery("select a from Author a")
                .getResultList();
        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors);
    }
}