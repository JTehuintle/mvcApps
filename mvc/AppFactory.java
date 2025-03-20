package game;

import mvc.Utilities;
import game.Command;

public class AppFactory {
    public Model makeModel() {
        return new Model();
    }
    public View makeView(Model m) {
        return new View(m);
    }
    public String getTitle() {
        return "Mine Field";
    }
    public String[] getHelp() {
        return new String[] { "Use directional buttons or Edit menu to move.", "Avoid mines and reach the bottom-right corner." };
    }
    public String about() {
        return "'I don't think I'm gonna surrvive midterm 1'- Lu Maw Tun ";
    }
    public String[] getEditCommands() {
        return new String[] { "North", "South", "East", "West", "NorthWest", "NorthEast", "SouthWest", "SouthEast" };
    }
    public Command makeEditCommand(Model model, String type, Object source) {
        switch (type) {
            case "North":      return new MoveCommand(model, Movement.North);
            case "South":      return new MoveCommand(model, Movement.South);
            case "East":       return new MoveCommand(model, Movement.East);
            case "West":       return new MoveCommand(model, Movement.West);
            case "NorthWest":  return new MoveCommand(model, Movement.NorthWest);
            case "NorthEast":  return new MoveCommand(model, Movement.NorthEast);
            case "SouthWest":  return new MoveCommand(model, Movement.SouthWest);
            case "SouthEast":  return new MoveCommand(model, Movement.SouthEast);
            default:           return null;
        }
    }
}
