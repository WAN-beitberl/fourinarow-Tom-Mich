import java.io.*;

public class CONNECT4 {
    enum GameState { PLAYER1_VIC, PLAYER2_VIC, GAME}    // enum for game states
    static GameState gamestate;     // static variable for static field main


    public static void main(String[] args){

        gamestate = GameState.GAME;
        Player player1 = new Player();
        Player player2 = new Player();
        Board board = new Board();
        CheckColor(player1, player2);


        // Game in theory runs forever until a victory or a full board
        while(true)
        {
            System.out.println("Insert Coin In Row:");
            board.PlaceCoin(player1.Turn(), player1);   // Get an input from user for row and place it
            gamestate = board.CheckVictory(player1, player2);   // check for victory
            board.PrintBoard();
            if(gamestate != GameState.GAME)     // If game has ended
                break;

            System.out.println("Insert Coin In Row:");
            board.PlaceCoin(player2.Turn(), player2);
            gamestate = board.CheckVictory(player1, player2);
            board.PrintBoard();
            if(gamestate != GameState.GAME)
                break;

        }

        if(gamestate == GameState.PLAYER1_VIC)
        {
            System.out.println("GAME OVER! PLAYER " + player1.getColor() + " WON!");
        }
        else System.out.println("GAME OVER! PLAYER " + player2.getColor() + " WON!");

    }

    // Get color for each player and check if they're not identical
    public static void CheckColor(Player player1, Player player2)
    {
        System.out.println("Please Enter Player Color For Player 1:");
        player1.SetColor();
        System.out.println("Please Enter Player Color For Player 2:");
        player2.SetColor();

        while(player1.PlayerCol == player2.PlayerCol)
        {
            // Complete
        }
    }


}

class Board {



    int[] Col_Capacity; // array to keep the capacity of each column
    int RowSize = 7;    // Matrix sizes
    int ColSize = 6;
    public char[][] gameMat;


    public Board()
    {
        gameMat = new char[RowSize][ColSize];	//Initialize matrix
        Col_Capacity = new int[7];

        for(int i = 0; i < RowSize; i++){
            Col_Capacity[i] = 0;
        }

        for(int i = 0; i < RowSize; i++)
            for(int ii = 0; ii < ColSize; ii++)
            {
                gameMat[i][ii] = '-';
            }
    }

    public void PrintBoard()
    {
        for(int i = 0; i < ColSize; i++)
        {
            for(int ii = 0; ii < RowSize; ii++)
            {
                System.out.print(gameMat[ii][i] + " ");
            }
            System.out.println();
        }

        for(int j = 0; j < RowSize; j++)
        {
            System.out.print((j + 1) + " ");
        }

        System.out.println();


    }

    public boolean PlaceCoin(int row, Player player)    // Place coin on matrix
    {
        // Illegal input
        if(row < 1 || row > RowSize){
            System.out.println("Illegal Input, Please Pick Another Row");
            return false;
        }

        // Max capacity of row
        if(Col_Capacity[row - 1] == ColSize)
        {
            System.out.println("Row Already Full, Please Pick Another Row");
            return false;
        }

        gameMat[row - 1][ColSize - Col_Capacity[row - 1] - 1] = player.getColor();
        Col_Capacity[row - 1]++;
        return true;
    }


    public Main.GameState CheckVictory(Player player1, Player player2)
    {
        Main.GameState victory = Main.GameState.GAME;
        for(int i = 0; i < RowSize; i++)
            for(int ii = 0; ii < ColSize; ii++)
            {
                if(gameMat[i][ii] != '-')
                {
                    // For every filled spot, check if it starts a line
                    if(CheckDiagonal(i, ii) || CheckVer(i, ii) || CheckHor( i, ii))
                    {
                        // Just check which player won if someone indeed won
                        if(gameMat[i][ii] == player1.getColor())
                            victory = Main.GameState.PLAYER1_VIC;
                        else
                            victory = Main.GameState.PLAYER2_VIC;

                        return victory;
                    }

                }
            }
        return victory;
    }


    public boolean CheckDiagonal (int x, int y)
    {
        int cnt = 1;
        while(y + cnt < ColSize && x + cnt < RowSize && cnt < 4)
        {
            if(gameMat[x][y + cnt] == gameMat[x][y])
                cnt++;
            else
            {

                break;
            }
        }

        if(cnt == 4)
            return true;
        cnt = -1;

        while(x + cnt > 0 && y + cnt > 0 && cnt > -4)
        {
            if(gameMat[x][y + cnt] == gameMat[x][y])
                cnt--;
            else
            {
                return false;
            }
        }

        if(cnt == -4)
            return true;

        cnt = 1;
        while(y + cnt < ColSize && x - cnt > 0 && cnt < 4)
        {
            if(gameMat[x][y + cnt] == gameMat[x][y])
                cnt++;
            else
            {

                break;
            }
        }

        if(cnt == 4)
            return true;
        cnt = -1;

        while(x + cnt > 0 && y - cnt < ColSize && cnt > -4)
        {
            if(gameMat[x][y + cnt] == gameMat[x][y])
                cnt--;
            else
            {
                return false;
            }
        }

        if(cnt == -4)
            return true;

        return false;
    }

    public boolean CheckVer(int x, int y)
    {
        int cnt = 1;
        while(y + cnt < ColSize && cnt < 4)
        {
            if(gameMat[x][y + cnt] == gameMat[x][y])
                cnt++;
            else
            {

                break;
            }
        }

        if(cnt == 4)
            return true;
        cnt = -1;

        while(x + cnt > 0 && cnt > -4)
        {
            if(gameMat[x][y + cnt] == gameMat[x][y])
                cnt--;
            else
            {
                return false;
            }
        }

        if(cnt == -4)
            return true;
        return false;
    }


    public boolean CheckHor(int x, int y)
    {
        int cnt = 1;
        while(x + cnt < RowSize && cnt < 4)
        {
            if(gameMat[x + cnt][y] == gameMat[x][y])
                cnt++;
            else
            {

                break;
            }
        }

        if(cnt == 4)
            return true;
        cnt = -1;

        while(x + cnt > 0 && cnt > -4)
        {
            if(gameMat[x + cnt][y] == gameMat[x][y])
                cnt--;
            else
            {
                return false;
            }
        }

        if(cnt == -4)
            return true;
        return false;
    }


}

import java.util.Scanner;
class Player {

    public char PlayerCol;
    private Scanner scanner;    // Get user input

    public Player(){
        scanner = new Scanner(System.in);
    }

    public char getColor()
    {
        return PlayerCol;
    }

    public void SetColor(){
        String col = scanner.nextLine();
        col.toUpperCase();
        PlayerCol = col.charAt(0);
    }

    public int Turn(){
        return scanner.nextInt();
    }
}