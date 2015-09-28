package br.univali.pushsynth.ui;

import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VisorBinario extends StackPane {
	private int elementos;

	public VisorBinario(int elementos) {
		super();
		this.elementos = elementos;
		
		setAlignment(Pos.CENTER);
		InputStream in = VisorBinario.class.getClassLoader().getResourceAsStream("bg.jpg");
		setBackground(new Background(new BackgroundImage(new Image(in), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null)));
		setBorder(new Border(new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(1.5))));
		
		for (int i = 0; i < elementos; i++) {
			Label l = new Label();
			
			l.setFont(Font.font("Arial", i == 0 ? FontWeight.BOLD : FontWeight.NORMAL, 20 - i * 3));
			l.setTextFill(Color.HONEYDEW);
			l.setPadding(new Insets(0, 0, Math.log10(i + 1) * 74f, 0));
			l.setOpacity(1 - (Math.log10(i + 1) * 1.25));
			l.setEffect(new GaussianBlur(2));
			
			getChildren().add(l);
		}
	}
	
	public void receber(int numero) {
		for (int i = elementos - 1; i > 0; i--) {
			Label atual = (Label) getChildren().get(i);
			Label anterior = (Label) getChildren().get(i - 1);
			atual.setText(anterior.getText());
		}
		
		String texto = Integer.toBinaryString(numero);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8 - texto.length(); i++) {
			builder.append('0');
		}
		builder.append(texto);
		
		((Label) getChildren().get(0)).setText(builder.toString());
	}
}
