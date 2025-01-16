package org.dreamteam.sda.confiuguration;

import org.dreamteam.sda.service.InfoService;
import org.dreamteam.sda.service.InfoServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoConfig {

    @Bean
    InfoService infoService(@Autowired ApplicationConfiguration applicationConfiguration) {
        return new InfoServiceBean(applicationConfiguration);
    }
    /*
    @Bean
    InfoService wickedBean() {
        return new WickedBean();
    }
*/
}
