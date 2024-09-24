package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import static org.hibernate.jpa.SpecHints.HINT_SPEC_FETCH_GRAPH;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Book> findById(long id) {
        var graph = entityManager.getEntityGraph("author-entity-graph");
        return Optional.ofNullable(entityManager.find(Book.class, id, Map.of(HINT_SPEC_FETCH_GRAPH, graph)));
    }

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery("select b from Book b", Book.class)
                .setHint(HINT_SPEC_FETCH_GRAPH, entityManager.getEntityGraph("author-entity-graph"))
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
    }

    private Book insert(Book book) {
        entityManager.persist(book);
        return book;
    }

    private Book update(Book book) {
        entityManager.merge(book);
        return book;
    }
}