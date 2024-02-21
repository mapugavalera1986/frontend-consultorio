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

import pe.cibertec.superfrontend.modelos.Contacto;
import pe.cibertec.superfrontend.servicios.interfaz.IDatoService;
import pe.cibertec.superfrontend.xtra.Mensaje;

@Service
public class ContactoService implements IDatoService<Contacto> {

	@Autowired
	private RestTemplate plantillaRest;

	@Value("${api.superapi.baseUri}")
	private String baseUri;

	@Override
	public List<Contacto> listarTodo() {
		try {
			ResponseEntity<List<Contacto>> respuesta = plantillaRest.exchange(baseUri + "contactos",
					HttpMethod.GET, null, new ParameterizedTypeReference<List<Contacto>>() {
					});
			return respuesta.getBody();
		} catch (Exception e) {
			return new LinkedList<Contacto>();
		}
	}

	@Override
	public Contacto obtener(int id) {
		try {
			ResponseEntity<Contacto> obtener = plantillaRest.getForEntity(baseUri + "contactos/"+id,
					Contacto.class);
			Mensaje.consola("Mira: " +obtener.getBody().toString());
			return obtener.getBody();
		} catch (Exception e) {
			return new Contacto();
		}
	}

	@Override
	public String agregar(Contacto nuevo) {
		ResponseEntity<String> agregar = plantillaRest.postForEntity(baseUri + "contactos", nuevo, String.class);
		return agregar.getBody();
	}

	@Override
	public String modificar(int id, Contacto cambiar) {
		ResponseEntity<String> modificar = plantillaRest.postForEntity(baseUri + "contactos" + cambiar.getId(),
				cambiar, String.class);
		return modificar.getBody();
	}

	@Override
	public String eliminar(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
