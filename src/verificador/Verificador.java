package verificador;

import java.util.ArrayList;
import java.util.Arrays;

public class Verificador {

    private final ArrayList<State> states;
    private final ArrayList<String> alphabet;
    private final ArrayList<Rule> rules;
    private State inicial;

    public Verificador() {
        states = new ArrayList<>();
        alphabet = new ArrayList<>();
        rules = new ArrayList<>();
    }

    public void setStates(String states) {
        states = states.replaceAll("\\{|\\}", ""); // Tira {}
        for (String e : states.split(",")) {
            this.states.add(new State(e));
        }
        this.states.add(new State("Z", true));
    }

    public State getStateByName(String name) {
        for (State e : states) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public void setAlphabet(String alphabet) {
        alphabet = alphabet.replaceAll("\\{|\\}", ""); // Tira {}
        this.alphabet.addAll(Arrays.asList(alphabet.split(",")));
        this.alphabet.add("@");
        this.alphabet.add("λ");
    }

    public boolean checkLetter(String letter) {
        for (String l : alphabet) {
            if (l.equals(letter)) {
                return true;
            }
        }
        return false;
    }

    public void setRules(String regras) throws Exception {
        regras = regras.replaceAll("\\{|\\}", ""); // Tira {}
        for (String regra : regras.split(",")) {
            String[] r = regra.split("->");
            if (r.length != 2) {
                throw new Exception("Regra mal formatada");
            }
            State estadoAtual = getStateByName(r[0]);
            if (estadoAtual == null) {
                throw new Exception("Regra mal formatada - Estado invalido:" + r[0]);
            }
            try {
                for (String transicao : r[1].split("\\|")) {
                    State estadoDestino;
                    String letter = transicao.substring(0,1);
                    if(!checkLetter(letter)){
                        throw new Exception("Regra mal formata - Caracter invalido: " + transicao.substring(0,1));
                    }
                    if (transicao.substring(1).equals("")) {
                        estadoDestino = getStateByName("Z");
                    } else {
                        estadoDestino = getStateByName(transicao.substring(1));
                        if (estadoDestino == null) {
                            throw new Exception("Regra mal formatada - Estado invalido:" + transicao.substring(1));
                        }
                    }
                    this.rules.add(new Rule(letter, estadoAtual, estadoDestino));
                }
            } catch (RuntimeException e) {
                throw new Exception("Regra mal formatada");
            }
        }
    }

    public ArrayList<Rule> getPossibleRules(State estado, String elemento) {
        ArrayList<Rule> regras = new ArrayList<>();
        for (Rule r : this.rules) {
            if (r.getState() == estado && r.getElement().equals(elemento)) {
                regras.add(r);
            }
        }
        return regras;
    }

    public void setInicial(String inicial) throws Exception {
        State estado = getStateByName(inicial);
        if (estado == null) {
            throw new Exception("Estado inicial invalido");
        }
        this.inicial = estado;
    }

    // ({A,B,C}; {a,b,c}; A -> aB | bA | cA | λ, B -> aB | bC | cA | λ, C -> aB | bA | λ; A)
    public void readGrammar(String gramatica) throws Exception {
        gramatica = gramatica.replaceAll("\\(|\\)|\\s+|\\n", ""); // Tira parentesis e espaços
        if(gramatica.contains("=")){ // Tira o nome da gramatica do inicio
            gramatica = gramatica.split("=")[1];
        }
        gramatica = gramatica.replaceAll("\\},", "\\};"); // Substitui a , por ;
        String[] g = gramatica.split(";"); // Quebra partes da gramatica
        if (g.length != 4) {
            throw new Exception("Gramatica mal formatada");
        }
        setStates(g[0]);
        setAlphabet(g[1]);
        setRules(g[2]);
        setInicial(g[3]);
    }

    public void printRules() {
        for (Rule r : rules) {
            System.out.println(r.toString());
        }
    }

    boolean checkWord(String palavra) {
        return checkWord(palavra, inicial);
    }

    boolean checkWord(String palavra, State estadoAtual) {
        if (palavra.isEmpty() && estadoAtual.getIsFinal()) { // terminou a palavra
            return true;
        } else if (palavra.isEmpty()) {
            return false;
        }
        ArrayList<Rule> possiveisRegras = getPossibleRules(estadoAtual, String.valueOf(palavra.charAt(0)));
        for (Rule r : possiveisRegras) {
            if (checkWord(palavra.substring(1), r.getDestiny())) {
                return true;
            }
        }
        return false;
    }
}
