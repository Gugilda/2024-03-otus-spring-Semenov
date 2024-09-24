package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class JpaAuthorRepositoryTest {

    private static final Long EXISTING_AUTHOR_ID_1 = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final Long EXISTING_AUTHOR_ID_3 = 3L;

    @Autowired
    private AuthorRepositoryImpl authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllTest() {
        var authorOne = entityManager.find(Author.class, EXISTING_AUTHOR_ID_1);
        var authorTwo = entityManager.find(Author.class, EXISTING_AUTHOR_ID_2);
        var authorThree = entityManager.find(Author.class, EXISTING_AUTHOR_ID_3);

        var actualAuthors = authorRepository.findAll();

        assertThat(actualAuthors).containsExactly(authorOne, authorTwo, authorThree);
    }

    @Test
    void findByIdTest() {
        var expected = entityManager.find(Author.class, EXISTING_AUTHOR_ID_1);
        var actual = authorRepository.findById(EXISTING_AUTHOR_ID_1);

        assertThat(actual).isPresent()
                .hasValue(expected);
    }
}