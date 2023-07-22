package com.example.payment.services;

import com.example.payment.entities.Transaction;
import com.example.payment.exceptions.InvalidPaymentModeException;
import com.example.payment.exceptions.TransactionNotFoundException;

import java.util.List;

public interface PaymentService {
    public Transaction getTransactionDetails(int id) throws TransactionNotFoundException;

    public boolean deleteTransaction(int id);

    public List<Transaction> getAllTransactions();

    public Transaction processTransaction(Transaction transaction) throws InvalidPaymentModeException;
}
