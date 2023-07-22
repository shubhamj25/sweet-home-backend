package com.example.booking.dao;

import com.example.booking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  BookingDao extends JpaRepository<Booking, Integer> { }
