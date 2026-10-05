package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> getAllPayments();
    Payment findPaymentByOrderId(long orderId);
    Payment findPaymentById(long id);
    void savePayment(Payment payment);
}