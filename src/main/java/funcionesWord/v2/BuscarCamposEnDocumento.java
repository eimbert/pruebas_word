package funcionesWord.v2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class BuscarCamposEnDocumento extends LeerDocumentoWord {
	
	@Getter
	private List<TagWord> etiquetas = new ArrayList<TagWord>();
	
	public void buscarLasEtiquetas(String codInicio, String codFinal) throws FileNotFoundException, InvalidFormatException, IOException {
	 this.openDocument();
	 //this.leerParrafosEnTexto();
	 //this.leerParrafosEnTablas();
	 this.leerDoc();
		
		for(int indice = 0; indice < runs.size(); indice++) {
			if(buscarCompleta(runs.get(indice).getText(0), codInicio, codFinal)) {
				etiquetas.add(infoEtiquetas(runs.get(indice).getText(0), codInicio, codFinal)); 
			}else {
				if(buscarInicioEtiqueta(runs.get(indice).getText(0), codInicio)) {
					boolean seEncuentreFinalEtiqueta = false;
					String textoParcialParrafo = runs.get(indice).getText(0);
					do {
						seEncuentreFinalEtiqueta = buscarFinalEtiqueta(runs.get(++indice).getText(0), codFinal);
						textoParcialParrafo += runs.get(indice).getText(0);
					}while(seEncuentreFinalEtiqueta==false && indice <  runs.size()-1 && !textoParcialParrafo.contains(codFinal));
					etiquetas.add(infoEtiquetas(textoParcialParrafo, codInicio, codFinal));
				}
			}
		}
		this.closeDocumento();
	}
	
	private TagWord infoEtiquetas(String txt, String codInicio, String codFinal) {
		TagWord auxTag = new TagWord();
				
		String etiqueta = txt.substring(txt.indexOf(codInicio), (txt.indexOf(codFinal)+codFinal.length()));
		auxTag.setCodigoTag(etiqueta);
		etiqueta = etiqueta.substring(codInicio.length());
		
		auxTag.setTipologia(etiqueta.substring(0, etiqueta.indexOf(Constants.CODIGO_SEPARADOR)));
		etiqueta = etiqueta.substring(etiqueta.indexOf(Constants.CODIGO_SEPARADOR)+1);
		
		auxTag.setTipoCampo(etiqueta.substring(0, (etiqueta.indexOf(Constants.CODIGO_SEPARADOR))));
		etiqueta = etiqueta.substring(etiqueta.indexOf(Constants.CODIGO_SEPARADOR)+1);

		auxTag.setTextoSolicitud( etiqueta.substring(0, (etiqueta.indexOf(codFinal))));
		
		return  auxTag;
	}
}
