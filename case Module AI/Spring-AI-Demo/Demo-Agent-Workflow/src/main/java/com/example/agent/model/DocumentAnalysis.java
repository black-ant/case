package com.example.agent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAnalysis {
    private String keyInformation;
    private String sentiment;
    private String summary;
    private long processingTimeMs;
}
