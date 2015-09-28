package br.univali.pushsynth.communicacao;

import java.util.function.Consumer;

public class Decodificador {
	private Runnable keepaliveAction;
	private Consumer<Mensagem> mensagemAction;
	private Mensagem mensagem = new Mensagem();
	
	public Decodificador() {
		
	}
	
	public void onKeepalive(Runnable keepaliveAction) {
		this.keepaliveAction = keepaliveAction;
	}
	
	public void onMensagem(Consumer<Mensagem> mensagemAction) {
		this.mensagemAction = mensagemAction;
	}
	
	public void decodificar(byte[] dados) {
		for (byte dado : dados) {
			decodificar(dado);
		}
	}
	
	public void decodificar(byte dado) {
		if (((dado & 0xFF) >> 4) == Protocolo.KEEPALIVE) {
			if (((dado & 0xF) == 0) && keepaliveAction != null) {
				keepaliveAction.run();
			}
		} else {
			if (mensagem.interpretar(dado)) {
				mensagemAction.accept(mensagem);
			}
		}
	}
	
	public static byte testarHandshake(byte dado) {
		if ((dado & 0xFF) >> 4 == Protocolo.HANDSHAKE) {
			return (byte) (dado & 0xF);
		}
		
		return -1;
	}
}
