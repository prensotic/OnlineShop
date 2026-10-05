package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Comment;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.exceptions.commentExceptions.CommentNotFoundException;
import ru.loginov.dev.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Transactional
    public List<Comment> findCommentsByProductId(long productId){
        if(productId <= 0) throw new IllegalArgumentException("ID товара должен быть положительным");
        return commentRepository.findCommentsByProductId(productId);
    }

    @Transactional
    public Comment findCommentById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID комментария должен быть положительным");

        Comment comment = commentRepository.findCommentById(id);

        if(comment == null) throw new CommentNotFoundException("Комментарий с ID: " + id + " не найден");

        return comment;
    }

    @Transactional
    public void createComment(User user, Product product, String text){
        if(user == null) throw new IllegalArgumentException("Пользователь указан некорректно");

        if(product == null) throw new IllegalArgumentException("Товар указан некорректно");

        if(text == null || text.isBlank())
            throw new IllegalArgumentException("Содержание комментария указан некорректно");

        commentRepository.saveComment(new Comment(user, product, text));
    }

    @Transactional
    public void updateComment(long id, String text){
        Comment comment = findCommentById(id);

        if(text == null || text.isBlank())
            throw new IllegalArgumentException("Содержание комментария указан некорректно");

        comment.setText(text);
    }

    @Transactional
    public void deleteComment(long id){
        findCommentById(id);
        commentRepository.deleteComment(id);
    }
}