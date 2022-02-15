package com.gang.client.repository;


import com.gang.client.po.CarBooking;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author <a href="http://youngitman.tech">青年IT男</a>
 * @version v1.0.0
 * @className CarBookingRepository
 * @description
 * @date 2019-12-16 15:46
 * @JunitTest: {@link  }
 **/
public interface CarBookingRepository extends CrudRepository<CarBooking, Long> {

    @Query("SELECT * FROM carbooking WHERE name LIKE :name")
    List<CarBooking> findByName(@Param("name") String name);

    @Query("SELECT * FROM carbooking WHERE uuid = :uuid")
    List<CarBooking> findByUuid(@Param("uuid") String uuid);

}
