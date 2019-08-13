package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlException;
import org.w3c.dom.stylesheets.DocumentStyle;

import funcionesWord.v2.BuscarCamposEnDocumento;
import funcionesWord.v2.Constants;
import funcionesWord.v2.FusionarDocumentoWord;
import funcionesWord.v2.LeerDocumentoWord;

public class PruebasConWord {

	public static void main(String[] args) throws FileNotFoundException, InvalidFormatException, IOException, XmlException {
		
		LeerDocumentoWord documento1 = new LeerDocumentoWord();
		LeerDocumentoWord documento2 = new LeerDocumentoWord();
		
		documento1.openDocument("C:\\Users\\EIB\\Desktop\\MC-MUTUAL Funcional\\Documentos prueba","MODELO_PLIEGO_PCAP.docx");
		documento2.openDocument("C:\\Users\\EIB\\Desktop\\MC-MUTUAL Funcional\\Documentos prueba","Ejemplo documento para insertar.docx");
		
		
		XWPFDocument document = new XWPFDocument();
		
		int pos = 0;
//		for(XWPFParagraph p: documento2.getDocument().getParagraphs()) {
//			XWPFParagraph pTmp = document.createParagraph();
//			document.setParagraph(p, pos);
//			
//			for (XWPFRun r : p.getRuns()) { 
//				pTmp.addRun(r);
//			}
//			pos++;
//		}
		XWPFStyles estilo = documento2.getDocument().getStyles();
		XWPFStyles estiloNuevoDoc = document.createStyles();
		

		
	
		
		pos=0;
		List<IBodyElement> listElements = documento1.getDocument().getBodyElements();
		for(IBodyElement element: listElements) {
			XWPFParagraph pTmp = document.createParagraph();
			if (element.getElementType() == BodyElementType.PARAGRAPH) {
				document.setParagraph((XWPFParagraph)element, pos++);
			}else 
				if(element.getElementType() == BodyElementType.TABLE) {
					document.insertTable(pos++, (XWPFTable)element);
			}

		}
		
		
			
		
		
//		for (XWPFTable tbl : documento2.getDocument().getTables()) {
//			document.insertTable(pos++, tbl);
//			
//		}
		
		FileOutputStream fileOutput = new FileOutputStream (Constants.OUT_PATH + File.separator + Constants.TEST_DOCUMENT_NAME);
		document.write(fileOutput);
		fileOutput.close();
		document.close();
		
	}
}
