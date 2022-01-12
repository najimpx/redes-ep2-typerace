package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMain {

    private WebSocketClient client;

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String idCliente) {
        System.out.println("Iniciando cliente: " + idCliente);
        client.connect();
        // TODO: Implementar
    }

    public static void printOpcoes(String serverName, String clientName){
        System.out.println("SEJA BEM VINDO AO TYPE RACE");
        System.out.println("Digite o numero para prosseguir:");
        System.out.println("1 - Para definir seu nome");
        System.out.println("2 - Para definir o nome do Servidor");
        System.out.println("3 - Para comecar o jogo");
        System.out.println("4 - Para sair do jogo");
        if (!clientName.isBlank()){
            System.out.println("\nNome do Jogador: "+clientName);
        }
        if (!serverName.isBlank()){
            System.out.println("\nNome do Servidor: "+serverName);
        }
    }
    public static int escaneia(Scanner scan){
        int resp;
        try {
            resp = scan.nextInt();

        } catch (InputMismatchException exception) {
            System.out.println("Por favor digite um numero");
            resp = escaneia(scan);
        }
        return resp;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String nomeDoServer = "";
        String idDoCliente = "";
        while(true){
            System.out.println("\u001b[H\u001b[2J");
            printOpcoes(nomeDoServer,idDoCliente);
            int resposta = escaneia(scan);
            //System.out.print("55");
            switch(resposta){
                case 1:
                    System.out.println("\u001b[H\u001b[2J");
                    System.out.print("Digite seu nome: ");
                    scan.nextLine();
                    String nomeCliente = scan.nextLine();
                    while(nomeCliente.isBlank() || ((20 >= nomeCliente.length())&& (nomeCliente.length() <= 3))){
                        System.out.println("\u001b[H\u001b[2J");
                        System.out.println("Nome inválido, insira-o novamente");
                        System.out.println("Seu nome deve ter de 3 ate 20 caracteres");
                        System.out.print("Digite seu nome: ");
                        nomeCliente = scan.nextLine();
                    }
                    idDoCliente = nomeCliente;
                    break;
                case 2:
                    System.out.println("\u001b[H\u001b[2J");
                    System.out.print("Digite o servidor: ");
                    scan.nextLine();
                    String nomeServer = scan.nextLine();
                    while(nomeServer.isBlank() || nomeServer.length() <= 2){
                        System.out.println("\u001b[H\u001b[2J");
                        System.out.println("Nome inválido, insira-o novamente");
                        System.out.println("Seu nome deve ter de mais de 2 caracteres");
                        nomeServer = scan.nextLine();
                    }
                    nomeDoServer = nomeServer;
                    break;
                case 3:
                    if (idDoCliente.isBlank()||nomeDoServer.isBlank()){
                        break;
                    }
                    break;
                case 4:

                    scan.close();
                    return;

                default:
                    break;
            }

        }



    }
}
