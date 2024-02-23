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
import pe.cibertec.superfrontend.modelos.Contacto;
import pe.cibertec.superfrontend.servicios.ContactoService;
import pe.cibertec.superfrontend.utilidades.ContactoPdfs;

@Controller
@RequestMapping("/contactos")
public class ContactoController implements IDatoController<Contacto> {

	@Autowired
	private ContactoService srvc_contactos;

	@SuppressWarnings("unchecked")
	@GetMapping
	public ModelAndView inicio(HttpSession s, ModelMap m) {
		List<Contacto> listar_contactos = new LinkedList<Contacto>();
		if (m.getAttribute("list") == null) {
			listar_contactos = srvc_contactos.listarTodo();
		} else {
			listar_contactos = (List<Contacto>) m.getAttribute("list");
		}
		m.addAttribute("list", listar_contactos);
		s.setAttribute("listado", listar_contactos);
		return new ModelAndView("crud/Contactos", m);
	}

	@GetMapping("/{id}")
	public ModelAndView obtener(@PathVariable("id") int id, ModelMap m) {
		Contacto empty = new Contacto();
		Contacto obtener = srvc_contactos.obtener(id);
		if (!obtener.equals(empty)) {
			m.addAttribute("contacto", obtener);
			return new ModelAndView("crud/visualizar/Contacto", m);
		} else {
			return new ModelAndView("redirect:/contactos");
		}
	}

	// Se supone que aquí se realizan las búsquedas.
	// Por ahora, serán de nombre y apellido
	@GetMapping("/buscar")
	public ModelAndView formularioBuscar(String buscar) {
		return new ModelAndView("crud/buscar/BuscaContactos");
	}

	@PostMapping("/buscar")
	public RedirectView encontrar(@RequestParam String buscar, RedirectAttributes atributos) {
		List<Contacto> lista_buscar = srvc_contactos.buscar(buscar);
		atributos.addFlashAttribute("list", lista_buscar);
		atributos.addFlashAttribute("texto", "Resultados de la búsqueda: " + lista_buscar.size());
		return new RedirectView("/contactos");
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

	@PostMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable("id") int id, RedirectAttributes atributos) {
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

	// Métodos ULTRA experimentales para generar PDF
	@SuppressWarnings("unchecked")
	@GetMapping("/pdf")
	public void crearListaPdf(HttpSession s, HttpServletResponse respuesta) throws DocumentException, IOException {
		List<Contacto> listado = new LinkedList<Contacto>();
		if (s.getAttribute("listado") == null) {
			listado = srvc_contactos.listarTodo();
		} else {
			listado = (List<Contacto>) s.getAttribute("listado");
		}
		respuesta.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=lista_contactos.pdf";
		respuesta.setHeader(headerkey, headervalue);
		ContactoPdfs generador = new ContactoPdfs();
		generador.crearGrupal(listado, respuesta);
	}

	@GetMapping("/{id}/pdf")
	public void crearPdf(@PathVariable("id") int id, HttpServletResponse respuesta)
			throws DocumentException, IOException {
		Contacto imprimir = srvc_contactos.obtener(id);
		respuesta.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=contacto_" + imprimir.getId() + ".pdf";
		respuesta.setHeader(headerkey, headervalue);
		ContactoPdfs generador = new ContactoPdfs();
		generador.crearIndividual(imprimir, respuesta);
	}
}
