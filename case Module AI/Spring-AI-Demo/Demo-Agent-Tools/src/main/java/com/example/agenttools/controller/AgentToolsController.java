package com.example.agenttools.controller;

import com.example.agenttools.core.Agent;
import com.example.agenttools.tools.impl.InMemoryDatabaseTool;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/agent-tools")
public class AgentToolsController {

    private final Agent opsAgent;
    private final Agent readonlyWeatherAgent;
    private final InMemoryDatabaseTool db;

    public AgentToolsController(Agent opsAgent, Agent readonlyWeatherAgent, InMemoryDatabaseTool db) {
        this.opsAgent = opsAgent;
        this.readonlyWeatherAgent = readonlyWeatherAgent;
        this.db = db;
    }

    // Quick demo: run the requested scenario on both agents
    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> demo() {
        String prompt = "查询北京天气并保存到数据库";
        Map<String, Object> resp = new HashMap<>();
        resp.put("prompt", prompt);
        resp.put("opsAgentResult", opsAgent.execute(prompt));
        resp.put("readonlyAgentResult", readonlyWeatherAgent.execute(prompt));
        resp.put("dbRecords", db.findAll());
        return resp;
    }

    // Flexible execution API
    @PostMapping(value = "/run", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> run(@RequestParam(defaultValue = "ops") String agent,
                                   @RequestBody String input) {
        Agent chosen = switch (agent) {
            case "readonly", "ro" -> readonlyWeatherAgent;
            default -> opsAgent;
        };
        Map<String, Object> resp = new HashMap<>();
        resp.put("agent", agent);
        resp.put("input", input);
        resp.put("result", chosen.execute(input));
        resp.put("dbRecords", db.findAll());
        return resp;
    }
}

