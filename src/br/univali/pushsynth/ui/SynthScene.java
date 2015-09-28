package br.univali.pushsynth.ui;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SynthScene extends Scene {
	private static final String[] INSTRUMENTOS = {"Teclado", "Órgão", "Guitarra", "Violino"};
	private Modo modo;
	private Consumer<Modo> modoListener;
	
	private HBox sustenidas;
	private HBox naturais;
	private MenuBar barraMenu;
	private StackPane teclas;
	private VBox afinacao;
	private Slider afinador;
	private HBox conteudo;
	private VBox tabelaContainer;
	private VBox controlesContainer;
	private VBox raiz;
	private GridPane tabela;
	private VisorBinario visor;
	
	public SynthScene(Modo modo) {
		super(new VBox());
		raiz = (VBox) getRoot();
		teclas = new StackPane();
		conteudo = new HBox(6);
		conteudo.setPadding(new Insets(10, 10, 10, 10));
		conteudo.setAlignment(Pos.CENTER_LEFT);
		
		this.modo = modo;
		
		inicializarMenu();
		inicializarDireita();
		inicializarControles();
		
		conteudo.getChildren().addAll(controlesContainer, new Separator(Orientation.VERTICAL), tabelaContainer);
		raiz.getChildren().addAll(barraMenu, conteudo);
		
		alterarModo(modo);
		alterarInstrumento(0);
	}
	
	private void inicializarControles() {
		inicializarNaturais();
		inicializarSustenidas(); 
		inicializarAfinacao();
		
		controlesContainer = new VBox(8, teclas, afinacao);
		controlesContainer.setPadding(new Insets(0, conteudo.getSpacing() - 3, 0, 0));
		teclas.getChildren().add(naturais);
	}

	private void inicializarDireita() {
		tabelaContainer = new VBox(8);
		tabelaContainer.setAlignment(Pos.TOP_CENTER);
		
		visor = new VisorBinario(5);
		tabela = new GridPane();
		
		tabela.add(new Celula("S1", true), 0, 0);
		tabela.add(new Celula("S2", true), 1, 0);
		tabela.add(new Celula("Instrumento", true), 2, 0);
		
		for (int i = 0; i < 4; i++) {
			tabela.add(new Celula(String.valueOf(i >> 1)), 0, i + 1);
			tabela.add(new Celula(String.valueOf(i & 1)), 1, i + 1);
			tabela.add(new Celula(INSTRUMENTOS[i]), 2, i + 1);
		}
		
		tabela.setBorder(new Border(new BorderStroke(Color.DIMGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5))));
		Separator separador = new Separator();
		separador.setPadding(new Insets(2, 0, 0, 0));
		tabelaContainer.getChildren().addAll(tabela, separador, visor);
	}

	private void inicializarAfinacao() {
		afinador = new Slider(-100, 100, 0);
		afinador.setDisable(true);
		afinador.setShowTickMarks(true);
		
		Label grave = new Label("Grave");
		Label agudo = new Label("Agudo");
		
		AnchorPane labelsAfinador = new AnchorPane(grave, agudo);
		
		AnchorPane.setLeftAnchor(grave, 4d);
		AnchorPane.setRightAnchor(agudo, 4d);
		
		afinacao = new VBox(afinador, labelsAfinador);
	}

	private void inicializarMenu() {
		barraMenu = new MenuBar();
		Menu modoMenu = new Menu("Modo");
		
		ToggleGroup modoToggle = new ToggleGroup();
		RadioMenuItem seteItem = new RadioMenuItem("Sete teclas");
		RadioMenuItem dozeItem = new RadioMenuItem("Doze teclas");
		
		seteItem.setOnAction(e -> alterarModo(Modo.SETE_TECLAS));
		seteItem.setToggleGroup(modoToggle);
		dozeItem.setOnAction(e -> alterarModo(Modo.DOZE_TECLAS));
		dozeItem.setToggleGroup(modoToggle);
		
		modoToggle.selectToggle(modo == Modo.SETE_TECLAS ? seteItem : dozeItem);
		
		modoMenu.getItems().addAll(seteItem, dozeItem);
		barraMenu.getMenus().add(modoMenu);
	}

	private void alterarModo(Modo modo) {
		this.modo = modo;
		if (modoListener != null) {
			modoListener.accept(modo);
		}
		
		if (modo == Modo.SETE_TECLAS) {
			teclas.getChildren().remove(sustenidas);
		} else {
			teclas.getChildren().add(sustenidas);
		}
	}
	
	public void setModoListener(Consumer<Modo> modoListener) {
		this.modoListener = modoListener;
	}

	private void inicializarNaturais() {
		naturais = new HBox(1.2);
		naturais.setAlignment(Pos.TOP_LEFT);
		naturais.setPadding(new Insets(1, 1, 1.25, 1));
		naturais.setBackground(new Background(new BackgroundFill(Tecla.OUTLINE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		for (int i = 0; i < 7; i++) {
			naturais.getChildren().add(new TeclaNatural(i));
		}
	}

	private void inicializarSustenidas() {
		sustenidas = new HBox(18);
		sustenidas.setAlignment(Pos.TOP_LEFT);
		sustenidas.setPadding(new Insets(0, 0, 0, 31));
		
		for (int i = 0; i < 6; i++) {
			sustenidas.getChildren().add(new TeclaSustenida(i, i == 2));
		}
	}
	
	private Tecla obterTecla(int indice) {
		if (modo == Modo.SETE_TECLAS) {
			return (Tecla) naturais.getChildren().get(indice);
		} else {
			//Natural caso <= 4 e par ou > 4 e ímpar; Sustenida se <= 4 e ímpar ou > 4 e par
			HBox teclas = (indice % 2 == (indice <= 4 ? 0 : 1)) ? naturais : sustenidas;
			
			int indiceAjustado = indice / 2;
			if (indice > 4) indiceAjustado -= (indice % 2);
			
			return (Tecla) teclas.getChildren().get(indiceAjustado);
		}
	}
	
	public void pressionar(int tecla) {
		if (tecla < modo.getValor()) {
			obterTecla(tecla).pressionar();
		}
	}
	
	public void soltar(int tecla) {
		if (tecla < modo.getValor()) {
			obterTecla(tecla).soltar();
		}
	}
	
	public void afinar(int afinacao) {
		Platform.runLater(() -> afinador.setValue(afinacao));
	}
	
	public void alterarInstrumento(int indice) {
		Platform.runLater(() -> {
			for (Node node : tabela.getChildren()) {
				if (GridPane.getRowIndex(node) == indice + 1) {
					((Celula) node).selecionar();
				} else {
					((Celula) node).normalizar();
				}
			}
		});
	}
	
	public void receberByte(byte b) {
		Platform.runLater(() -> visor.receber(b & 0xFF));	
	}
}
