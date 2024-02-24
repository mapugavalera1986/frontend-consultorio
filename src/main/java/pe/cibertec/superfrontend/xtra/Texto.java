package pe.cibertec.superfrontend.xtra;

public class Texto {
	public static String mostrarOmitido(String dato, String reemplazo) {
	if(dato == null || dato.trim().isEmpty()){
			return reemplazo;
		}else {
			return dato;
		}
	}
}
