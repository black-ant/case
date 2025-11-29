package com.example.prompt.model;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisRequest {
    private String domain;
    private Integer experienceLevel;
    private String context;
    private String task;
    private List<String> requirements;
    private String outputFormat;
    private String constraints;
}
