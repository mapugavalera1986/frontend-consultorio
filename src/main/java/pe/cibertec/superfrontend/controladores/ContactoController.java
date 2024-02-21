package pe.cibertec.superfrontend.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
		Contacto empty = new Contacto();
		Contacto obtener = srvc_contactos.obtener(id);
		if(!obtener.equals(empty)) {
			m.addAttribute("contacto", obtener);
			return new ModelAndView("crud/visualizar/Contacto", m);
		}else {
			return new ModelAndView("redirect:/contactos");
		}
	}

	@Override
	public ModelAndView formularioCrear(ModelMap m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView formularioModificar(int id, ModelMap m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RedirectView crear(Contacto nuevo, RedirectAttributes a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RedirectView modificar(Contacto cambiar, RedirectAttributes a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RedirectView eliminar(int id, RedirectAttributes a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView volver() {
		// TODO Auto-generated method stub
		return null;
	}
}
