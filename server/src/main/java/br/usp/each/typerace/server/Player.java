package br.usp.each.typerace.server;

public class Jogador {
    private String jogador;
    private boolean jogando;
    private int acertos;
    private int posicaoLista;
    private long tempo;
    private int erros;

    Jogador(String id){
        this.jogador = id;
        this.jogando = true;
        this.acertos = 0;
        this.erros = 0;
        this.posicaoLista = 0;
        this.tempo = 0;
    }
    public boolean getJogando(){
        return this.jogando;
    }
    public int getErros() {
        return this.erros;
    }
    public void proximaPalavra(){
        this.posicaoLista++;
    }
    public void setPosicaoDaLista(int i) {
        this.posicaoLista = i;
    }
    public int getposicaoLista() {
        return posicaoLista;
    }
    public void adicionaAcerto() {
        acertos++;
    }
    public void adicionaErro() {
        erros++;
    }
    public int getAcertos() {
        return acertos;
    }
    public long getTempo() {
        return tempo;
    }
    public String getNome() {
        return jogador;
    }
    public void fim(long inicio) {
        this.tempo = (System.nanoTime() - inicio)/1000000000;
        this.jogando = false;
    }

}

