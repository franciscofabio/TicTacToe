public class Main{
    CurrBoardSingleton currBoard = CurrBoardSingleton.getInstance();
    UserInterface ui;

    public static GameBoard selectMin(GameBoard board){
        int minim = 100000;
        for(GameBoard A:board.getNextMoves()){
            if(A.getScore() < minim){
                minim = A.getScore();
                board = A;
            }
        }
        return board;
    }
    public void newGame() {
        currBoard.getBoard().generateAllPossibleMoves();
        currBoard.getBoard().computeScore();
        this.ui =  new UserInterface();
        while(true){
            while (currBoard.getPlayer()!=0){
                System.console();
            }

            if(currBoard.getBoard().gameOver()){
                ui.changeWinningLineColor(currBoard.getBoard().getWinningLine());
                ui.drawBoard(currBoard.getBoard());
                ui.removeBtnsMouseListeners();
                return;
            }

            else{
                currBoard.setBoard(selectMin(currBoard.getBoard()));
                currBoard.setPlayer(1);
                if(currBoard.getBoard().gameOver()){
                    ui.changeWinningLineColor(currBoard.getBoard().getWinningLine());
                    ui.drawBoard(currBoard.getBoard());
                    ui.removeBtnsMouseListeners();
                    return;
                }
            }
            ui.drawBoard(currBoard.getBoard());
        }
    }
    public static void main(String args[]){
        Main M = new Main();
        M.newGame();
    }
}