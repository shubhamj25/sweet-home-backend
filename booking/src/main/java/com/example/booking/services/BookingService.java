package com.example.booking.services;

import com.example.booking.entities.Booking;
import com.example.booking.exceptions.BookingNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    public Booking getBookingDetails(int id) throws BookingNotFoundException;

    public Booking updateBooking(int id,Booking booking);
    public boolean deleteMovie(int id);

    public List<Booking> getAllBookings();

    public Booking addBooking(Booking booking);
}
