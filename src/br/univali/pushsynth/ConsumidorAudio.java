package br.univali.pushsynth;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import br.univali.pushsynth.communicacao.Mensagem;
import br.univali.pushsynth.ui.Modo;

public class ConsumidorAudio implements Consumer<Mensagem> {
	private static final int[] NOTAS_DOZE = {72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 73};
	private static final int[] NOTAS_SETE = {72, 74, 76, 77, 79, 81, 83};
	private static final int[] INSTRUMENTOS = {1, 19, 30, 48};
	private Synthesizer synth;
	private MidiChannel channel;
	private Modo modo;
	
	public ConsumidorAudio(Modo modo) throws MidiUnavailableException, InvalidMidiDataException, IOException {
		this.modo = modo;
		synth = MidiSystem.getSynthesizer();
		synth.open();
		channel = synth.getChannels()[0];
		
		URL u = ConsumidorAudio.class.getClassLoader().getResource("genusrmusescore.sf2");
		Soundbank bank = MidiSystem.getSoundbank(u);
		Instrument[] instrumentos = bank.getInstruments();
		for (int indice : INSTRUMENTOS) {
			synth.loadInstrument(instrumentos[indice]);
		}
	}
	
	private int notaModificada(int indice) {
		return (modo == Modo.SETE_TECLAS ? NOTAS_SETE[indice] : NOTAS_DOZE[indice]);
	}
	
	@Override
	public void accept(Mensagem msg) {
		switch (msg.getTipo()) {
		case PRESSIONADO:
			if (msg.getValor() < modo.getValor()) {
				channel.noteOn(notaModificada(msg.getValor()), 127);
			}
			break;
		case SOLTO:
			if (msg.getValor() < modo.getValor()) {
				channel.noteOff(notaModificada(msg.getValor()));
			}
			break;
		case POTENCIOMETRO:
			int val = (int) (msg.getValor() * (16383 / 30f));
			channel.setPitchBend(val);
			break;
		case INSTRUMENTO:
			channel.programChange(INSTRUMENTOS[msg.getValor()]);
			break;
		}
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}
}
