package br.usp.each.typerace.server;

import org.java_websocket.server.WebSocketServer;

import java.util.HashMap;

public class ServerMain {

    private WebSocketServer server;

    public ServerMain(WebSocketServer server) {
        this.server = server;
    }

    public void init() {
        System.out.println("Iniciando servidor...");
        // TODO: Implementar
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
    	System.out.println("Digite o valor da porta");
    	door = Integer.parseInt(scan.nextLine());
       
        WebSocketServer server = new Server(door, new HashMap<>());

        ServerMain main = new ServerMain(server);

        main.init();
        scan.close();
    }

}
