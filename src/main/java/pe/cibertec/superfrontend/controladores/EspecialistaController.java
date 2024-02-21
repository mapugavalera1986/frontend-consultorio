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
import pe.cibertec.superfrontend.modelos.Especialista;
import pe.cibertec.superfrontend.servicios.EspecialistaService;
import pe.cibertec.superfrontend.xtra.Mensaje;

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
		Especialista empty = new Especialista();
		Especialista obtener = srvc_especialistas.obtener(id);
		if (!obtener.equals(empty)) {
			m.addAttribute("especialista", obtener);
			return new ModelAndView("crud/visualizar/Especialista", m);
		} else {
			return new ModelAndView("redirect:/especialistas");
		}
	}

	@GetMapping("/crear")
	public ModelAndView formularioCrear(ModelMap m) {
		Especialista nuevo = new Especialista();
		m.addAttribute("especialista", nuevo);
		return new ModelAndView("crud/crear/Especialista", m);
	}

	@GetMapping("/modificar/{id}")
	public ModelAndView formularioModificar(@PathVariable("id") int id, ModelMap m) {
		Especialista empty = new Especialista();
		Especialista cambiar = srvc_especialistas.obtener(id);
		if (!cambiar.equals(empty)) {
			m.addAttribute("especialista", cambiar);
			return new ModelAndView("crud/crear/Especialista", m);
		} else {
			return new ModelAndView("redirect:/especialistas");
		}
	}

	@PostMapping("/crear")
	public RedirectView crear(Especialista nuevo, RedirectAttributes atributos) {
		String resultado = srvc_especialistas.agregar(nuevo);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se agregó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo crear; intenta de nuevo");
		}
		return new RedirectView("/especialistas");
	}

	@PostMapping("/modificar")
	public RedirectView modificar(Especialista cambiar, RedirectAttributes atributos) {
		String resultado = srvc_especialistas.agregar(cambiar);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se modificó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo modificar; intenta de nuevo");
		}
		return new RedirectView("/especialistas");
	}

	@PostMapping("/eliminar")
	public RedirectView eliminar(int id, RedirectAttributes atributos) {
		String resultado = srvc_especialistas.eliminar(id);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "Eliminación exitosa");
		} else {
			atributos.addFlashAttribute("texto",
					"Imposible eliminar; es probable que haya datos que dependen de esta, por lo que deberás cambiarlos");
		}
		return new RedirectView("/especialistas");
	}
	
	@PostMapping("/volver")
	public ModelAndView volver() {
		return new ModelAndView("redirect:/especialistas");
	}
}
