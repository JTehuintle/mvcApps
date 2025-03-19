package mvc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AppPanel extends JPanel implements Subscriber, ActionListener {

    protected Model model;
    protected AppFactory factory;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;
    public static int FRAME_WIDTH = 800;
    public static int FRAME_HEIGHT = 600;

    public AppPanel(AppFactory factory) {
        this.factory = factory;
        this.model = factory.makeModel();
        this.view = factory.makeView(this.model);

        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        addControlsAndView();
    }

    private void addControlsAndView() {
        setLayout(new BorderLayout());
        JPanel controls = new JPanel(new GridLayout(3, 3));
        String[] labels = {
                "NorthWest", "North", "NorthEast",
                "West", "", "East",
                "SouthWest", "South", "SouthEast"
        };
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
        add((Component) view, BorderLayout.CENTER);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void update() {
        // no-op for now
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        view.setModel(this.model);
        model.changed();
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "SaveAs", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[]{"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String cmmd = ae.getActionCommand();
            if (cmmd.equals("Save")) {
                Utilities.save(model, false);
            } else if (cmmd.equals("SaveAs")) {
                Utilities.save(model, true);
            } else if (cmmd.equals("Open")) {
                Model newModel = Utilities.open(model);
                if (newModel != null) setModel(newModel);
            } else if (cmmd.equals("New")) {
                Utilities.saveChanges(model);
                setModel(factory.makeModel());
                model.setUnsavedChanges(false);
            } else if (cmmd.equals("Quit")) {
                Utilities.saveChanges(model);
                System.exit(0);
            } else if (cmmd.equals("About")) {
                Utilities.inform(factory.about());
            } else if (cmmd.equals("Help")) {
                Utilities.inform(factory.getHelp());
            } else {
                Command c = factory.makeEditCommand(model, cmmd, this);
                if (c != null) {
                    c.execute();
                    model.setUnsavedChanges(true);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    protected void handleException(Exception e) {
        Utilities.error(e);
    }

    public static void main(String[] args) {
        AppFactory factory = new AppFactory();
        AppPanel panel = new AppPanel(factory);
        panel.setModel(factory.makeModel());
        panel.display();
    }
}
