package funcionesWord.v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeerDocumentoWord {

	List<XWPFRun> runs = new ArrayList<XWPFRun>();
	
	private XWPFDocument document;
	
	protected void leerParrafosEnTexto() {
		for (XWPFParagraph p : document.getParagraphs()) 
			for (XWPFRun r : p.getRuns()) 
				if(r!=null && r.getText(0)!=null)
					runs.add(r);
	}
	
	
	protected void leerParrafosEnTablas() {
		for (XWPFTable tbl : document.getTables())
			for (XWPFTableRow row : tbl.getRows())
				for (XWPFTableCell cell : row.getTableCells())
					for (XWPFParagraph p : cell.getParagraphs())
						for (XWPFRun r : p.getRuns())
							if(r!=null && r.getText(0)!=null)
								runs.add(r);
							
	}
	
	protected  void openDocument() throws FileNotFoundException, IOException, InvalidFormatException {
		File filename = new File(Constants.IN_PATH + File.separator + Constants.TEST_DOCUMENT_NAME);
		InputStream is = new FileInputStream(filename);
		OPCPackage oPackage = OPCPackage.open(is);
		document = new XWPFDocument(oPackage);
		
	}
	
	public  void openDocument(String IN_PATH, String DOCUMENT_NAME) throws FileNotFoundException, IOException, InvalidFormatException {
		File filename = new File(IN_PATH + File.separator + DOCUMENT_NAME);
		InputStream is = new FileInputStream(filename);
		OPCPackage oPackage = OPCPackage.open(is);
		document = new XWPFDocument(oPackage);
		this.leerParrafosEnTexto();
		this.leerParrafosEnTablas();
		
		
	}
	
	public void saveDocument() throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = new FileOutputStream (Constants.OUT_PATH + File.separator + Constants.TEST_DOCUMENT_NAME);
		document.write(fileOutput);
		fileOutput.close();
		document.close();
	}
	
	protected void closeDocumento() throws IOException {
		document.close();
	}
	
	protected Boolean buscarCompleta(String texto, String codInicio, String codFinal) {
		if(texto!=null) {
			if(texto.contains(codInicio) && texto.contains(codFinal)) {
				return true; //busqueda completa 
			}
		}
		return false; //se deberá hacer una busqueda parcial
	}
	
	protected Boolean buscarInicioEtiqueta(String texto, String codInicio) {
		if(texto!=null) {
			if(texto.contains(codInicio)) {
				return true; //contiene inicio
			}
		}
		return false; //parrafo sin etiqueta
	}
	
	protected Boolean buscarFinalEtiqueta(String texto, String codFinal) {
		if(texto!=null) {
			if(texto.contains(codFinal)) {
				return true; //contiene final
			}
		}
		return false; //parrafo sin etiqueta
	}
}
