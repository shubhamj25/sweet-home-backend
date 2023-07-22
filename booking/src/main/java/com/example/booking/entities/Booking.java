package com.example.booking.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String aadharNumber;

    @Column(nullable = false)
    private int numOfRooms;

    private String roomNumbers;

    @Column(nullable = false)
    private int roomPrice;

    @Column(nullable = false)
    private int transactionId;

    private LocalDateTime bookedOn;
}
