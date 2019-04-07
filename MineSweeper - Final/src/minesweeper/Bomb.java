/*Eric Morgan
 *Bomb class that extends tile
 *This class is used to create a bomb object
 *A bomb is a type of tile (child class)
 */
package minesweeper;

    
public class Bomb extends Tile {
    
    boolean endGame;

    public Bomb(int x, int y) {
        super(x, y);

    }

    public Bomb(int x, int y, boolean e) {
        super(x, y);
        endGame = false;
    }

    public boolean getState() {
        return endGame;
    }

    public boolean checkEnd(Bomb b) {
        boolean end = b.getState();
        if (end == true) {
            endGame = true;
        }
        return endGame;
    }
}
