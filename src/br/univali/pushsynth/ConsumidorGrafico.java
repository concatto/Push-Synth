package br.univali.pushsynth;

import java.util.function.Consumer;

import br.univali.pushsynth.communicacao.Mensagem;
import br.univali.pushsynth.ui.SynthScene;

public class ConsumidorGrafico implements Consumer<Mensagem> {
	private SynthScene synthScene;
	
	public ConsumidorGrafico(SynthScene synthScene) {
		this.synthScene = synthScene;
	}
	
	@Override
	public void accept(Mensagem msg) {
		synthScene.receberByte(msg.getDado()); 
		
		switch (msg.getTipo()) {
		case PRESSIONADO:
			synthScene.pressionar(msg.getValor());
			break;
		case SOLTO:
			synthScene.soltar(msg.getValor());
			break;
		case POTENCIOMETRO:
			//Mapeia valores de 0 a 30 para -100 a 100
			int val = (int) ((msg.getValor() * (100 / 15f)) - 100);
			synthScene.afinar(val);
			break;
		case INSTRUMENTO:
			synthScene.alterarInstrumento(msg.getValor());
			break;
		}
	}

}
