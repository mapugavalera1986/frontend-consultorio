package pe.cibertec.superfrontend.modelos;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Inscrito {
	private int id;
	private String nmbrs;
	private String apllds;
	private String nacimiento;
	private int edad;
	private String dni ="";
	private String direccn;
	private String email = "";
	private String telf = "";
	private String modalidad;
	private int especialista_id;
	private int contacto_id;
}