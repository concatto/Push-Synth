package br.univali.pushsynth.ui;

import javafx.scene.paint.Color;

public class TeclaSustenida extends Tecla {
	private static final String NOMES[] = {"C#", "D#", "", "F#", "G#", "A#"};
	
	public TeclaSustenida(int indice) {
		this(indice, false);
	}
	
	public TeclaSustenida(int indice, boolean vazia) {
		super(22, 80, vazia ? Color.TRANSPARENT : Color.valueOf("#555555"), vazia ? Color.TRANSPARENT : Color.valueOf("#0F0F0F"), NOMES[indice]);
		
		if (!vazia) retangulo.setStroke(Tecla.OUTLINE);
	}
}
