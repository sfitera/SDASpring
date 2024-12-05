package org.dreamteam.sda.service;

import org.springframework.stereotype.Service;

@Service("InfoServiceBean")
public class InfoServiceBean implements InfoService {


    @Override
    public String getInfo() {
        return "Hello World";
    }
}
