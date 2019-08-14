package funcionesWord.v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;


public class FusionarDocumentoWord extends LeerDocumentoWord{

	private List<DatosEtiqueta> docFragmentado = new ArrayList<DatosEtiqueta>();
	
	public void fusionarDatos(List<TagWord> valoresCampo, String tipologiaDoc, String codInicio, String codFinal) throws FileNotFoundException, InvalidFormatException, IOException {
		leerDocumento(codInicio, codFinal);
		for(DatosEtiqueta parrafo : docFragmentado) {
			for(TagWord tag : valoresCampo)
			if(parrafo.getParrafoConEtiqueta() && parrafo.getEtiqueta(codInicio, codFinal).equals(tag.getCodigoTag()) && tag.getTipologia().equals(tipologiaDoc)) { 
				parrafo.getDocFragment().escribirParrafo(parrafo.getDocFragment().getTextoParrafo().replace(tag.getCodigoTag(), tag.getRespuesta()));
				//parrafo.getDocFragment().escribirParrafo(parrafo.getDocFragment().getTextoParrafo().replace(tag.getCodigoTag(), "[REDRUM]"));
				break;
			}
		}
		seleccionarTipologia(tipologiaDoc);
	}
	
	
	private void leerDocumento(String codInicio, String codFinal) throws FileNotFoundException, InvalidFormatException, IOException {
		
		this.openDocument();
		//this.leerParrafosEnTexto();
		//this.leerParrafosEnTablas();
		this.leerDoc();
		
		for(int indice = 0; indice < runs.size(); indice++) {
			DatosEtiqueta parrafo = new DatosEtiqueta(new FragmentoDelDocumento(runs.get(indice)));
			if(parrafo.buscarCompleta(codInicio, codFinal)) {
				parrafo.setParrafoConEtiqueta(true);
				docFragmentado.add(parrafo);
			}else {
				if(!parrafo.buscarInicioEtiqueta(codInicio)) {
					parrafo.setParrafoConEtiqueta(false);
					docFragmentado.add(parrafo);
				}else {
					boolean seEncuentreFinalEtiqueta = false;
					String textoParcialParrafo = "";
					int auxInd = indice;
					docFragmentado.add(parrafo);
					do {
						parrafo = new DatosEtiqueta(new FragmentoDelDocumento(runs.get(++indice)));
						seEncuentreFinalEtiqueta = parrafo.buscarFinalEtiqueta(codFinal);
						parrafo.setEliminarParrafo(true);
						textoParcialParrafo += parrafo.getDocFragment().getTextoParrafo();
						parrafo.getDocFragment().escribirParrafo(""); //borrar el contenido del parrafo
						docFragmentado.add(parrafo);
					}while(seEncuentreFinalEtiqueta==false && indice <  runs.size()-1 && !textoParcialParrafo.contains(codFinal));
					docFragmentado.get(auxInd).juntarTexto(textoParcialParrafo);
					docFragmentado.get(auxInd).setParrafoConEtiqueta(true);
				}
			}
		}
	}
	
	private void seleccionarTipologia(String tipologiaBuscada) {
		for(int indice = 0; indice < docFragmentado.size(); indice++) {
			if(docFragmentado.get(indice).getDocFragment().getTextoParrafo().contains(Constants.CODIGO_INICIO_FRAGMENTO) &&
					tipologia(indice).equals(tipologiaBuscada)==false) {
				do {
					docFragmentado.get(indice).setEliminarParrafo(true);
					docFragmentado.get(indice).getDocFragment().escribirParrafo(""); //borrar el contenido del parrafo
				}while(!docFragmentado.get(++indice).getDocFragment().getTextoParrafo().contains(Constants.CODIGO_FIN_FRAGMENTO) &&
						indice < docFragmentado.size());
				docFragmentado.get(indice).setEliminarParrafo(true);
				docFragmentado.get(indice).getDocFragment().escribirParrafo(""); //borrar el contenido del parrafo
			}
		}
	}
	
	private String tipologia(int indice) {
		int indiceAux = indice;
		String tipologiaEncontrada = ""; 

		do {
			tipologiaEncontrada += docFragmentado.get(indiceAux).getDocFragment().getTextoParrafo();
		}while(!docFragmentado.get(indiceAux++).getDocFragment().getTextoParrafo().contains(Constants.CODIGO_SEPARADOR));
			
		
		tipologiaEncontrada = tipologiaEncontrada.substring(tipologiaEncontrada.indexOf(Constants.CODIGO_INICIO_FRAGMENTO)+Constants.CODIGO_INICIO_FRAGMENTO.length());
		tipologiaEncontrada = tipologiaEncontrada.substring(0, tipologiaEncontrada.indexOf(Constants.CODIGO_SEPARADOR));
		
		return tipologiaEncontrada;
	}
}



