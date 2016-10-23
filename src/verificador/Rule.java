package verificador;

public class Rule {
    private String element;
    private State state;
    private State destiny;

    public Rule(String element, State state, State destiny){
        this.element = element;
        this.state = state;
        this.destiny = destiny;
        if(element.equals("@")){
            this.state.setIsFinal(true);
        }
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getDestiny() {
        return destiny;
    }

    public void setDestiny(State destiny) {
        this.destiny = destiny;
    }

    @Override
    public String toString(){
        return state + " -> " + element + destiny;
    }
}
