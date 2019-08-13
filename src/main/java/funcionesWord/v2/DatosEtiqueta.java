package funcionesWord.v2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosEtiqueta {

	private FragmentoDelDocumento docFragment;
	
	private Boolean parrafoConEtiqueta;
	private Boolean eliminarParrafo;
	private String tipoDeEtiqueta; // campo, ayuda, insert_doc
		
	public DatosEtiqueta(FragmentoDelDocumento docFragment) {
		this.docFragment = docFragment;
		this.parrafoConEtiqueta = false;
		this.eliminarParrafo = false;
	}
	
	public Boolean buscarCompleta(String codInicio, String codFinal) {
		if(docFragment.getTextoParrafo()!=null) {
			if(docFragment.getTextoParrafo().contains(codInicio) && docFragment.getTextoParrafo().contains(codFinal)) {
				return true; //busqueda completa 
			}
		}
		return false; //se deberá hacer una busqueda parcial
	}
	
	public Boolean buscarInicioEtiqueta(String codInicio) {
		if(docFragment.getTextoParrafo()!=null) {
			if(docFragment.getTextoParrafo().contains(codInicio)) {
				return true; //contiene inicio
			}
		}
		return false; //parrafo sin etiqueta
	}
	
	public Boolean buscarFinalEtiqueta(String codFinal) {
		if(docFragment.getTextoParrafo()!=null) {
			if(docFragment.getTextoParrafo().contains(codFinal)) {
				return true; //contiene final
			}
		}
		return false; //parrafo sin etiqueta
	}
	
	public String getEtiqueta(String codInicio, String codFinal) {
		if(this.parrafoConEtiqueta) {
			String textoParrafo = docFragment.getTextoParrafo();
			return textoParrafo.substring(textoParrafo.indexOf(codInicio), textoParrafo.indexOf(codFinal)+codFinal.length()); 
		}
		return "";
	}
	
	public void juntarTexto(String texto) {
		docFragment.addText(texto);
	}	
}
