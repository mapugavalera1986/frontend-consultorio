package pe.cibertec.superfrontend.controladores;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.cibertec.superfrontend.controladores.interfaz.IDatoController;
import pe.cibertec.superfrontend.modelos.Inscrito;
import pe.cibertec.superfrontend.modelos.Modalidad;
import pe.cibertec.superfrontend.servicios.ContactoService;
import pe.cibertec.superfrontend.servicios.EspecialistaService;
import pe.cibertec.superfrontend.servicios.InscritoService;
import pe.cibertec.superfrontend.utilidades.InscritoPdfs;
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

	// Si hay un método mejor para enumerar, hay que ponerlo. Pero ya no hay tiempo.
	private List<String> enumerarModalidad() {
		List<String> previa_modalidades = new LinkedList<String>();
		for (Modalidad modalidad : Modalidad.values()) {
			previa_modalidades.add(modalidad.toString());
		}
		return previa_modalidades;
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public ModelAndView inicio(HttpSession s, ModelMap m) {
		List<Inscrito> listar_inscritos = new LinkedList<Inscrito>();
		if (m.getAttribute("list") == null) {
			listar_inscritos = srvc_inscritos.listarTodo();
		} else {
			listar_inscritos = (List<Inscrito>) m.getAttribute("list");
		}
		m.addAttribute("list", listar_inscritos);
		s.setAttribute("listado", listar_inscritos);
		return new ModelAndView("crud/Inscritos", m);
	}

	@GetMapping("/{id}") // Se buscarán el contacto y especialistas del participante
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

	// Se supone que aquí se realizan las búsquedas.
	// Por ahora, serán de nombre y apellido
	@GetMapping("/buscar")
	public ModelAndView formularioBuscar(String buscar) {
		return new ModelAndView("crud/buscar/BuscaInscritos");
	}

	@PostMapping("/buscar")
	public RedirectView encontrar(@RequestParam String buscar, RedirectAttributes atributos) {
		List<Inscrito> lista_buscar = srvc_inscritos.buscar(buscar);
		atributos.addFlashAttribute("list", lista_buscar);
		atributos.addFlashAttribute("texto", "Resultados de la búsqueda: " + lista_buscar.size());
		return new RedirectView("/inscripciones");
	}

	@GetMapping("/crear")
	public ModelAndView formularioCrear(ModelMap m) {
		Inscrito nuevo = new Inscrito();
		m.addAttribute("inscrito", nuevo);
		m.addAttribute("modalidades", enumerarModalidad());
		m.addAttribute("contactos", srvc_contactos.listarTodo()); // Estos son para el formulario
		m.addAttribute("especialistas", srvc_especialistas.listarTodo()); // Recuerda sus nombres
		return new ModelAndView("crud/crear/Inscrito", m);
	}

	@GetMapping("/modificar/{id}")
	public ModelAndView formularioModificar(@PathVariable("id") int id, ModelMap m) {
		Inscrito empty = new Inscrito();
		Inscrito cambiar = srvc_inscritos.obtener(id);
		if (!cambiar.equals(empty)) {
			m.addAttribute("inscrito", cambiar);
			m.addAttribute("modalidades", enumerarModalidad());
			m.addAttribute("contactos", srvc_contactos.listarTodo()); // Estos son para el formulario
			m.addAttribute("especialistas", srvc_especialistas.listarTodo()); // Recuerda sus nombres
			return new ModelAndView("crud/crear/Inscrito", m);
		} else {
			return new ModelAndView("redirect:/inscripciones");
		}
	}

	@PostMapping("/crear")
	public RedirectView crear(Inscrito nuevo, RedirectAttributes atributos) {
		Mensaje.consola(nuevo.toString());
		String resultado = srvc_inscritos.agregar(nuevo);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se agregó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo crear; intenta de nuevo");
		}
		return new RedirectView("/inscripciones");
	}

	@PostMapping("/modificar")
	public RedirectView modificar(Inscrito cambiar, RedirectAttributes atributos) {
		Mensaje.consola(cambiar.toString());
		String resultado = srvc_inscritos.agregar(cambiar);
		if (resultado.equals("200 OK")) {
			atributos.addFlashAttribute("texto", "¡Se modificó con éxito!");
		} else {
			atributos.addFlashAttribute("texto", "No se pudo modificar; intenta de nuevo");
		}
		return new RedirectView("/inscripciones");
	}

	@PostMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable("id") int id, RedirectAttributes atributos) {
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

	// Métodos ULTRA experimentales para generar PDF
	@SuppressWarnings("unchecked")
	@GetMapping("/pdf")
	public void crearListaPdf(HttpSession s, HttpServletResponse respuesta) throws DocumentException, IOException {
		List<Inscrito> listado = new LinkedList<Inscrito>();
		if (s.getAttribute("listado") == null) {
			listado = srvc_inscritos.listarTodo();
		} else {
			listado = (List<Inscrito>) s.getAttribute("listado");
		}
		respuesta.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=lista_inscritos.pdf";
		respuesta.setHeader(headerkey, headervalue);
		InscritoPdfs generador = new InscritoPdfs();
		generador.crearGrupal(listado, respuesta);
	}

	@GetMapping("/{id}/pdf")
	public void crearPdf(@PathVariable("id") int id, HttpServletResponse respuesta)
			throws DocumentException, IOException {
		Inscrito imprimir = srvc_inscritos.obtener(id);
		respuesta.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=participante_" + imprimir.getId() + ".pdf";
		respuesta.setHeader(headerkey, headervalue);
		InscritoPdfs generador = new InscritoPdfs();
		generador.crearIndividual(imprimir, respuesta);
	}
}
