package mvc;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class View extends JPanel implements Subscriber {
    public void setModel(Model newModel){
        //model.unsubscribe(this);
        //model = newModel;
        //model.subscribe(this);
        initView();
        repaint();
    }
    protected Model model;
    private JButton[][] buttons;

    public View(Model m) {
        this.model = m;
        initView();
    }
    public void initView(){
        removeAll();
        setLayout(new GridLayout(model.getRows(),model.getColums()));
        buttons = new JButton[model.getRows()][model.getColums()];
        for(int r = 0; r < model.getRows(); r++){
            for(int c = 0; c < model.getColums(); c++){
                JButton btn = new JButton();
                btn.setEnabled(false);
                buttons[r][c] = btn;
                add(btn);
            }
        }
    }
    public void update(){
        for(int r = 0; r < model.getRows(); r++) {
            for (int c = 0; c < model.getColums(); c++) {
                JButton btn = buttons[r][c];
                if(r == model.getPlayerRowStart() && c == model.getPlayerColStart()){
                    btn.setBackground(Color.white);
                    btn.setText(model.getPeriferalMines() + "");
                }else if(model.isVisited(r, c)){
                    btn.setBackground((Color.GRAY));
                }else{
                    btn.setBackground(Color.black);
                    btn.setText("?");
                }
                if(r == model.getRows()-1 && c == model.getColums() - 1){
                    btn.setBackground(Color.GREEN);
                }
            }
        }
        revalidate();
        repaint();
    }
}
