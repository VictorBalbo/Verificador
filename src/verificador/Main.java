package verificador;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Verificador verificador = new Verificador();
        String word;
        System.out.println("Exemplo de Gramatica: ({A,B,Z}; {0,1}; A -> 0A | 0 | 1B, Z -> @, B -> 1B | @; A)");
        System.out.println("Essa gramatica reconhece os estados A,B,Z");
        System.out.println("O alfabeto 0, 1;");
        System.out.println("A expressão 0*1*");
        System.out.println("E o estado inicial é A");
        System.out.print("Digite a Gramatica Regular: ");
        verificador.readGrammar(in.nextLine());
        while(true){
            System.out.print("Digite a palavra: ");
            word = in.nextLine();
            if(word.equals("-1")){
                return;
            }
            if(verificador.checkWord(word)){
                System.out.println("Sim");
            }else{
                System.out.println("Não");
            }
        }
    }
}
