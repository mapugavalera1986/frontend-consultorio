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
import pe.cibertec.superfrontend.modelos.Contacto;
import pe.cibertec.superfrontend.servicios.ContactoService;

@Controller
@RequestMapping("/contactos")
public class ContactoController implements IDatoController<Contacto> {

	@Autowired
	private ContactoService srvc_contactos;

	@GetMapping
	public ModelAndView inicio(ModelMap m) {
		List<Contacto> listar_todo = srvc_contactos.listarTodo();
		m.addAttribute("list", listar_todo);
		return new ModelAndView("crud/Contactos", m);
	}

	@GetMapping("/{id}")
	public ModelAndView obtener(@PathVariable("id") int id, ModelMap m) {
		Contacto obtener = new Contacto();
		if(id>0) {
			obtener = srvc_contactos.obtener(id);
		}
		m.addAttribute("contacto", obtener);
		return new ModelAndView("crud/visualizar/Contacto");
	}
}
