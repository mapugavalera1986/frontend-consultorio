package pe.cibertec.superfrontend.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class BienvenidaController {
	@GetMapping
	public ModelAndView inicio() {
		return new ModelAndView("crud/Bienvenida");
	}
}
