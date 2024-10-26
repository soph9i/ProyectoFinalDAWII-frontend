package pe.edu.cibertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProyectoFinalDawiiFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalDawiiFrontendApplication.class, args);
    }

}
