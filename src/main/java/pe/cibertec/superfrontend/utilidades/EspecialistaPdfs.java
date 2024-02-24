package pe.cibertec.superfrontend.utilidades;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import pe.cibertec.superfrontend.modelos.Especialista;
import pe.cibertec.superfrontend.utilidades.interfaz.IGenerarPdfs;
import pe.cibertec.superfrontend.xtra.Texto;

public class EspecialistaPdfs implements IGenerarPdfs<Especialista> {

	@Override
	public void crearGrupal(List<Especialista> listado, HttpServletResponse respuesta)
			throws DocumentException, IOException {
		Document nuevo_doc = new Document(PageSize.A4);
		PdfWriter.getInstance(nuevo_doc, respuesta.getOutputStream());
		nuevo_doc.open();// Aquí empiezas a formatear el documento
		nuevo_doc.setMargins(16, 16, 16, 16);
		Paragraph encabezado = new Paragraph("Lista de especialistas", elegirFuente(16, "bold"));
		encabezado.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		encabezado.setSpacingAfter(12);
		nuevo_doc.add(encabezado);
		for (Especialista i : listado) {
			nuevo_doc.add(redactarEntidad(i, elegirFuente(11, "normal")));
		}
		nuevo_doc.close();
	}

	@Override
	public void crearIndividual(Especialista entidad, HttpServletResponse respuesta) throws DocumentException, IOException {
		Document nuevo_doc = new Document(PageSize.A4);
		PdfWriter.getInstance(nuevo_doc, respuesta.getOutputStream());
		nuevo_doc.open();
		Paragraph encabezado = new Paragraph("Datos de especialista registrado", elegirFuente(16, "bold"));
		encabezado.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		encabezado.setSpacingAfter(12);
		nuevo_doc.add(encabezado);
		nuevo_doc.add(redactarEntidad(entidad, elegirFuente(11, "normal")));
		nuevo_doc.close();
	}

	private Paragraph redactarEntidad(Especialista entidad, Font fuente) {
		String texto_entidad = "Especialista: " + entidad.getNmbrs() + " " + entidad.getApllds() + "\nFecha nac.: "
				+ entidad.getNacimiento() + "\nEdad: " + entidad.getEdad() + "\nDNI: "
				+ Texto.mostrarOmitido(entidad.getDni(), "No registrado") + "\nRUC: "
				+ Texto.mostrarOmitido(entidad.getRuc(), "No registrado") + "\nE-Mail: "
				+ Texto.mostrarOmitido(entidad.getEmail(), "No registrado") + "\nTelf.: "
				+ Texto.mostrarOmitido(entidad.getTelf(), "No registrado") + "\nDirección: " + entidad.getDireccn()
				+ "\nPart. a cargo: " + entidad.getInscritos().size();
		Paragraph redactar_entidad = new Paragraph(texto_entidad, fuente);
		redactar_entidad.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		redactar_entidad.setSpacingAfter(8);
		return redactar_entidad;
	}

	private Font elegirFuente(int size, String propiedad) {
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setSize(size);
		fuente.setStyle(propiedad);
		return fuente;
	}

}
