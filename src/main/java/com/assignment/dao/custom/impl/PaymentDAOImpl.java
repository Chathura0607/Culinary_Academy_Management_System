package com.assignment.dao.custom.impl;

import com.assignment.config.FactoryConfiguration;
import com.assignment.dao.custom.PaymentDAO;
import com.assignment.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Payment entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.update(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String ID) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        Payment payment = session.get(Payment.class, ID);
        if (payment != null) {
            session.delete(payment);
            tx.commit();
            session.close();
            return true;
        } else {
            tx.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        List<Payment> all = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        all = session.createQuery("from Payment ").list();
        transaction.commit();
        session.close();
        return all;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String lastID = (String) session.createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastID != null) {
                int id = Integer.parseInt(lastID.replace("P", "")) + 1;
                return "P" + String.format("%03d", id);
            } else {
                return "P001";
            }
        }
    }

    @Override
    public boolean IdExists(String id) throws SQLException, ClassNotFoundException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "SELECT COUNT(p.id) FROM Payment p WHERE p.id = :id";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("id", id);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        }
    }

    @Override
    public Payment findPaymentById(String paymentId) throws Exception {
        Transaction transaction = null;
        Payment payment = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            Query<Payment> query = session.createQuery("FROM Payment p WHERE p.id = :id", Payment.class);
            query.setParameter("id", paymentId);
            payment = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }

        return payment;
    }
}
