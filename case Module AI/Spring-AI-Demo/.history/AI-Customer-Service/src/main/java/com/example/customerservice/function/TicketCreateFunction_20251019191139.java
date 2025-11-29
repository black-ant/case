package com.example.customerservice.function;

import com.example.customerservice.entity.CustomerTicket;
import com.example.customerservice.repository.CustomerTicketRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * 创建工单工具
 */
@Component
@RequiredArgsConstructor
public class TicketCreateFunction implements Function<TicketCreateFunction.Request, TicketCreateFunction.Response> {
    
    private final CustomerTicketRepository ticketRepository;
    
    @Override
    public Response apply(Request request) {
        try {
            CustomerTicket ticket = new CustomerTicket();
            ticket.setCustomerId(request.customerId);
            ticket.setCustomerName(request.customerName);
            ticket.setTitle(request.title);
            ticket.setDescription(request.description);
            ticket.setPriority(CustomerTicket.TicketPriority.valueOf(request.priority.toUpperCase()));
            ticket.setCreatedAt(LocalDateTime.now());
            
            CustomerTicket saved = ticketRepository.save(ticket);
            
            return new Response(
                true,
                "工单创建成功",
                saved.getId(),
                "我们的客服团队会在24小时内处理您的问题"
            );
        } catch (Exception e) {
            return new Response(false, "工单创建失败: " + e.getMessage(), null, null);
        }
    }
    
    public record Request(
        @JsonProperty(value = "customerId", required = true) String customerId,
        @JsonProperty(value = "customerName", required = true) String customerName,
        @JsonProperty(value = "title", required = true) String title,
        @JsonProperty(value = "description", required = true) String description,
        @JsonProperty(value = "priority", required = false, defaultValue = "MEDIUM") String priority
    ) {}
    
    public record Response(
        boolean success,
        String message,
        Long ticketId,
        String nextSteps
    ) {}
}
