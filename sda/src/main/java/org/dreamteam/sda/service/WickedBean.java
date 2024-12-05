package org.dreamteam.sda.service;

import org.springframework.stereotype.Service;

@Service
public class WickedBean implements InfoService{


    @Override
    public String getInfo() {
        return "Wicked Bean";
    }
}
