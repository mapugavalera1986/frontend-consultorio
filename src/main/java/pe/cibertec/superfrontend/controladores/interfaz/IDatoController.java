package pe.cibertec.superfrontend.controladores.interfaz;

import java.io.IOException;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface IDatoController<T> {
	public ModelAndView inicio(HttpSession s, ModelMap m);
	public ModelAndView obtener(int id, ModelMap m);
	public ModelAndView formularioCrear(ModelMap m);
	public ModelAndView formularioModificar(int id, ModelMap m);
	public RedirectView crear(T nuevo, RedirectAttributes a);
	public RedirectView modificar(T cambiar, RedirectAttributes a);
	public RedirectView eliminar(int id, RedirectAttributes a);
	public ModelAndView volver();
	public void crearListaPdf(HttpSession ssn, HttpServletResponse respuesta) throws DocumentException, IOException;
	public void crearPdf(int id, HttpServletResponse respuesta) throws DocumentException, IOException;
}
