package pe.cibertec.superfrontend.controladores.interfaz;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public interface IDatoController<T> {
	public ModelAndView inicio(ModelMap m);
	public ModelAndView obtener(int id, ModelMap m);
}
