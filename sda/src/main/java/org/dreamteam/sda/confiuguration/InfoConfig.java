package org.dreamteam.sda.confiuguration;

import org.dreamteam.sda.service.InfoService;
import org.dreamteam.sda.service.InfoServiceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoConfig {

    @Bean
    InfoService infoService() {
        return new InfoServiceBean();
    }
    /*
    @Bean
    InfoService wickedBean() {
        return new WickedBean();
    }
*/
}
