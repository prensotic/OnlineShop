package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Order;
import ru.loginov.dev.entities.Payment;
import ru.loginov.dev.exceptions.paymentExceptions.PaymentNotFoundException;
import ru.loginov.dev.repositories.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public List<Payment> getAllPayments(){
        return paymentRepository.getAllPayments();
    }

    @Transactional
    public Payment findPaymentById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        Payment payment = paymentRepository.findPaymentById(id);

        if(payment == null) throw new PaymentNotFoundException("Платеж с ID: " + id + " не найден");

        return payment;
    }

    @Transactional
    public void createPayment(Order order, String paymentMethod){
        if(order == null) throw new IllegalArgumentException("Заказ указан некорректно");

        if(paymentMethod == null || paymentMethod.isBlank())
            throw new IllegalArgumentException("Способ платежа указан некорректно");

        paymentRepository.savePayment(new Payment(order, order.getAmount(), paymentMethod));
    }

    @Transactional
    public void markAsPaid(long paymentId) {
        Payment payment = findPaymentById(paymentId);

        if (!"PENDING".equals(payment.getStatus()))
            throw new IllegalStateException("Платёж нельзя перевести в статус PAID");

        payment.setStatus("PAID");
    }

    @Transactional
    public void markAsFailed(long paymentId) {
        Payment payment = findPaymentById(paymentId);

        if (!"PENDING".equals(payment.getStatus()))
            throw new IllegalStateException("Платёж нельзя перевести в статус FAILED");

        payment.setStatus("FAILED");
    }

    @Transactional
    public void refundPayment(long paymentId) {
        Payment payment = findPaymentById(paymentId);

        if (!"PAID".equals(payment.getStatus()))
            throw new IllegalStateException("Возврат возможен только для оплаченного платежа");

        payment.setStatus("REFUNDED");
    }
}