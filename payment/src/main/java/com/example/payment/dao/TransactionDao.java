package com.example.payment.dao;

import com.example.payment.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transaction,Integer> {
}
