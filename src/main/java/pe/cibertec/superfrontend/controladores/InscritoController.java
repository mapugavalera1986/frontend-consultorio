package pe.cibertec.superfrontend.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pe.cibertec.superfrontend.controladores.interfaz.IDatoController;
import pe.cibertec.superfrontend.modelos.Inscrito;
import pe.cibertec.superfrontend.servicios.InscritoService;

@Controller
@RequestMapping("/inscripciones")
public class InscritoController implements IDatoController<Inscrito> {

	@Autowired
	private InscritoService srvc_inscritos;

	@GetMapping
	public ModelAndView inicio(ModelMap m) {
		List<Inscrito> listar_todo = srvc_inscritos.listarTodo();
		m.addAttribute("list", listar_todo);
		return new ModelAndView("crud/Inscritos", m);
	}

	@GetMapping("/{id}")
	public ModelAndView obtener(@PathVariable("id") int id, ModelMap m) {
		Inscrito obtener = new Inscrito();
		if(id>0) {
			obtener = srvc_inscritos.obtener(id);
		}
		m.addAttribute("inscrito", obtener);
		return new ModelAndView("crud/visualizar/Inscrito");
	}
}
