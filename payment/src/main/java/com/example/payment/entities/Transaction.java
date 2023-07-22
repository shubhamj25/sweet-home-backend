package com.example.payment.entities;

import com.example.payment.exceptions.InvalidPaymentModeException;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    private String paymentMode;

    @Column(nullable = false)
    private int bookingId;

    private String upiId;
    private String cardNumber;
}
