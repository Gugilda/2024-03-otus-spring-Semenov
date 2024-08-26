package ru.otus.hw.repositories;

import java.util.Optional;
import ru.otus.hw.models.Comment;

public interface CommentRepository {

    Comment save(Comment comment);

    void deleteById(String commentId);

    Optional<Comment> findById(String commentId);

}
