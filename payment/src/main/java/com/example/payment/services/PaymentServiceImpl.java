package com.example.payment.services;

import com.example.payment.dao.TransactionDao;
import com.example.payment.entities.Transaction;
import com.example.payment.exceptions.InvalidPaymentModeException;
import com.example.payment.exceptions.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    TransactionDao transactionDao;
    @Override
    public Transaction getTransactionDetails(int id) {
        return transactionDao.findById(id).orElseThrow(() -> new TransactionNotFoundException("No transaction exists for this id"));
    }

    @Override
    public boolean deleteTransaction(int id) {
        try{
            transactionDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.findAll();
    }

    @Override
    public Transaction processTransaction(Transaction trnx) throws InvalidPaymentModeException{
        if(!Objects.equals(trnx.getPaymentMode(), "UPI") && !Objects.equals(trnx.getPaymentMode(), "CARD")) throw new InvalidPaymentModeException("Invalid mode of payment");
        Transaction newTransaction = Transaction.builder()
                .paymentMode(trnx.getPaymentMode())
                .bookingId(trnx.getBookingId())
                .upiId(trnx.getUpiId())
                .cardNumber(trnx.getCardNumber())
                .build();
        return transactionDao.save(newTransaction);
    }
}
