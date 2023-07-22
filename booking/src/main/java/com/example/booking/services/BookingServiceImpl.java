package com.example.booking.services;

import com.example.booking.dao.BookingDao;
import com.example.booking.entities.Booking;
import com.example.booking.exceptions.BookingNotFoundException;
import com.example.booking.utils.BookingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingDao bookingDao;

    @Override
    public Booking getBookingDetails(int id) throws BookingNotFoundException {
        return bookingDao.findById(id).orElseThrow(() -> new BookingNotFoundException("Invalid Booking Id"));
    }

    @Override
    public Booking updateBooking(int id, Booking booking) {
        Booking updatedBooking = Booking.builder()
                .bookingId(id)
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .aadharNumber(booking.getAadharNumber())
                .numOfRooms(booking.getNumOfRooms())
                .roomNumbers(BookingUtils.getRandomNumbers(booking.getNumOfRooms()).toString())
                .roomPrice(booking.getNumOfRooms() * 1000 * Period.between(booking.getFromDate().toLocalDate(),booking.getToDate().toLocalDate()).getDays())
                .transactionId(booking.getTransactionId())
                .build();
        return bookingDao.save(updatedBooking);
    }

    @Override
    public boolean deleteMovie(int id) {
        try{
            bookingDao.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDao.findAll();
    }

    @Override
    public Booking addBooking(Booking booking) {
        return bookingDao.save(booking);
    }
}
