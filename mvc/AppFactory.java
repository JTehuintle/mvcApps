package mvc;

public class AppFactory {
    public Model makeModel(){
        return new Model();
    }
    public View makeView(Model m){
        return new View(m);
    }
    public String getTitle(){
        return "Mine Field";
    }
    public String[] getHelp(){
        return new String[]{
                "Avoid mines and reach the bottom right corner"
        };
    }
    public String[] getCommands(){
        return new String[]{
                "North, South, East, West, NorthWest, NorthEast, SouthWest, SouthEast"
        };
    }
    public Command makeCommand(Model model, String type)
}
