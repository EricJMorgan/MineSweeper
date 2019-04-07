/*Eric Morgan
 * This class is used to create a tile object
 * A Tile contains an x location, y location, and a number of surrounding bombs
 */

package minesweeper;
import javax.swing.JButton;//import JButton
import javax.swing.JFrame;//import JFrame
import javax.swing.JPanel;//import JPanel


public class Tile {

    int xLoc, yLoc, numBombs; //declare global variables for a tile

    /**
     * Primary Constructor
     * @param x = x location
     * @param y = y location
     */
    public Tile(int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
        numBombs = 0; //set number of bombs to 0 intitially
    }
    /**
     * Accessor method
     * @return the x value
     */
    public int getX(){
        return xLoc;
    }
    /**
     * Accessor method
     * @return the y value
     */
    public int getY(){
        return yLoc;
    }
    /**
     * Accessor 
     * @return the number of bombs around a tile
     */
    public int getNumBombs(){
        return numBombs ;
    }
    /**
     * Calculation method
     * @param t = an array of tiles
     * @param x = x coordinate
     * @param y = y coordinate
     * @param size = the size of the field
     * @return the number of bombs
     */
    public int getNumBombs(Tile t[][], int x, int y, int size) {
        //check the blocks to surrounding the tile to see if they are bombs
        //if they are bombs increase the numBombs of that tile by 1
        if(x+1 < size && t[x+1][y] instanceof Bomb){
            numBombs++;
        }
        if(x-1 >= 0 && t[x-1][y] instanceof Bomb){
            numBombs++;
        }
        if(y+1 < size && t[x][y+1] instanceof Bomb){
            numBombs++;
        }
        if(y - 1 >= 0 && t[x][y-1] instanceof Bomb){
            numBombs++;
        }
        
        if((x+1 < size && y+1 < size) && t[x+1][y+1] instanceof Bomb){
            numBombs++;
        }
        if((x-1 >= 0 && y-1 >= 0) && t[x-1][y-1] instanceof Bomb){
            numBombs++;
        }
        if((x+1 < size && y-1 >= 0) && t[x+1][y-1] instanceof Bomb){
            numBombs++;
        }
        if((x-1 >= 0 && y+1 < size) && t[x-1][y+1] instanceof Bomb){
            numBombs++;
        }
        
        return numBombs;//return the number of bombs
    }

    /**
     * Mutator method
     * @param bombs = number of bombs 
     */
    public void setNumBombs(int bombs) {
        this.numBombs = bombs;//set the number of bombs to the given value
    }
    
   /**
    * Method is used to flip the tile when clicked
    * @param t1 = the tile
    * @param b =  the button
    * @param bombs = the number of bombs
    */
   public void flip(Tile t1, JButton b, int bombs) {
        //if there is no text on the button
        if(b.getText().equals("")){
        b.setText("" + bombs + "");//set the button text to the number of bombs
        b.setEnabled(false);//disable the button
        }
    }

    

}
