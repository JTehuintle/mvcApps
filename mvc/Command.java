package game;

public abstract class Command implements java.io.Serializable {
    protected Model model;
    public Command(Model model) {
        this.model = model;
    }
    public abstract void execute() throws Exception;
}

