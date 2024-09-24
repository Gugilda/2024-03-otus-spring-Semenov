package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;
import ru.otus.hw.models.Comment;

public interface CommentRepository {

    Comment save(Comment comment);

    void deleteById(long commentId);

    Optional<Comment> findById(long commentId);

    List<Comment> findAll();
}
