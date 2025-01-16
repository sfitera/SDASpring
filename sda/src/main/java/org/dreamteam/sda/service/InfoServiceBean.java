package org.dreamteam.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.dreamteam.sda.confiuguration.ApplicationConfiguration;

@Service("InfoServiceBean")
public class InfoServiceBean implements InfoService {

    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public InfoServiceBean(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public String getInfo() {
        return String.format("Hello World", applicationConfiguration.getVersion());
    }
}
