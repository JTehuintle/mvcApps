package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mvc.SafeFrame;
import mvc.Utilities;
import mvc.Subscriber;

public class AppPanel extends JPanel implements Subscriber, ActionListener {
    protected Model model;
    protected AppFactory factory;
    protected View view;
    private JFrame frame;
    public static int FRAME_WIDTH = 800, FRAME_HEIGHT = 600;

    public AppPanel(AppFactory factory) {
        this.factory = factory;
        this.model = factory.makeModel();
        this.view = factory.makeView(model);
        this.model.subscribe(this);
        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout());
        addControls();
        add((JComponent)view, BorderLayout.CENTER);
    }
    private void addControls() {
        JPanel controls = new JPanel(new GridLayout(3, 3, 2, 2));
        String[] labels = { "NorthWest", "North", "NorthEast", "West", "", "East", "SouthWest", "South", "SouthEast" };
        for (String label : labels) {
            if (label.equals("")) {
                controls.add(new JLabel());
            } else {
                JButton btn = new JButton(label);
                btn.addActionListener(this);
                controls.add(btn);
            }
        }
        add(controls, BorderLayout.WEST);
    }
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] fileItems = {"New", "Save", "SaveAs", "Open", "Quit"};
        JMenu fileMenu = Utilities.makeMenu("File", fileItems, this);
        menuBar.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        menuBar.add(editMenu);
        String[] helpItems = {"About", "Help"};
        JMenu helpMenu = Utilities.makeMenu("Help", helpItems, this);
        menuBar.add(helpMenu);
        return menuBar;
    }
    public void display() {
        frame.setVisible(true);
    }
    @Override
    public void update() { }
    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();
        try {
            switch (cmd) {
                case "Save": Utilities.save(model, false); break;
                case "SaveAs": Utilities.save(model, true); break;
                case "Open": {
                    Model newModel = Utilities.open(model);
                    if (newModel != null) setModel(newModel);
                    break;
                }
                case "New": Utilities.saveChanges(model); setModel(factory.makeModel()); model.setUnsavedChanges(false); break;
                case "Quit": Utilities.saveChanges(model); System.exit(0); break;
                case "About": Utilities.inform(factory.about()); break;
                case "Help": Utilities.inform(factory.getHelp()); break;
                default: {
                    Command cmdObj = factory.makeEditCommand(model, cmd, this);
                    if (cmdObj != null) { cmdObj.execute(); model.setUnsavedChanges(true); }
                    break;
                }
            }
        } catch (Exception e) {
            Utilities.error(e);
        }
    }
    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        view.setModel(newModel);
        model.changed();
    }
    public Model getModel() {
        return model;
    }
    public static void main(String[] args) {
        AppFactory factory = new AppFactory();
        AppPanel panel = new AppPanel(factory);
        panel.display();
    }
}
