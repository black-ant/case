package com.example.customerservice.function;

import com.example.customerservice.entity.CustomerTicket;
import com.example.customerservice.repository.CustomerTicketRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.function.Function;

/**
 * 工单查询工具
 */
public class TicketQueryFunction implements Function<TicketQueryFunction.Request, TicketQueryFunction.Response> {
    
    private final CustomerTicketRepository ticketRepository;
    
    public TicketQueryFunction(CustomerTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    
    @Override
    public Response apply(Request request) {
        List<CustomerTicket> tickets = ticketRepository.findByCustomerId(request.customerId);
        return new Response(
            true,
            "查询到 " + tickets.size() + " 个工单",
            tickets
        );
    }
    
    public record Request(
        @JsonProperty(value = "customerId", required = true) String customerId
    ) {}
    
    public record Response(
        boolean success,
        String message,
        List<CustomerTicket> tickets
    ) {}
}
