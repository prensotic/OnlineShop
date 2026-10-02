package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findCommentsByProductId(long productId);
    Comment findCommentById(long id);
    void saveComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(long id);
}