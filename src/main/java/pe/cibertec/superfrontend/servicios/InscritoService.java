package pe.cibertec.superfrontend.servicios;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.cibertec.superfrontend.modelos.Inscrito;
import pe.cibertec.superfrontend.servicios.interfaz.IDatoService;

@Service
public class InscritoService implements IDatoService<Inscrito> {

	@Autowired
	private RestTemplate plantillaRest;

	@Value("${api.superapi.baseUri}")
	private String baseUri;

	@Override
	public List<Inscrito> listarTodo() {
		try {
			ResponseEntity<List<Inscrito>> respuesta = plantillaRest.exchange(baseUri + "inscripciones",
					HttpMethod.GET, null, new ParameterizedTypeReference<List<Inscrito>>() {
					});
			return respuesta.getBody();
		} catch (Exception e) {
			return new LinkedList<Inscrito>();
		}
	}

	@Override
	public Inscrito obtener(int id) {
		try {
			ResponseEntity<Inscrito> obtener = plantillaRest.getForEntity(baseUri + "inscripciones/"+id,
					Inscrito.class);
			return obtener.getBody();
		} catch (Exception e) {
			return new Inscrito();
		}
	}

	@Override
	public String agregar(Inscrito nuevo) {
		ResponseEntity<String> agregar = plantillaRest.postForEntity(baseUri + "inscripciones", nuevo, String.class);
		return agregar.getBody();
	}

	@Override
	public String eliminar(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}

