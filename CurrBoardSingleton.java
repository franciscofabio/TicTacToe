public class CurrBoardSingleton{
    private GameBoard board;
    private int player;
    private static CurrBoardSingleton instance= null;
    private CurrBoardSingleton(){
        this.board = new GameBoard();
        this.player = 1;
    }

    public void setPlayer(int player){
        this.player = player;
    }
    public int getPlayer(){
        return this.player;
    }
    public static CurrBoardSingleton getInstance(){
        if(instance == null){
            instance = new CurrBoardSingleton();
        }
        return instance;
    }

    public void setBoard(GameBoard board){
        this.board = new GameBoard();
        this.board = board;
    }
    public GameBoard getBoard(){
        return this.board;
    }

}
