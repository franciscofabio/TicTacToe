import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class UserInterface{
    JFrame frame;
    JPanel panel;
    JButton[][] buttons;

    public UserInterface(){
        this.buttons = new JButton[3][3];
        this.frame = new JFrame("Tic Tac Toe");
        this.frame.setDefaultCloseOperation(this.frame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(300,300));
        this.panel = new JPanel(new GridLayout(3,3));
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                this.buttons[i][j] = new JButton("-");
                this.buttons[i][j].setContentAreaFilled(false);
                final int finalI = i;
                final int finalJ = j;
                this.buttons[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setBtn(finalI, finalJ, "X");
                        CurrBoardSingleton currBoard = CurrBoardSingleton.getInstance();
                        if(currBoard.getBoard().getBoardField(finalI, finalJ).equals("-")){
                            String boardStr[][] = currBoard.getBoard().getBoard();
                            boardStr[finalI][finalJ] = "X";
                            for(GameBoard A : currBoard.getBoard().getNextMoves()){
                                if(Arrays.deepEquals(A.getBoard(), boardStr)){
                                    currBoard.setBoard(A);
                                    currBoard.setPlayer(0);
                                }
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                this.panel.add(buttons[i][j]);
            }
        }
        this.frame.add(this.panel);
        this.frame.pack();
        this.frame.setVisible(true);
    }
    public void setBtn(int i, int j, String text){
        this.buttons[i][j].setText(text);
    }
    public void drawBoard(GameBoard board){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                this.setBtn(i, j, board.getBoardField(i, j));
            }
        }
    }
    public void setLineColor(int line){
        for(int i=0; i<3; i++){
            this.buttons[line][i].setContentAreaFilled(true);
            this.buttons[line][i].setBackground(Color.gray);
        }
    }
    public void setColumnColor(int column){
        for(int i=0; i<3; i++){
            this.buttons[i][column].setContentAreaFilled(true);
            this.buttons[i][column].setBackground(Color.gray);
        }
    }
    public void setDiagonal1Color(){
        for(int i=0; i<3; i++){
            this.buttons[i][i].setContentAreaFilled(true);
            this.buttons[i][i].setBackground(Color.gray);
        }
    }
    public void setDiagonal2Color(){
        for(int i=0; i<3; i++) {
            this.buttons[i][2 - i].setContentAreaFilled(true);
            this.buttons[i][2 - i].setBackground(Color.gray);
        }
    }
    public void changeWinningLineColor(int WL){
        if(WL == -1) return;
        if(WL < 3){ setLineColor(WL); return; }
        if(WL < 6){ setColumnColor(WL-3); return; }
        if(WL == 6){ setDiagonal1Color(); return; }
        if(WL == 7){ setDiagonal2Color(); return; }
    }
    public void removeBtnsMouseListeners(){
        for(int i=0; i<3; i++){
            for(int j = 0; j<3; j++) {
                for (MouseListener ML : this.buttons[i][j].getMouseListeners())
                    this.buttons[i][j].removeMouseListener(ML);
            }
        }
    }
}
