package pe.edu.cibertec.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplateAutenticacion(RestTemplateBuilder builder){
        return builder
                .rootUri("http://localhost:8082/autenticacion")
                .setConnectTimeout(Duration.ofSeconds(10)) //tiempo maximo para conectar con el servidor
                .setReadTimeout(Duration.ofSeconds(10)) //tiempo maximo para leer respuestas una vez conectada  al servidor
                .build();
    }


}