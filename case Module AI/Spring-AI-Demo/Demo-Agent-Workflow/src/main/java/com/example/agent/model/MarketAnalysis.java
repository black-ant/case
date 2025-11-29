package com.example.agent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketAnalysis {
    private Map<String, String> perspectiveAnalyses;
    private String consolidatedAnalysis;
    private String recommendation;
    private long processingTimeMs;
}
