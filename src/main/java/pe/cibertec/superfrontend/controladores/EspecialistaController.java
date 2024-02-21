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
import pe.cibertec.superfrontend.modelos.Especialista;
import pe.cibertec.superfrontend.servicios.EspecialistaService;

@Controller
@RequestMapping("/especialistas")
public class EspecialistaController implements IDatoController<Especialista> {

	@Autowired
	private EspecialistaService srvc_especialistas;

	@GetMapping
	public ModelAndView inicio(ModelMap m) {
		List<Especialista> listar_todo = srvc_especialistas.listarTodo();
		m.addAttribute("list", listar_todo);
		return new ModelAndView("crud/Especialistas", m);
	}

	@GetMapping("/{id}")
	public ModelAndView obtener(@PathVariable("id") int id, ModelMap m) {
		Especialista obtener = new Especialista();
		if(id>0) {
			obtener = srvc_especialistas.obtener(id);
		}
		m.addAttribute("especialista", obtener);
		return new ModelAndView("crud/visualizar/Especialista");
	}
}
