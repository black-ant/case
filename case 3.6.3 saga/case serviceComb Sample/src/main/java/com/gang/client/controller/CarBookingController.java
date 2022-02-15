/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gang.client.controller;

import com.gang.client.po.CarBooking;
import com.gang.client.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class CarBookingController {
  @Autowired
  private ICarService carService;

  private final AtomicInteger id = new AtomicInteger(0);

  @CrossOrigin
  @GetMapping("/bookings")
  List<CarBooking> getAll() {
    return new ArrayList<>(carService.getAllBookings());
  }

  @PostMapping("/order/{name}/{cars}")
  CarBooking order(@PathVariable String name, @PathVariable Integer cars) {
    CarBooking booking = new CarBooking();
    booking.setId(id.incrementAndGet());
    booking.setName(name);
    booking.setAmount(cars);
    booking.setUuid(UUID.randomUUID().toString());
    carService.bookingCar(booking);
    return booking;
  }

  @DeleteMapping("/bookings")
  void clear() {
    carService.clearAllBookings();
    id.set(0);
  }
}
