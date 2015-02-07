import java.util.ArrayList;

public class GameBoard {
    private String[][] board;
    private int currPlayer; //1 -> X || 0 -> 0
    private ArrayList<GameBoard> nextMoves;
    private int score;
    private boolean noMoreMoves;

    /**
     * Class constructor.
     */
    public GameBoard(){
        this(1);
    }

    /**
     * Class constructor specifying current player.
     * @param currPlayer current player
     */
    public GameBoard(int currPlayer){
        this.score = 0;
        this.noMoreMoves = false;
        this.currPlayer = currPlayer;
        this.board = new String[3][3];
        this.nextMoves = new ArrayList<GameBoard>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.board[i][j] = "-";
            }
        }
    }

    /**
     * @return <code>true</code> if GAME OVER;
     * <code>false</code> otherwise.
     */
    public boolean gameOver(){
        if(this.board[0][0].equals(this.board[1][1]) &&
                this.board[0][0].equals(this.board[2][2]) &&
                !this.board[0][0].equals("-")) return true;//first diagonal
        if(this.board[0][2].equals(this.board[1][1]) &&
                this.board[0][2].equals(this.board[2][0]) &&
                !this.board[0][2].equals("-")) return true;//second diagonal
        for(int i = 0; i < 3; i++){
            if(this.board[i][0].equals(this.board[i][1]) &&
                    this.board[i][0].equals(this.board[i][2]) &&
                    !this.board[i][0].equals("-")) return true; //lines
            if(this.board[0][i].equals(this.board[1][i]) &&
                    this.board[0][i].equals(this.board[2][i]) &&
                    !this.board[0][i].equals("-")) return true; //columns
        }
        boolean emptySpace = false;
        for(int i = 0; i < 3 && !emptySpace; i++)
            for(int j = 0; j < 3 && !emptySpace; j++)
                if(this.board[i][j].equals("-"))
                    emptySpace = true;

        return !emptySpace;
    }

    /**
     * @return <code>X</code> if currPlayer is 1;
     * <code>0</code> otherwise.
     */
    public String getPlayerSymbol(){
        if(this.currPlayer == 1) return "X";
        else return "0";
    }

    /**
     * Generates recursively all possible moves starting from current
     * configuration taking into account player turns.
     * E.g.
     * X | 0 | X      X | 0 | X        X | 0 | X
     * 0 | 0 | X  =>  0 | 0 | X   OR   0 | 0 | X
     * X | - | -      X | 0 | -        X | - | 0
     */
    public void generateAllPossibleMoves(){
        boolean generatedMove = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.getBoardField(i, j).equals("-")) {
                    generatedMove = true;
                    GameBoard newState = new GameBoard(1 - this.getCurrentPlayer());
                    newState.setBoard(this.getBoard());
                    newState.setBoardField(i, j, this.getPlayerSymbol());
                    this.addNextMove(newState);

                    if(newState.gameOver()){
                        int Q = newState.evaluateBoard();
                        newState.setScore(Q);
                        newState.noMoreMoves = true;

                    }
                    else newState.generateAllPossibleMoves();
                }
            }
        }
        if(!generatedMove) {
            this.setScore(0);
            this.noMoreMoves = true;

        }
    }

    /**
     * Getter for current player.
     * @return currPlayer
     */
    public int getCurrentPlayer(){
        return this.currPlayer;
    }

    /**
     * Add new possible move that can result from the current board.
     * @param nextMove new board configuration.
     */
    public void addNextMove(GameBoard nextMove){
        this.nextMoves.add(nextMove);
    }

    /**
     * @return all the possible moves that can result from
     * the current board configuration.
     */
    public ArrayList<GameBoard> getNextMoves(){
        return this.nextMoves;
    }

    /**
     * Heuristically evaluates the current board using the
     * following scoring formula:
     * +100 (3 Xs in a row)
     * +10 (2 Xs in a row and an empty cell)
     * +1 (1 X in a row and 2 empty cells)
     * -100 (3 0s in a row)
     * -10 (2 0s in a row and an empty cell)
     * -1 (1 0 in a row and 2 empty cell)
     * @return the total score of the current board
     */
    public int evaluateBoard(){
        int boardScore;
        boardScore = 0;

        //evaluate diagonals
        int countXDiag1 = 0, countXDiag2 = 0, count0Diag1 = 0, count0Diag2 = 0;
        for(int i = 0; i < 3; i++){
            if(this.board[i][i].equals("X")) countXDiag1++;
            if(this.board[i][i].equals("0")) count0Diag1++;
            if(this.board[i][3-i-1].equals("X")) countXDiag2++;
            if(this.board[i][3-i-1].equals("0")) count0Diag2++;
        }
        //score for diagonals
        if(countXDiag1 == 3) boardScore += 100;
        else if(countXDiag1 == 2 && count0Diag1 == 0) boardScore += 10;
        else if(countXDiag1 == 1 && count0Diag1 == 0) boardScore += 1;
        if(count0Diag1 == 3) boardScore -= 100;
        else if(count0Diag1 == 2 && countXDiag1 == 0) boardScore -= 10;
        else if(count0Diag1 == 1 && countXDiag1 == 0) boardScore -= 1;
        if(countXDiag2 == 3) boardScore += 100;
        else if(countXDiag2 == 2 && count0Diag2 == 0) boardScore += 10;
        else if(countXDiag2 == 1 && count0Diag2 == 0) boardScore += 1;
        if(count0Diag2 == 3) boardScore -= 100;
        else if(count0Diag2 == 2 && countXDiag2 == 0) boardScore -= 10;
        else if(count0Diag2 == 1 && countXDiag2 == 0) boardScore -= 1;


        //evaluate lines and columns
        int countXLine, countXColumn, count0Line, count0Column;
        for (int i = 0; i < 3; i++) {
            countXLine = 0; count0Line = 0; countXColumn = 0; count0Column = 0;
            for(int j = 0; j < 3; j++){
                if(this.board[i][j].equals("X")) countXLine++;
                if(this.board[i][j].equals("0")) count0Line++;
                if(this.board[j][i].equals("X")) countXColumn++;
                if(this.board[j][i].equals("0")) count0Column++;
            }
            //score for lines and columns
            if(countXLine == 3) boardScore += 100;
            else if(countXLine == 2 && count0Line == 0) boardScore += 10;
            else if(countXLine == 1 && count0Line == 0) boardScore += 1;
            if(countXColumn == 3) boardScore += 100;
            else if(countXColumn == 2 && count0Column == 0) boardScore += 10;
            else if(countXColumn == 1 && count0Column == 0) boardScore += 1;
            if(count0Line == 3) boardScore -= 100;
            else if(count0Line == 2 && countXLine == 0) boardScore -= 10;
            else if(count0Line == 1 && countXLine == 0) boardScore -= 1;
            if(count0Column == 3) boardScore -= 100;
            else if(count0Column == 2 && countXColumn == 0) boardScore -= 10;
            else if(count0Column == 1 && countXColumn == 0) boardScore -= 1;
        }
        return boardScore;
    }

    /**
     * Compute score recursively for current board(starting from
     * the leafs) using MINIMAX technique. (If current player is X,
     * return the MAX of its children's score; otherwise, return the
     * MIN of its children's score.)
     * @return the score for the current board configuration.
     */
    public int computeScore(){
        int score;
        if(this.currPlayer == 1){
            score = -1000000000;
            ArrayList<GameBoard> possibleMoves = this.getNextMoves();
            for(GameBoard A : possibleMoves){
                if(A.noMoreMoves)
                    score = Math.max(score, A.getScore());
                else
                    score = Math.max(score, A.computeScore());
            }
            this.setScore(score);
            return score;
        }
        else{
            score = +1000000000;
            ArrayList<GameBoard> possibleMoves = this.getNextMoves();
            for(GameBoard A : possibleMoves){
                if(A.noMoreMoves)
                    score = Math.min(score, A.getScore());
                else
                    score = Math.min(score, A.computeScore());
            }
            this.setScore(score);
            return score;
        }
    }

    /**
     * Setter for current board score.
     * @param score new score for current board.
     */
    public void setScore(int score){ this.score = score; }

    /**
     * Getter for current board score.
     * @return the current board's score.
     */
    public int getScore(){ return this.score; }

    /**
     * Setter for current board.
     * @param board new board configuration.
     */
    public void setBoard(String[][] board){
        this.board = board;
    }

    /**
     * Getter for current board configuration.
     * @return current board configuration.
     */
    public String[][] getBoard(){
        String newBoard[][] = new String[3][3];
        for(int i = 0; i < 3; i++) {
            System.arraycopy(this.board[i], 0, newBoard[i], 0, 3);
        }
        return newBoard;
    }

    /**
     * Used to set vales in different places of the
     * current board configuration.
     * @param line line where the value is going to be set
     * @param column column where the value is going to be set
     * @param value the value that is going to be set
     */
    public void setBoardField(int line, int column, String value){
        this.board[line][column] = value;
    }

    /**
     * Get the value of a specific field from the current board configuration.
     * @param line field's line
     * @param column field's column
     * @return value of the specified field
     */
    public String getBoardField(int line, int column){
        return this.board[line][column];
    }

    public int getWinningLine(){
        int countLine, countColumn, countDiag1, countDiag2;
        countDiag1 = 0;
        countDiag2 = 0;
        String diagStr = this.getBoardField(1,1), lineString, columnString;
        for(int i=0; i<3; i++){
            countLine = 0; countColumn = 0;
            lineString = this.getBoardField(i,0);
            columnString = this.getBoardField(0,i);
            for(int j=0; j<3; j++){
                if(this.getBoardField(i,j).equals(lineString))
                    countLine++;
                if(this.getBoardField(j,i).equals(columnString))
                    countColumn++;
            }
            if(countLine == 3 && !lineString.equals("-"))
                return i;
            if(countColumn == 3 && !columnString.equals("-"))
                return 3+i;

            if(this.getBoardField(i,i).equals(diagStr))
                countDiag1++;
            if(this.getBoardField(i, 2-i).equals(diagStr))
                countDiag2++;
        }
        if(countDiag1 == 3 && !diagStr.equals("-"))
            return 6;
        if(countDiag2 == 3 && !diagStr.equals("-"))
            return 7;
        return -1;
    }
    @Override
    public String toString(){
        String str = "";
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                str += this.getBoardField(i,j);
            str += '\n';
        }
        return str;
    }
}