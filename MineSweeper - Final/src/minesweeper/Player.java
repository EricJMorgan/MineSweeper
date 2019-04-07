package minesweeper;

/**
 *The player class is used to create a player object that contains the data necessary for the scoreboard
 * @author Jake Viana
 */

public class Player {

    String name;//players name
    double scorePercent;//players score
    double win, loss;//players wins and losses
    //primary constructor

    public Player(String n) {//creates a player with only a name
        this.name = n;
    }

    //secondary constructor
    public Player(String n, double win, double loss) {//creates a player with name, wins, and, losses
        this.name = n;
        this.win = win;
        this.loss = loss;
    }

    //getter
    public String getName() {
        return name;//returns name
    }

    //getter
    public double getScorePercent() {
        if (loss > 0) {//checks to make sure not dividing by 0
            scorePercent = win / loss; //score = win /loss
        } else {
            scorePercent = 0;//sets score to 0
        }

        return scorePercent;//returns score
    }
    
    //getter
    public double getWin() {
        return win;//returns num of wins
    }
    //setter
    public void setWin(int win) {
        this.win += win;//adds wins
    }
    //getter
    public double getLoss() {
        return loss;//returns losses
    }

    public void setLoss(int loss) {
        this.loss += loss;//adds losses
    }

    @Override
    public String toString() {
        if (loss > 0) {//checks to make sure not dividing by 0
            scorePercent = win / loss; //score = win /loss
        } else {
            scorePercent = 0;//sets score to 0
        }
        return "\n" + name + "\n" + win + "\n" + loss + "\n" + scorePercent + ""; //returns all values
    }

}//return "" + name + "\nWins: " + win + "\nLosses: " + loss + "\nW/L Ratio: " + scorePercent + "\n"; //returns all values
    
