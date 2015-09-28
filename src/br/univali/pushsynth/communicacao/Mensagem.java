package br.univali.pushsynth.communicacao;

public class Mensagem {	
	private Tipo tipo;
	private int valor;
	private byte dado;
	
	public Mensagem() {
		//Em branco
	}
	
	public boolean interpretar(byte b) {
		dado = b;
		boolean valido = true;
		
		switch ((b & 0xFF) >> 5) {
		case 0b001:
			tipo = Tipo.PRESSIONADO;
			break;
		case 0b000:
			tipo = Tipo.SOLTO;
			break;
		case 0b111:
			tipo = Tipo.POTENCIOMETRO;
			break;
		case 0b110:
			tipo = Tipo.INSTRUMENTO;
			break;
		default:
			valido = false;
			break;
		}
		
		valor = b & 0b11111;
		return valido;
	}

	public Tipo getTipo() {
		return tipo;
	}
	
	public int getValor() {
		return valor;
	}
	
	public byte getDado() {
		return dado;
	}
}
