package pe.cibertec.superfrontend.utilidades;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import pe.cibertec.superfrontend.modelos.Inscrito;
import pe.cibertec.superfrontend.utilidades.interfaz.IGenerarPdfs;

public class InscritoPdfs implements IGenerarPdfs<Inscrito> {

	@Override
	public void crearGrupal(List<Inscrito> listado, HttpServletResponse respuesta) throws DocumentException, IOException {
		Document nuevo_doc = new Document(PageSize.A4);
		PdfWriter.getInstance(nuevo_doc, respuesta.getOutputStream());
		nuevo_doc.open();//Aquí empiezas a formatear el documento
		//Le damos formato al texto
		Font fntTexto = FontFactory.getFont(FontFactory.HELVETICA);
		fntTexto.setSize(11);
		//Creamos el encabezado
		Paragraph encabezado = new Paragraph("Inscripciones", fntTexto);
		encabezado.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		nuevo_doc.add(encabezado);
		//Vamos a probar cómo añadir varios párrafos con la info
		List<Paragraph> cuerpo = new LinkedList<Paragraph>();
		for(int i=0; i<listado.size();i++) {
			cuerpo.add(new Paragraph(listado.get(i).toString(), fntTexto));
			nuevo_doc.add(cuerpo.get(i));
		}
		//Aquí termina añadir párrafos
		nuevo_doc.close();//Aquí ya no puedes formatear
	}

	@Override
	public void crearIndividual(Inscrito entidad, HttpServletResponse respuesta) throws DocumentException, IOException {
		Document nuevo_doc = new Document(PageSize.A4);
		PdfWriter.getInstance(nuevo_doc, respuesta.getOutputStream());
		nuevo_doc.open();//Aquí empiezas a formatear el documento
		//Le damos formato al texto
		Font fntTexto = FontFactory.getFont(FontFactory.HELVETICA);
		fntTexto.setSize(11);
		//Creamos el encabezado
		Paragraph encabezado = new Paragraph("Participante inscrito", fntTexto);
		encabezado.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		nuevo_doc.add(encabezado);
		Paragraph cuerpo = new Paragraph(entidad.toString());
		nuevo_doc.add(cuerpo);
		nuevo_doc.close();//Aquí ya no puedes formatear
	}
}