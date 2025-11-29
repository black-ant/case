package com.example.customerservice.repository;

import com.example.customerservice.entity.CustomerTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerTicketRepository extends JpaRepository<CustomerTicket, Long> {
    List<CustomerTicket> findByCustomerId(String customerId);
    List<CustomerTicket> findByStatus(CustomerTicket.TicketStatus status);
}
