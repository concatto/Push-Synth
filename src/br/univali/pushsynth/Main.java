package br.univali.pushsynth;

import java.io.IOException;
import java.util.prefs.Preferences;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import br.univali.pushsynth.communicacao.GerenciadorPortas;
import br.univali.pushsynth.ui.Modo;
import br.univali.pushsynth.ui.SynthScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String CHAVE_MODO = "modo_teclas";	
	private Preferences prefs;
	
	@Override
	public void start(Stage primaryStage) {
		prefs = Preferences.userNodeForPackage(SynthScene.class);
		Modo modo = Modo.valueOf(prefs.get(CHAVE_MODO, Modo.SETE_TECLAS.toString()));
		
		SynthScene synthScene = new SynthScene(modo);
		ConsumidorGrafico grafico = new ConsumidorGrafico(synthScene);
		ConsumidorAudio audio; 
		
		try {
			audio = new ConsumidorAudio(modo);
		} catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
			e.printStackTrace();
			return;
		}
		
		GerenciadorPortas portas = new GerenciadorPortas();
		if (!portas.abrirExperimental()) {
			System.out.println("Não foi possível abrir nenhuma porta");
			return;
		}
		
		portas.addConsumidor(audio);
		portas.addConsumidor(grafico);
		
		synthScene.setModoListener(m -> {
			prefs.put(CHAVE_MODO, m.toString());
			audio.setModo(m);
		});
		
		primaryStage.setScene(synthScene);
		primaryStage.setTitle("Push Synth");
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(e -> portas.finalizar());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
