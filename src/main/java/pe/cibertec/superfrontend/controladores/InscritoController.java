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
import pe.cibertec.superfrontend.modelos.Inscrito;
import pe.cibertec.superfrontend.servicios.ContactoService;
import pe.cibertec.superfrontend.servicios.EspecialistaService;
import pe.cibertec.superfrontend.servicios.InscritoService;

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
	public RedirectView crear(Inscrito nuevo, RedirectAttributes a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RedirectView modificar(Inscrito cambiar, RedirectAttributes a) {
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
