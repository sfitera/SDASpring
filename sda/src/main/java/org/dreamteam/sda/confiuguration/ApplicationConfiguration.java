package org.dreamteam.sda.confiuguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="invoices")
@Data
public class ApplicationConfiguration {
    private String version = "1.0.1";
}
