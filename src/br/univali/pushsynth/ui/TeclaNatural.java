package br.univali.pushsynth.ui;

import javafx.scene.paint.Color;

public class TeclaNatural extends Tecla {
	private static final String NOMES[] = {"C", "D", "E", "F", "G", "A", "B"};
	
	public TeclaNatural(int indice) {
		super(40, 160, Color.SNOW, Color.GRAY, NOMES[indice]);
	}
}
