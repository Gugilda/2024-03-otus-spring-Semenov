package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment insert(String comment, long bookId) {
        return save(0, comment, bookId);
    }

    @Override
    public Comment update(long id, String comment) {
        return save(id, comment, 0);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(long id, String text, long bookId) {
        Comment comment;
        if (bookId != 0) {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book id not found: " + bookId));
            comment = new Comment(id, text, book);
        } else {
            comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment id not found: " + id));
            comment.setComment(text);
        }
        return commentRepository.save(comment);
    }
}
