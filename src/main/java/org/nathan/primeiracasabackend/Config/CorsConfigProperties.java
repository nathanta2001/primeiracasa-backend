package org.nathan.primeiracasabackend.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "app.cors")
@Component
@Getter @Setter
public class CorsConfigProperties {
    private List<String> allowedOrigins;


}
