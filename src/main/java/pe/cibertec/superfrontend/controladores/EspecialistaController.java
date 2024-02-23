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
import pe.cibertec.superfrontend.modelos.Especialista;
import pe.cibertec.superfrontend.servicios.EspecialistaService;
import pe.cibertec.superfrontend.utilidades.EspecialistaPdfs;

@Controller
@RequestMapping("/especialistas")
public class EspecialistaController implements IDatoController<Especialista> {

	@Autowired
	private EspecialistaService srvc_especialistas;

	@SuppressWarnings("unchecked")
	@GetMapping
	public ModelAndView inicio(HttpSession s, ModelMap m) {
		List<Especialista> listar_especialistas = new LinkedList<Especialista>();
		if (m.getAttribute("list") == null) {
			listar_especialistas = srvc_especialistas.listarTodo();
		} else {
			listar_especialistas = (List<Especialista>) m.getAttribute("list");
		}
		m.addAttribute("list", listar_especialistas);
		s.setAttribute("listado", listar_especialistas);
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

	// Se supone que aquí se realizan las búsquedas.
	// Por ahora, serán de nombre y apellido
	@GetMapping("/buscar")
	public ModelAndView formularioBuscar(String buscar) {
		return new ModelAndView("crud/buscar/BuscaEspecialistas");
	}

	@PostMapping("/buscar")
	public RedirectView encontrar(@RequestParam String buscar, RedirectAttributes atributos) {
		List<Especialista> lista_buscar = srvc_especialistas.buscar(buscar);
		atributos.addFlashAttribute("list", lista_buscar);
		atributos.addFlashAttribute("texto", "Resultados de la búsqueda: " + lista_buscar.size());
		return new RedirectView("/especialistas");
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

	@PostMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable("id") int id, RedirectAttributes atributos) {
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

	// Métodos ULTRA experimentales para generar PDF
	@SuppressWarnings("unchecked")
	@GetMapping("/pdf")
	public void crearListaPdf(HttpSession s, HttpServletResponse respuesta) throws DocumentException, IOException {
		List<Especialista> listado = new LinkedList<Especialista>();
		if (s.getAttribute("listado") == null) {
			listado = srvc_especialistas.listarTodo();
		} else {
			listado = (List<Especialista>) s.getAttribute("listado");
		}
		respuesta.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=lista_especialistas.pdf";
		respuesta.setHeader(headerkey, headervalue);
		EspecialistaPdfs generador = new EspecialistaPdfs();
		generador.crearGrupal(listado, respuesta);
	}

	@GetMapping("/{id}/pdf")
	public void crearPdf(@PathVariable("id") int id, HttpServletResponse respuesta)
			throws DocumentException, IOException {
		Especialista imprimir = srvc_especialistas.obtener(id);
		respuesta.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=especialista_" + imprimir.getId() + ".pdf";
		respuesta.setHeader(headerkey, headervalue);
		EspecialistaPdfs generador = new EspecialistaPdfs();
		generador.crearIndividual(imprimir, respuesta);
	}
}
