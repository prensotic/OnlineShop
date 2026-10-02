package ru.loginov.dev.repositories.impl;

import org.hibernate.SessionFactory;
import ru.loginov.dev.entities.Comment;
import ru.loginov.dev.repositories.CommentRepository;

import java.util.List;

public class HibernateCommentRepository implements CommentRepository {
    private final SessionFactory sessionFactory;

    public HibernateCommentRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comment> findCommentsByProductId(long productId) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select c from Comment c where c.product.id = :productId", Comment.class)
                .getResultList();
    }

    @Override
    public Comment findCommentById(long id) {
        return sessionFactory.getCurrentSession().get(Comment.class, id);
    }

    @Override
    public void saveComment(Comment comment) {
        sessionFactory.getCurrentSession().persist(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        sessionFactory.getCurrentSession().merge(comment);
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = findCommentById(id);
        if(comment != null) sessionFactory.getCurrentSession().remove(comment);
    }
}
