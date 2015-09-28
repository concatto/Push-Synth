package br.univali.pushsynth.ui;

public enum Modo {
	SETE_TECLAS(7), DOZE_TECLAS(12);
	
	private int valor;
	Modo(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
}
