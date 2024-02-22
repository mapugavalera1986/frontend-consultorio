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

import pe.cibertec.superfrontend.modelos.Especialista;
import pe.cibertec.superfrontend.servicios.interfaz.IDatoService;

@Service
public class EspecialistaService implements IDatoService<Especialista> {

	@Autowired
	private RestTemplate plantillaRest;

	@Value("${api.superapi.baseUri}")
	private String baseUri;

	@Override
	public List<Especialista> listarTodo() {
		try {
			ResponseEntity<List<Especialista>> respuesta = plantillaRest.exchange(baseUri + "especialistas",
					HttpMethod.GET, null, new ParameterizedTypeReference<List<Especialista>>() {
					});
			return respuesta.getBody();
		} catch (Exception e) {
			return new LinkedList<Especialista>();
		}
	}

	@Override
	public Especialista obtener(int id) {
		try {
			ResponseEntity<Especialista> obtener = plantillaRest.getForEntity(baseUri + "especialistas/"+id,
					Especialista.class);
			return obtener.getBody();
		} catch (Exception e) {
			return new Especialista();
		}
	}

	@Override
	public String agregar(Especialista nuevo) {
		ResponseEntity<String> agregar = plantillaRest.postForEntity(baseUri + "especialistas", nuevo, String.class);
		return agregar.getStatusCode().toString();
	}

	@Override
	public String eliminar(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
