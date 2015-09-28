package br.univali.pushsynth.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public abstract class Tecla extends StackPane {
	public static final Color OUTLINE = Color.valueOf("#4F4F4F");
	protected Rectangle retangulo;
	protected Label nota;
	private Paint corSolta;
	private Paint corPressionada;

	Tecla(double largura, double altura, Paint corSolta, Paint corPressionada, String nome) {
		super();
		this.corSolta = corSolta;
		this.corPressionada = corPressionada;
		
		retangulo = new Rectangle(largura, altura, corSolta);
		nota = new Label(nome);
		nota.setTextFill(Color.valueOf("#9F9F9F"));
		nota.setPadding(new Insets(0, 0, 6, 0));
		getChildren().addAll(retangulo, nota);
		
		setMaxHeight(altura);
		setAlignment(Pos.BOTTOM_CENTER);
	}
	
	public void pressionar() {
		retangulo.setFill(corPressionada);
	}
	
	public void soltar() {
		retangulo.setFill(corSolta);
	}
}
