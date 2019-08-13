package funcionesWord.v2;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FragmentoDelDocumento {

	private XWPFRun parrafo;
	private String textoParrafo;
	
	public FragmentoDelDocumento(XWPFRun parrafo){
		this.parrafo = parrafo;
		this.textoParrafo = parrafo.getText(0);
	}
	
	public String getPrimerCatacter() {
		if(textoParrafo.length() > 0)
			return textoParrafo.substring(0,1);
		else
			return "";
	}
	
	public String getUltimoCarcater() {
		if(textoParrafo.length() > 0)
			return textoParrafo.substring(textoParrafo.length()-1);
		else
			return "";
	}
	
	public String getTextoParrafo() {
		return this.textoParrafo;
	}
	
	public void setTextoParrafo(String texto) {
		this.textoParrafo = texto;
	}
	
	public void addText(String texto) {
		this.textoParrafo += texto;
	}
	
	public void escribirParrafo() {
		parrafo.setText(textoParrafo, 0);
	}
	
	public void escribirParrafo(String texto) {
		parrafo.setText(texto, 0);
	}
	
	public void borrarParrafo() {
		parrafo.setText("", 0);
	}
}
