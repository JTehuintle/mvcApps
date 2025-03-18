package mvc;

public class Command implements Serializable {
    protected Model model;
    public Command(Model model){
        this.model = model;
    }
    public abstract void execute() throws Exception;

    class MoveCommand extends Command{
        Movement movement;
        public MoveCommand(Model model, Movement movement){
            super(model);
            this.movement = movement;
        }
    }
}
