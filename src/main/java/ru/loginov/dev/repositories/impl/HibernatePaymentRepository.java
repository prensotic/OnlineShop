package ru.loginov.dev.repositories.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Payment;
import ru.loginov.dev.repositories.PaymentRepository;

import java.util.List;

@Repository
public class HibernatePaymentRepository implements PaymentRepository {
    private final SessionFactory sessionFactory;

    public HibernatePaymentRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Payment> getAllPayments() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select p from Payment p", Payment.class)
                .getResultList();
    }

    @Override
    public Payment findPaymentByOrderId(long orderId) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select p from Payment p where p.order.id = :orderId", Payment.class)
                .setParameter("orderId", orderId)
                .uniqueResult();
    }

    @Override
    public Payment findPaymentById(long id) {
        return sessionFactory.getCurrentSession().get(Payment.class, id);
    }

    @Override
    public void savePayment(Payment payment) {
        sessionFactory.getCurrentSession().persist(payment);
    }
}
