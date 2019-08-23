package funcionesWord.v2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagWord {
	private String codigoTag;
	private String tipologia;
	private String tipoCampo;
	private String textoSolicitud;
	private String respuesta;
	private String aux;
	

	public TagWord() {
		
	}
	
	public TagWord(String codigoTag, String tipoCampo, String textoSolicitud, String ayuda) {
		this.codigoTag = codigoTag;
		this.tipoCampo = tipoCampo;
		this.textoSolicitud = textoSolicitud;
		this.respuesta = "";
		this.aux = ayuda;
	}
	
	public TagWord(String codigoTag, String tipoCampo, String textoSolicitud) {
		this.codigoTag = codigoTag;
		this.tipoCampo = tipoCampo;
		this.textoSolicitud = textoSolicitud;
		this.respuesta = "";
		this.aux = "";
	}

	public TagWord(String codigoTag, String tipologia, String tipoCampo, String textoSolicitud, String respuesta,
			String textoAyuda) {
		super();
		this.codigoTag = codigoTag;
		this.tipologia = tipologia;
		this.tipoCampo = tipoCampo;
		this.textoSolicitud = textoSolicitud;
		this.respuesta = respuesta;
		this.aux = textoAyuda;
	}
	
	
}
