package br.univali.pushsynth.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Celula extends Label {
	private static final Background NORMAL = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
	private static final Background SELECIONADO = new Background(new BackgroundFill(Color.valueOf("#D5FFD5"), CornerRadii.EMPTY, Insets.EMPTY));
	private static final Background HEADER = new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY));
	
	private boolean header;
	
	public Celula(String texto) {
		this(texto, false);
	}
	
	public Celula(String texto, boolean header) {
		super(texto);
		this.header = header;
		
		setPadding(new Insets(3, 5, 3, 5));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, new Insets(-1))));
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		setAlignment(Pos.CENTER);
		
		normalizar();
	}
	
	public void selecionar() {
		setBackground(SELECIONADO);
	}
	
	public void normalizar() {
		setBackground(header ? HEADER : NORMAL);
	}
}
