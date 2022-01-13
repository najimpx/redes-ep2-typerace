package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
//import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Conexão criada!\n");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("\u001b[H\u001b[2J");
        System.out.println("\nMessagem recebida :" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("\u001b[H\u001b[2J");
        if (code == 1000) {
            System.out.println("Desconectado");
        }
        else {
            System.out.println("Desconexão imprevista.");
        }

        System.out.println("Motivo: "+reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("\u001b[H\u001b[2J");
        System.out.println("Erro" + ex.getMessage());
        ex.printStackTrace();
    }
}
