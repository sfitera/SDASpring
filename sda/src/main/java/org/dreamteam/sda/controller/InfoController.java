package org.dreamteam.sda.controller;

import org.dreamteam.sda.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private final InfoService infoService;

    @Autowired
    public InfoController(@Qualifier("InfoServiceBean")InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/info")
    String info(){
        return infoService.getInfo();
    }

}
