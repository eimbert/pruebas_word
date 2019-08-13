package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.w3c.dom.stylesheets.DocumentStyle;

import fr.opensagres.poi.xwpf.converter.core.Color;
import funcionesWord.v2.BuscarCamposEnDocumento;
import funcionesWord.v2.Constants;
import funcionesWord.v2.FusionarDocumentoWord;
import funcionesWord.v2.LeerDocumentoWord;

public class PruebasConWord {

	public static void main(String[] args) throws FileNotFoundException, InvalidFormatException, IOException, XmlException {
		
		LeerDocumentoWord documento1 = new LeerDocumentoWord();
		LeerDocumentoWord documento2 = new LeerDocumentoWord();
		
		//documento1.openDocument("C:\\Users\\EIB\\Desktop\\MC-MUTUAL Funcional\\Documentos prueba","MODELO_PLIEGO_PCAP.docx");
		documento2.openDocument("C:\\Users\\eimbe\\Desktop\\pruebas","prueba.docx");
		
		
		XWPFDocument document = new XWPFDocument();
		
		XWPFStyles estilo = documento2.getDocument().getStyles();
		XWPFStyles estiloNuevoDoc = document.createStyles();
		

		int pos = 0;
		int numTables = 0;
		List<IBodyElement> listElements = documento2.getDocument().getBodyElements();
		for(IBodyElement element: listElements) {
			if (element.getElementType() == BodyElementType.PARAGRAPH) {
				document.createParagraph();
				document.setParagraph((XWPFParagraph)element, pos++);
			}else 
				if(element.getElementType() == BodyElementType.TABLE) {
					XWPFTable tbl = document.createTable();
					document.setTable(numTables, (XWPFTable)element);
					setTableBorder(tbl);
			}
		}

	
	
		
		FileOutputStream fileOutput = new FileOutputStream (Constants.OUT_PATH + File.separator + Constants.TEST_DOCUMENT_NAME);
		document.write(fileOutput);
		fileOutput.close();
		document.close();
		
	}
	
	private static void setTableBorder(XWPFTable table) {
		  
		CTTblPr tblpro = table.getCTTbl().getTblPr();

		CTTblBorders borders = tblpro.addNewTblBorders();
		borders.addNewBottom().setVal(STBorder.SINGLE); 
		borders.addNewLeft().setVal(STBorder.SINGLE);
		borders.addNewRight().setVal(STBorder.SINGLE);
		borders.addNewTop().setVal(STBorder.SINGLE);
		borders.addNewInsideH().setVal(STBorder.SINGLE);
		borders.addNewInsideV().setVal(STBorder.SINGLE);

		}
}
