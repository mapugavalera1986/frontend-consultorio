package pe.cibertec.superfrontend.servicios.interfaz;

import java.util.List;

public interface IDatoService<T> {
	
	public List<T> listarTodo();
	public T obtener(int id);
	public String agregar(T nuevo);
	public String eliminar(int id);
}
