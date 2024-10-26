package pe.edu.cibertec.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.cibertec.config.FeignClientConfig;
import pe.edu.cibertec.dto.LoginRequestDTO;
import pe.edu.cibertec.dto.LoginResponseDTO;

import java.util.List;

@FeignClient(name = "autenticacion", url = "http://proyecto-final-dawii.azurewebsites.net/autenticacion", configuration = FeignClientConfig.class)
public interface AutenticacionClient {

    @PostMapping("/autenticar")
    public ResponseEntity<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO);
}
