package org.dreamteam.sda.controller.web;

import org.dreamteam.sda.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class InfoController {

    private final InfoService infoService;

    @Autowired
    public InfoController(@Qualifier("InfoServiceBean")InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/info")
    String info(Model model){
        model.addAttribute("info", infoService.getInfo());
        return "info";
    }

}
