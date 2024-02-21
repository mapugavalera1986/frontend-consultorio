package pe.cibertec.superfrontend.controladores.interfaz;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

public interface IDatoController<T> {
	public ModelAndView inicio(ModelMap m);
	public ModelAndView obtener(int id, ModelMap m);
	public ModelAndView formularioCrear(ModelMap m);
	public ModelAndView formularioModificar(int id, ModelMap m);
	public RedirectView crear(T nuevo, RedirectAttributes a);
	public RedirectView modificar(T cambiar, RedirectAttributes a);
	public RedirectView eliminar(int id, RedirectAttributes a);
	public ModelAndView volver();
}
