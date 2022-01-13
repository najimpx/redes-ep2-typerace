package br.usp.each.typerace.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

//classe que ira gerar as palavras necessárias ao jogo
//http://200.17.137.109:8081/novobsi/Members/cicerog/disciplinas/introducao-a-programacao/arquivos-2016-1/algoritmos/Lista-de-Palavras.txt/view
//txt do link acima irá gerar a lista de palavras
//o txt estará no diretório txt
public class Words {
    File file  = new File(System.getProperty("user.dir") + "\\txt\\Lista-de-Palavras.txt");
    List<String> words = new ArrayList();
    List<String> wordsGame = new ArrayList();

    public List getWords(File file, int quantity) {
        BufferedReader bf;
        {
            try {
                bf = new BufferedReader(new FileReader(file));
                Iterator<String> it = bf.lines().iterator();
                while (it.hasNext()) {
                    String word = String.valueOf(it.next());
                    words.add(word);


                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
           Collections.shuffle(words);
            for(int i = 0; i < quantity; i++){
                wordsGame.add(words.get(i));

            }
        }
        return wordsGame;
    }

    public static void main(String[] args) {

        Words w = new Words();
        List<String> l = w.getWords(w.file, 4);
        for(String a : l){
            System.out.println(a);
        }
    }


}
