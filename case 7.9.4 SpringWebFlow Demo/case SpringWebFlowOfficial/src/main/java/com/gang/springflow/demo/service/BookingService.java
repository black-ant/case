package com.gang.springflow.demo.service;

import com.gang.springflow.demo.entity.Booking;
import com.gang.springflow.demo.entity.Hotel;
import com.gang.springflow.demo.to.SearchCriteria;

import java.util.List;

/**
 * A service interface for retrieving hotels and bookings from a backing repository. Also supports the ability to cancel
 * a booking.
 */
public interface BookingService {

    /**
     * Find bookings made by the given user
     * @param username the user's name
     * @return their bookings
     */
    List<Booking> findBookings(String username);

    /**
     * Find hotels available for booking by some criteria.
     * @param criteria the search criteria
     * @param firstResult the index of the first result to return
     * @param sortBy the field to sort by
     * @param ascending true if the sorting should be in ascending order, false for descending
     * @return a list of hotels meeting the criteria
     */
    List<Hotel> findHotels(SearchCriteria criteria, int firstResult, String sortBy, boolean ascending);

    /**
     * Find hotels by their identifier.
     * @param id the hotel id
     * @return the hotel
     */
    Hotel findHotelById(Long id);

    /**
     * Create a new, transient hotel booking instance for the given user.
     * @param hotelId the hotelId
     * @param userName the user name
     * @return the new transient booking instance
     */
    Booking createBooking(Long hotelId, String userName);

    /**
     * Persist the booking to the database
     * @param booking the booking
     */
    void persistBooking(Booking booking);

    /**
     * Cancel an existing booking.
     * @param id the booking id
     */
    void cancelBooking(Booking booking);

    /**
     * Return the total number of hotels for the given criteria.
     * @param criteria the criteria to use
     * @return the number of matching hotels
     */
    int getNumberOfHotels(SearchCriteria criteria);

}
