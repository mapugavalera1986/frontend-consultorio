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
import pe.cibertec.superfrontend.modelos.Inscrito;
import pe.cibertec.superfrontend.modelos.Modalidad;
import pe.cibertec.superfrontend.servicios.ContactoService;
import pe.cibertec.superfrontend.servicios.EspecialistaService;
import pe.cibertec.superfrontend.servicios.InscritoService;
import pe.cibertec.superfrontend.xtra.Mensaje;

@Controller
@RequestMapping("/inscripciones")
public class InscritoController implements IDatoController<Inscrito> {

	@Autowired
	private InscritoService srvc_inscritos;

	@Autowired
	private ContactoService srvc_contactos;// La idea es que se use solo una vez, para ubicar el id

	@Autowired
	private EspecialistaService srvc_especialistas;// Igual que arriba

	@GetMapping
	public ModelAndView inicio(ModelMap m) {
		List<Inscrito> listar_todo = srvc_inscritos.listarTodo();
		m.addAttribute("list", listar_todo);
		return new ModelAndView("crud/Inscritos", m);
	}

	@GetMapping("/{id}")//Se buscarán el contacto y especialistas del participante
	public ModelAndView obtener(@PathVariable("id") int id, ModelMap m) {
		Inscrito empty = new Inscrito();
		Inscrito obtener = srvc_inscritos.obtener(id);
		if (!obtener.equals(empty)) {
			m.addAttribute("contacto", srvc_contactos.obtener(obtener.getContacto_id()));
			m.addAttribute("especialista", srvc_especialistas.obtener(obtener.getEspecialista_id()));
			m.addAttribute("inscrito", obtener);
			return new ModelAndView("crud/visualizar/Inscrito", m);
		} else {
			return new ModelAndView("redirect:/inscripciones");
		}
	}
	

	@GetMapping("/crear")
	public ModelAndView formularioCrear(ModelMap m) {
		Inscrito nuevo = new Inscrito();
		m.addAttribute("inscrito", nuevo);
		m.addAttribute("contactos", srvc_contactos.listarTodo()); //Estos son para el formulario
		m.addAttribute("especialistas", srvc_especialistas.listarTodo()); //Recuerda sus nombres
		return new ModelAndView("crud/crear/Inscrito", m);
	}

	@GetMapping("/modificar/{id}")
	public ModelAndView formularioModificar(@PathVariable("id") int id, ModelMap m) {
		Inscrito empty = new Inscrito();
		Inscrito cambiar = srvc_inscritos.obtener(id);
		if (!cambiar.equals(empty)) {
			m.addAttribute("inscrito", cambiar);
			m.addAttribute("contactos", srvc_contactos.listarTodo()); //Estos son para el formulario
			m.addAttribute("especialistas", srvc_especialistas.listarTodo()); //Recuerda sus nombres
			return new ModelAndView("crud/crear/Inscrito", m);
		} else {
			return new ModelAndView("redirect:/inscripciones");
		}
	}

	@PostMapping("/crear")
	public RedirectView crear(Inscrito nuevo, RedirectAttributes atributos) {
		Mensaje.consola(nuevo.toString());
		/*String resultado = srvc_inscritos.agregar(nuevo);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se agregó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo crear; intenta de nuevo");
		}*/
		return new RedirectView("/inscripciones");
	}

	@PostMapping("/modificar")
	public RedirectView modificar(Inscrito cambiar, RedirectAttributes atributos) {
		Mensaje.consola(cambiar.toString());
		/*String resultado = srvc_inscritos.agregar(cambiar);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se modificó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo modificar; intenta de nuevo");
		}*/
		return new RedirectView("/inscripciones");
	}

	@PostMapping("/eliminar")
	public RedirectView eliminar(int id, RedirectAttributes atributos) {
		String resultado = srvc_inscritos.eliminar(id);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "Eliminación exitosa");
		} else {
			atributos.addFlashAttribute("texto",
					"Imposible eliminar; es probable que haya datos que dependen de esta, por lo que deberás cambiarlos");
		}
		return new RedirectView("/inscripciones");
	}
	
	@PostMapping("/volver")
	public ModelAndView volver() {
		return new ModelAndView("redirect:/inscripciones");
	}
}
