package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class Server extends WebSocketServer {

    private Jogo jogo;
    private final Map<String, WebSocket> connections;

    public Server(int port, Map<String, WebSocket> connections) {//ok
        super(new InetSocketAddress(port));
        this.connections = connections;
        this.jogo = new Jogo();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Nova conexao recebida");
        if (duplicado(conn.getResourceDescriptor().substring(1))) {
            conn.send("Este ID ja esta sendo utilizado!");
            conn.close(1008, "Nome duplicado");
        }
        else{
            String user = conn.getResourceDescriptor().substring(1);
            connections.put(user, conn);
            if (jogo.getEstado()) {
                conn.send("A partida ja está sendo realizada, aguarde a proxima.");
                return;
            }
            broadcast(user + " acabou de entrar!\n" + connections.size()+" %d jogadores esperando.\n" );
            printMenu(conn);
            System.out.println("Conexao realizada");
        }

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

        String jogador = conn.getResourceDescriptor().substring(1);
        System.out.println(jogador +" desconectado.");
        if(jogo.getEstado()){//tira jogador do meio da partida
            jogo.removeJogador(jogo.getJogador(jogador));
        }
        connections.remove(jogador);
        if(!conn.isClosed()){
            conn.close(code, reason);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message.length() == 0) return;
        String jogador = conn.getResourceDescriptor().substring(1);

        if(jogo.getEstado()){
            inputJogando(conn,jogador,message);
            return;
            //input no jogo
        }
        String resposta = message;
        switch(Integer.parseInt(resposta)){
            case 1://start
                broadcast(jogador + " começou o jogo\n");
                jogo.inicia(connections.keySet());
                broadcast(jogo.palavraExibida(jogador));
                break;
            case 2:// quit
                System.out.printf("%s saiu do jogo com sucesso!\n", jogador);
                if(jogador.isEmpty()) return;
                connections.remove(jogador,conn);
                conn.close(1000,"Saida padrao");
                break;
            default:
                conn.send("Comando inexistente.");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            System.out.println("Erro em: "+ conn.getResourceDescriptor().substring(1));
        }
        ex.printStackTrace();

    }

    @Override
    public void onStart() {
        System.out.println("Servidor iniciado");
    }

    //Verifica ids duplicados
    public boolean duplicado(String nome) {
        if(connections.containsKey(nome)){
            return true;
        }
        return false;
    }

    //Imprime menu do jogo
    public void printMenu (WebSocket conn) {
        conn.send("\nBem Vindo ao TYPE RACE\n"+"Digite o comando para prosseguir\n"+"1 - Para comecar o jogo\n"+"2 - Para sair do jogo\n");

    }

    //Pega inputs dos player que estão jogando
    public void inputJogando(WebSocket conn, String user, String message) {
        if (jogo.getJogadoresParticipando() == 0)  {
            jogo.termina();
            broadcast(jogo.geraRanking());
            return;
        }
        if (jogo.getJogador(user) == null){
            return;
        }
        if (jogo.getJogador(user).getJogando()){
            jogo.comparaPalavra(user, message);
        }
        if (!jogo.getEstado()){
            broadcast(jogo.geraRanking());
            return;
        }
        if (!jogo.getJogador(user).getJogando()) {
            conn.send("Você acabou, aguarde os outros jogadores");
        } else {
            conn.send(jogo.palavraExibida(user));
        }
    }


}
