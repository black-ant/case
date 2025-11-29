package com.example.customerservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 聊天会话实体
 */
@Data
@Entity
@Table(name = "chat_sessions")
public class ChatSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String sessionId;
    
    @Column(nullable = false)
    private String customerId;
    
    private String customerName;
    
    @Column(nullable = false)
    private LocalDateTime startTime = LocalDateTime.now();
    
    private LocalDateTime endTime;
    
    @Enumerated(EnumType.STRING)
    private SessionStatus status = SessionStatus.ACTIVE;
    
    private Integer messageCount = 0;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    public enum SessionStatus {
        ACTIVE, ENDED
    }
}
