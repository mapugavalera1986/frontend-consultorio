package pe.cibertec.superfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import pe.cibertec.superfrontend.xtra.Mensaje;

@SpringBootApplication
public class FrontIniciar {
	
	public static void main(String[] args) {
		SpringApplication.run(FrontIniciar.class, args);
		Mensaje.consola("El Front End ya podrá funcionar");
	}
	
	@Bean
	RestTemplate plantillaRest() {
		return new RestTemplate();
	}

}