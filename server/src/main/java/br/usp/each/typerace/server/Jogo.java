package br.usp.each.typerace.server;
import java.util.*;
import java.util.Random;

public class Jogo {

    private long inicio;
    private boolean iniciado;
    private int jogadoresParticipando;
    private int maxScore;
    private Map<String, Jogador> jogadores;
    private String[] listaDePalavras;


    Jogo(){
        this.inicio = 0;
        this.iniciado = false;
        this.jogadoresParticipando = 0;
        this.jogadores = new HashMap<>();


    }
    public int getJogadoresParticipando(){
        return this.jogadoresParticipando;
    }

    public int getMaxScore(){
        return this.maxScore;
    }

    public void inicia(Set<String> listaDeJogadores){
        this.iniciado = true;
        this.geraListas();
        this.maxScore = listaDePalavras.length;
        this.jogadores = new HashMap<>();
        this.jogadoresParticipando = 0;
        this.prepararJogadores(listaDeJogadores);

        this.inicio = System.nanoTime();
    }

    //Acaba partida
    public void termina(){
        this.iniciado = false;
    }

    //Coloca joadores para jogar
    public void prepararJogadores(Set<String> listaDeJogadores){
        for (String playerName : listaDeJogadores) {
            jogadores.put(playerName, new Jogador(playerName));
        }
        this.jogadoresParticipando = listaDeJogadores.size();
    }

    //Verifica se a palavra do jogador está certa
    public void comparaPalavra(String player, String palavraTentada){//ver oq fazer com condicçoes de vitoria
        Jogador jogador = jogadores.get(player);
        int n = jogador.getposicaoLista();

        if(listaDePalavras[n].equals(palavraTentada)){//acerto
            jogador.adicionaAcerto();
            if(jogador.getposicaoLista() == listaDePalavras.length - 1){// finalizou
                System.out.println((61));
                jogador.setPosicaoDaLista(0);
            }
            if (jogador.getAcertos() == this.getMaxScore()) {
                System.out.println((65));
                removeJogador(jogador);
                if (jogadoresParticipando == 0) this.iniciado = false;
            }
        }
        else{//erro
            jogador.adicionaErro();
        }
        jogador.proximaPalavra();
    }

    //Tira jogador do jogo
    public void removeJogador(Jogador jogador){
        jogador.fim(inicio);
        this.jogadoresParticipando--;
    }

    public Jogador getJogador(String nome){
        return jogadores.get(nome);
    }

    //Cria lista de palavras
    public void geraListas(){
        String[] teste = {"Anatidaefobia e a fobia  de ser observado ou perseguido por patos",
                "A altura ideal para derrubar uma torrada com manteiga e fazer com que ela caia com a manteiga para cima e de dois metros e meio",
                "A velocidade media com que um pum sai do seu corpo e de tres metros por segundo",
                "No nosso querido Brasil existem 23 homens com o nome Mulher e uma mulher chamada Homem"};
        Random gerador = new Random();
        this.listaDePalavras = teste[gerador.nextInt(teste.length)].split(" ");
    }

    public boolean getEstado(){
        return iniciado;
    }

    //Exibe palavra ao player
    public String palavraExibida(String user) {
        Jogador player = jogadores.get(user);
        String retorno = ("Palavra "+(player.getposicaoLista()+1 )+"\n Escreva: "+listaDePalavras[player.getposicaoLista()]);
        return retorno;
    }

    //Gera ranking
    public String geraRanking() {
        StringBuilder ranking = new StringBuilder();
        ranking.append("\n                       RANKING\n");
        ranking.append(" Ranking |         Nome         | Acertos | Erros \n");
        int posicao = 1;
        float duracaoPartida = 0;
        PriorityQueue<Jogador> listaOrdenada ;
        listaOrdenada = new PriorityQueue<Jogador>(new ComparaJogadores());
        for (Jogador player : jogadores.values()) {
            listaOrdenada.add(player);
        }
        while (!listaOrdenada.isEmpty()) {
            Jogador player = listaOrdenada.poll();
            float duracaoPlayer = player.getTempo();
            if (duracaoPlayer > duracaoPartida) {
                duracaoPartida = duracaoPlayer;
            }
            ranking.append(String.format(" %-7d | %-20s | %-7d | %-4d \n",posicao++,player.getNome(),player.getAcertos(),player.getErros()));
        }
        ranking.append(String.format("\nDuracao da partida: %10.2f segs\n", duracaoPartida));
        return ranking.toString();
    }

}

class ComparaJogadores implements Comparator<Jogador> {
    @Override
    public int compare(Jogador jogador1, Jogador jogador2) {
        if(jogador1.getAcertos() > jogador2.getAcertos()) return -1;
        if(jogador1.getAcertos() < jogador2.getAcertos()) return 1;
        if(jogador1.getTempo() > jogador2.getTempo()) return 1;
        if(jogador1.getTempo() < jogador2.getTempo()) return -1;
        return 0;
    }
}

