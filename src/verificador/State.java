package verificador;

public class State {
    private String name;
    private boolean isFinal;

    public State(String name){
        this.name = name;
    }

    public State(String name, boolean isFinal){
        this.name = name;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public String toString(){
        if(name.equals("Z")){
            return "";
        }
        return name;
    }

}
