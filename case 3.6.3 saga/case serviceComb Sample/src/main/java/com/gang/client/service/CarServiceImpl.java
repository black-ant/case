package com.gang.client.service;

import com.gang.client.po.CarBooking;
import com.gang.client.repository.CarBookingRepository;
import org.apache.servicecomb.pack.omega.transaction.annotations.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="http://youngitman.tech">青年IT男</a>
 * @version v1.0.0
 * @className BookingServiceImpl
 * @description
 * @date 2019-12-16 15:50
 * @JunitTest: {@link  }
 **/
@Service
public class CarServiceImpl implements ICarService {

    @Autowired
    private CarBookingRepository carBookingRepository;

    @Transactional
    @Override
    @Compensable(compensationMethod = "cancel")
    public void bookingCar(CarBooking booking) {
        if (booking.getAmount() > 10) {
            throw new IllegalArgumentException("can not order the cars large than ten");
        }
        booking.setId(null);
        booking.confirm();
        carBookingRepository.save(booking);
    }

    @Transactional
    @Override
    public void cancel(CarBooking booking) {
        List<CarBooking> cars = carBookingRepository.findByUuid(booking.getUuid());
        if (cars != null && cars.size()>0) {
            CarBooking car = cars.get(0);
            carBookingRepository.delete(car);
        }
    }

    @Override
    public Collection<CarBooking> getAllBookings() {
        return (Collection<CarBooking>) carBookingRepository.findAll();
    }

    @Override
    public void clearAllBookings() {
        carBookingRepository.deleteAll();
    }
}
