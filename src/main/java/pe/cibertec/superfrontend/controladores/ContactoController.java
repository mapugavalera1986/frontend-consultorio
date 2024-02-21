package pe.cibertec.superfrontend.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/crear")
	public ModelAndView formularioCrear(ModelMap m) {
		Contacto nuevo = new Contacto();
		m.addAttribute("contacto", nuevo);
		return new ModelAndView("crud/crear/Contacto", m);
	}

	@GetMapping("/modificar/{id}")
	public ModelAndView formularioModificar(@PathVariable("id") int id, ModelMap m) {
		Contacto empty = new Contacto();
		Contacto cambiar = srvc_contactos.obtener(id);
		if (!cambiar.equals(empty)) {
			m.addAttribute("contacto", cambiar);
			return new ModelAndView("crud/crear/Contacto", m);
		} else {
			return new ModelAndView("redirect:/contactos");
		}
	}

	@PostMapping("/crear")
	public RedirectView crear(Contacto nuevo, RedirectAttributes atributos) {
		String resultado = srvc_contactos.agregar(nuevo);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se agregó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo crear; intenta de nuevo");
		}
		return new RedirectView("/contactos");
	}

	@PostMapping("/modificar")
	public RedirectView modificar(Contacto cambiar, RedirectAttributes atributos) {
		String resultado = srvc_contactos.agregar(cambiar);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se modificó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo modificar; intenta de nuevo");
		}
		return new RedirectView("/contactos");
	}

	@PostMapping("/eliminar")
	public RedirectView eliminar(int id, RedirectAttributes atributos) {
		String resultado = srvc_contactos.eliminar(id);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "Eliminación exitosa");
		} else {
			atributos.addFlashAttribute("texto",
					"Imposible eliminar; es probable que haya datos que dependen de esta, por lo que deberás cambiarlos");
		}
		return new RedirectView("/contactos");
	}
	
	@PostMapping("/volver")
	public ModelAndView volver() {
		return new ModelAndView("redirect:/contactos");
	}

}
