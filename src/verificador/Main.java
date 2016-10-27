package verificador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Verificador verificador = new Verificador();
        String word;
        String g = "";
        BufferedReader bufferedReader;
        if(args.length > 0){
            bufferedReader = new BufferedReader(new FileReader(args[0]));
        }else{
            System.out.print("Não ha parametros, digite o nome do arquivo: ");
            bufferedReader = new BufferedReader(new FileReader(in.nextLine()));
        }
        String line;
        while((line = bufferedReader.readLine()) != null){
            g += line;
        }
        verificador.readGrammar(g);
        System.out.println("Gramatica: " + g.replaceAll("\\s+"," "));
        while (true) {
            try {
                System.out.print("Digite a palavra: ");
                word = in.nextLine();
                if(word.length() == 0){
                    return;
                }
                word = word.replaceAll("\\s+|λ|@", "");
            } catch (NoSuchElementException e) {
                return;
            }
            if (verificador.checkWord(word)) {
                System.out.println("Sim");
            } else {
                System.out.println("Não");
            }
        }
    }
}
