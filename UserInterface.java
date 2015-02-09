import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class UserInterface implements ActionListener{
    JFrame frame;
    JPanel panel;
    JButton[][] buttons;
    JLabel labelMotto;
    JLabel iWant;
    JRadioButton x, o;
    JButton start;
    Boolean startGame = false;

    public UserInterface(){
        this.buttons = new JButton[3][3];
        this.frame = new JFrame("Tic Tac Toe");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(530,300));
        this.frame.setResizable(false);        
        this.panel = new JPanel();
        this.panel.setLayout(null);
        
        this.labelMotto = new JLabel("Ready for a Tic-Tac-Toe challenge?");
        Font newLabelFont=new Font(this.labelMotto.getFont().getName(),Font.ITALIC + Font.BOLD, 16);
        this.labelMotto.setFont(newLabelFont);
        this.labelMotto.setBounds(230, 10, 270, 100);
        this.panel.add(this.labelMotto);
        
        this.iWant = new JLabel("I want to play with: ");
        this.iWant.setFont(null);
        this.iWant.setBounds(260, 50, 220, 100);
        this.panel.add(this.iWant);
        
        this.x = new JRadioButton("X");
        this.x.setBounds(280, 130, 50, 20);
        this.x.setSelected(true);
        this.panel.add(this.x);
        this.o = new JRadioButton("O");
        this.o.setBounds(370, 130, 50, 20);
        this.panel.add(this.o);
        
        ButtonGroup group = new ButtonGroup();
        group.add(this.x);
        group.add(this.o);
        
        this.start = new JButton("Start");
        this.start.setBounds(300, 180, 90, 30);
        
        this.start.addActionListener(this);
		
        
        this.panel.add(this.start);
       
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                this.buttons[i][j] = new JButton("-");
                this.buttons[i][j].setBounds((i+1)*50, (j+1)*50, 50, 50);
                this.buttons[i][j].setContentAreaFilled(false);
                this.buttons[i][j].setEnabled(false);
                final int finalI = i;
                final int finalJ = j;
                this.buttons[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    	if(startGame) {
	                        if(btnIsEnabled(finalI, finalJ))
	                        	setBtn(finalI, finalJ, "X");
	                        CurrBoardSingleton currBoard = CurrBoardSingleton.getInstance();
	                        if(currBoard.getBoard().getBoardField(finalI, finalJ).equals("-")) {
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
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
    public boolean btnIsEnabled(int i, int j) {
    	return this.buttons[i][j].isEnabled();
	}
    
    public void setBtn(int i, int j, String text){
        this.buttons[i][j].setText(text);
        this.buttons[i][j].setEnabled(false);
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
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.start.setText("Restart?");
		startGame = true;
		CurrBoardSingleton currBoard = CurrBoardSingleton.getInstance();
		currBoard.setBoard(new GameBoard(1));
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++) {
				this.buttons[i][j].setBackground(null);
				this.buttons[i][j].setEnabled(true);
				this.setBtn(i, j, currBoard.getBoard().getBoardField(i, j));
				//currBoard.getBoard().generateAllPossibleMoves();
				//currBoard.getBoard().computeScore();
			}
		
	}
}
