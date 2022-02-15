package com.gang.client.service;


import com.gang.client.po.CarBooking;

import java.util.Collection;

/**
 * @author <a href="http://youngitman.tech">青年IT男</a>
 * @version v1.0.0
 * @className ICarService
 * @description
 * @date 2019-12-16 15:49
 * @JunitTest: {@link  }
 **/
public interface ICarService {
    void bookingCar(CarBooking booking);
    void cancel(CarBooking booking);
    Collection<CarBooking> getAllBookings();
    void clearAllBookings();
}
