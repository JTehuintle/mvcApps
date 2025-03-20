package game;

import javax.swing.*;
import java.awt.*;
import mvc.Subscriber;

public class View extends JPanel implements Subscriber {
    protected Model model;
    private JButton[][] buttons;

    public View(Model m) {
        this.model = m;
        model.subscribe(this);
        initView();
    }

    public void initView() {
        removeAll();
        setLayout(new GridLayout(model.getRows(), model.getColums()));
        buttons = new JButton[model.getRows()][model.getColums()];
        for (int r = 0; r < model.getRows(); r++) {
            for (int c = 0; c < model.getColums(); c++) {
                JButton btn = new JButton();
                btn.setEnabled(false);

                // Adjust button appearance:
                btn.setFont(new Font("Arial", Font.PLAIN, 10));
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setPreferredSize(new Dimension(40, 40));

                buttons[r][c] = btn;
                add(btn);
            }
        }
        revalidate();
        repaint();
    }

    public void setModel(Model newModel) {
        model.unsubscribe(this);
        model = newModel;
        model.subscribe(this);
        initView();
        repaint();
    }

    @Override
    public void update() {
        for (int r = 0; r < model.getRows(); r++) {
            for (int c = 0; c < model.getColums(); c++) {
                JButton btn = buttons[r][c];
                if (r == model.getPlayerRowStart() && c == model.getPlayerColStart()) {
                    btn.setBackground(Color.WHITE);
                    btn.setText(String.valueOf(model.getPeriferalMinesAt(r, c)));
                } else if (model.isVisited(r, c)) {
                    btn.setBackground(Color.LIGHT_GRAY);
                    btn.setText(String.valueOf(model.getPeriferalMinesAt(r, c)));
                } else if (model.isGameOver() && model.isMine(r, c)) {
                    btn.setBackground(Color.RED);
                    btn.setText("M");
                } else {
                    btn.setBackground(Color.BLACK);
                    btn.setText("?");
                }
                if (r == model.getRows() - 1 && c == model.getColums() - 1) {
                    btn.setBackground(Color.GREEN);
                }
            }
        }
        revalidate();
        repaint();
    }
}
