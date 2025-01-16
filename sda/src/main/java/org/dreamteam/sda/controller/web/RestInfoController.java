package org.dreamteam.sda.controller.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestInfoController {

    @GetMapping("/rest/info2")
    public String restInfo() {
        return "This is REST public info.";
    }
}
