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
public class Contacto {
	private int id;
	private String nmbrs;
	private String apllds;
	private String dni;
	private String email;
	private String telf;
	private List<Inscrito> inscritos = new LinkedList<Inscrito>();
}
