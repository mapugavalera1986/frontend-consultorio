package pe.cibertec.superfrontend.utilidades.interfaz;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

public interface IGenerarPdfs<T> {
	public void crearGrupal(List<T> lista, HttpServletResponse respuesta) throws DocumentException, IOException;
	public void crearIndividual(T entidad, HttpServletResponse respuesta) throws DocumentException, IOException;
}