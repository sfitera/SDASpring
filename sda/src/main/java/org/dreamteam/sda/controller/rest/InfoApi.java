package org.dreamteam.sda.controller.rest;

import org.dreamteam.sda.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/")
public class InfoApi {

    private final InfoService infoService;

    @Autowired
    public InfoApi(@Qualifier("InfoServiceBean")InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/info")
    String info(){
        return infoService.getInfo();
    }

}
