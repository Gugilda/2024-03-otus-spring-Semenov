package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import ru.otus.hw.models.Comment;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findAll();

    Comment insert(String comment, long bookId);

    Comment update(long id, String comment);

    void deleteById(long id);
}
