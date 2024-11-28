package com.assignment.dao.custom;

import com.assignment.dao.CrudDAO;
import com.assignment.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment> {
    public Payment findPaymentById(String paymentId) throws Exception;
}
