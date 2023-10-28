package org.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("getport")
public class InfoController {
    @Value("${server.port}")
    String port;

    @RequestMapping
    public String getPort() {
        return port;
    }
}
