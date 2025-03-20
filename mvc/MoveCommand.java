package game;

public class MoveCommand extends Command {
    private Movement movement;
    public MoveCommand(Model model, Movement movement) {
        super(model);
        this.movement = movement;
    }
    @Override
    public void execute() throws Exception {
        model.move(movement);
    }
}
