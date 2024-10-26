package pe.edu.cibertec.controller;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.client.AutenticacionClient;
import pe.edu.cibertec.dto.LoginRequestDTO;
import pe.edu.cibertec.dto.LoginResponseDTO;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AutenticacionClient autenticacionClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("codigoAlumno") String codigoAlumno,
                             @RequestParam("password") String password,
                             Model model) {

        // Verifica si los campos están vacíos antes de llamar al servicio de autenticación
        if (codigoAlumno.trim().isEmpty() || password.trim().isEmpty()) {
            model.addAttribute("error", "Datos Ingresados incorrectamente.");
            return "inicio";
        }

        // Crea un objeto de solicitud con los datos ingresados por el usuario
        LoginRequestDTO loginRequest = new LoginRequestDTO(codigoAlumno, password);

        try {
            // Llama al servicio Feign para autenticar al usuario
            ResponseEntity<LoginResponseDTO> response = autenticacionClient.autenticar(loginRequest);

            // Verifica si la autenticación fue exitosa
            if (response.getBody() != null && response.getBody().codigo().equals("00")) {
                // Autenticación exitosa, redirige a la pantalla de detalle
                return "redirect:/login/detalle";
            } else {
                // Si las credenciales no son válidas, muestra un mensaje de error
                model.addAttribute("error", "Autenticacion Fallida");
                return "inicio";
            }
        } catch (FeignException e) {
            // Captura las excepciones de Feign cuando el servidor no responde
            model.addAttribute("error", "Problemas en la autenticación.");
            return "inicio";
        }
    }


    @GetMapping("/detalle")
    public String detalle(Model model) {
        try {
            // Llama al servicio get-integrantes usando RestTemplate
            String url = "/get-integrantes";
            ResponseEntity<LoginResponseDTO[]> integrantesResponse = restTemplate.getForEntity(url, LoginResponseDTO[].class);

            // Verifica que la lista de integrantes no sea nula ni vacía
            if (integrantesResponse.getBody() != null && integrantesResponse.getBody().length > 0) {
                model.addAttribute("integrantes", integrantesResponse.getBody());
            } else {
                model.addAttribute("error", "No se encontraron integrantes.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al obtener la lista de integrantes.");
        }
        return "detalle"; // Página de detalle donde se mostrará la lista de integrantes
    }
}
