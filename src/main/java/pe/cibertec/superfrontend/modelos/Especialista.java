package pe.cibertec.superfrontend.modelos;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Especialista {
	private int id;
	private String nmbrs;
	private String apllds;
	private String nacimiento;
	private int edad;
	private String dni;
	private String ruc = "";
	private String direccn = "";
	private String email;
	private String telf;
	private List<Inscrito> inscritos = new LinkedList<Inscrito>();
}
