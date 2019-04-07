/**
 * Welcome to MineSweeper
 * This class is the game running class of this project. It is used to run
 * everything in the main game and build the JFrame for the game page.
 *
 * @author Eric Morgan
 * @author Jake Viana
 */


package minesweeper;

import java.awt.Color; //import colour features
import java.awt.GridLayout; //import grid layout
import java.awt.event.MouseAdapter;//import mouse adapter
import java.awt.event.MouseEvent;//import mouse event
import java.util.Random; //import random number utility
import javax.swing.ImageIcon; //import imageIcon
import javax.swing.JButton;//import jbutton package
import javax.swing.SwingUtilities;//import swing utilities
import java.io.BufferedReader; //import buffered reader
import java.io.BufferedWriter; //import buffered writer
import java.io.FileReader; //import file reader
import java.io.FileWriter;//import file writer
import java.io.IOException; //import io exception
import java.util.ArrayList; //import arraylist


public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    public static int size;//the size of the game field to be played on
    public static int colour;//colour variable for customization
    public static JButton btn[][];//array of buttons for game field
    public static Tile tiles[][];//array of tiles for mine field
    ImageIcon facePic = new ImageIcon("SRC//faces.jpg"); //smiley face picture for game
    String newName;//the name the user enters
    boolean pastPlayer = false; //has this player played before
    int index = 0;//counts how many people are in the file
    int currentIndex = 0;//counts how many people until a past player is found
    Player currentPlayer;// player the user is changing
    ArrayList<Player> players = new ArrayList(); // arrayList of players

    /**
     * Create a new game
     */
    public Game() {
        this.newName = MainMenu.name;
        size = FieldChoice.size; //get the user's choice for the size of the field
        btn = new JButton[size][size]; //set the array of buttons to the size of the field
        initComponents();//build the JFrame
        readFile();//reads in list of players
        nameSearch(); //searches for past player
        faceLabel.setIcon(facePic);//bring in the face picture
        this.setBounds(0, 0, size * 43, size * 38); //set the bounds of the JFrame
        gamePanel.setLayout(new GridLayout(size, size, -3, -3)); //add a grid layout to the jpanel that the game will be on
        createTiles(); //run the createTiles method
        drawBtns();//run the drawButtons method
        writePlayer();//writes currentPlayer into file
    }

    /**
     * This method reads in a file of players and adds it to an arrayList of
     * players
     */
    public void readFile() {
        boolean endOfFile;
        String name; // name of player
        double win, loss; //win and losses of player
        try {
             //try to create connection to file
            FileReader fr = new FileReader("src\\players.txt"); //file must be located in project src folder
            BufferedReader br = new BufferedReader(fr);
            String not = br.readLine();//used as nothing
            endOfFile = false;
            
            while (!endOfFile) { //loop until the end of file is reached
                name = br.readLine(); //read in first record
                
                if (name == null) { //check if read operation got no data
                    endOfFile = true;
                } else {//otherwise process rest of record
                    index++;//counts number of times looped
                    
                    if (name.equals(newName)) {//check if player name exists already
                        pastPlayer = true; //sets pastPlayer to true
                        currentIndex = index; //sets the index of the past player
                    }
                    win = Double.parseDouble(br.readLine()); //number of wins
                    loss = Double.parseDouble(br.readLine()); //number of losses
                    not = br.readLine(); //extra
                    players.add(new Player(name, win, loss)); //creates new Player and adds it to arrayList

                }//end of if
            }//end of the while

            br.close(); //close the stream to the file when done
        } catch (IOException e) { //catch the exception caused if the file is not found
            System.out.println("Error: " + e.toString());
            
        }

    }

    /**
     * This method writes the players to the file again
     */
    public void writePlayer() {
        try {
            //try to create connection to the file
            BufferedWriter out = new BufferedWriter(new FileWriter("src//players.txt"));//file must be located in project src folder
            out.write(players.toString());//writes the ArrayList of players to the file
            out.write(currentPlayer.toString());//writes the current player to the file
            out.close();//close connection to the file
        } catch (IOException e) {//catch the exception caused if the file is not found
            System.out.println("error" +e);
        }
    }

    /**
     * This method is used to Determine which player is being modified
     */
    public void nameSearch() {

        if (newName.equals("")) { //do nothing if name is blank

        } else if (pastPlayer == true) {//if past player is true
            
            currentPlayer = players.get(currentIndex - 1);//sets current player to the past player
            players.remove(currentIndex - 1);//removes the past player

        } else if (pastPlayer == false) {//if past player is false

            players.add(new Player(newName));//creates new player with new name
            currentIndex = players.size() - 1; //sets current index to the location of the new player
            currentPlayer = players.get(currentIndex); //sets current player to the new player
            players.remove(currentIndex);//removes old player
        }

    }

    /**
     * This method is used to draw the 2D array of buttons on the JFrame
     */
    public void drawBtns() {
        colour = Settings.backgroundC; //set colour to the user's choice of colour in the settings menu
        //this nested loop will create the buttons
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int x = i; //set x to be i
                final int y = j;//set y to be j
                JButton b = new JButton("");//create a new blank JButton

                //This if statement checks to see which colour the user chose on the settings menu
                if (colour == 1) {
                    b.setBackground(Color.red);//set the colour of the button to red
                } else if (colour == 2) {
                    b.setBackground(Color.blue);//set the colour of the button to blue
                } else if (colour == 3) {
                    b.setBackground(Color.green);//set the colour of the button to green
                }
                //set the button at i and j to the Jbutton that was just made
                btn[i][j] = b;
                gamePanel.add(btn[i][j]);//add this new button to the game panel
                btn[i][j].addMouseListener(new MouseAdapter() {//for each button add a mouse listener
                    //this method will be called when the mouse is clicked
                    
                    public void mouseClicked(MouseEvent evt) {
                        //if the user clicks the right mouse button
                        if (SwingUtilities.isRightMouseButton(evt)) {
                            
                            if (btn[x][y].getText().equals("")) {//if the button is blank
                                btn[x][y].setText("F");//set the text of the button to a capital F
                            } else {
                                btn[x][y].setText("");//set the button to blank
                                tiles[x][y].setNumBombs(0);//set the number of bombs to 0 to cancel out past floodfill add up
                            }
                        } else {//if the left button is clicked
                            floodFill(tiles, x, y);//run the floodfill algorith on the clicked tile
                            winGame(); //check to see if the user won the game
                        }
                    }
                });
            }
        }
    }

    /**
     * This method is used to create the tile objects in the game
     */
    public void createTiles() {
        Random rNum1 = new Random(), rNum2 = new Random();//create 2 random numbers

        tiles = new Tile[size][size]; //make the array of tiles the size of the field
        //nested loop to create the tiles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile(i, j);//create a new tile at i and j in the array
            }
        }
        //create a certain number of bombs depending on the size of the field
        for (int i = 0; i <= size * 3; i++) {
            int num1 = (rNum1.nextInt(size) - 0); //first random number
            int num2 = (rNum2.nextInt(size) - 0); //second random number
            tiles[num1][num2] = new Bomb(num1, num2, false); //turn a tile into a bomb at the x and y coordinates 2 random numbers 
        }
    }

    /**
     * This method checks to see if the user has won the game.
     */
    public void winGame() {
        int w = 0; //set variable w to 0
        //nested loop to check all of the buttons on the field
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //if the btn has already been clicked, is a bomb, or is a flag
                if (!btn[i][j].isEnabled() || tiles[i][j] instanceof Bomb || btn[i][j].getText().equals("F")) {
                    w++;//add 1 to w
                }
            }
        }
        //if the w number is every button on the field
        if (w == (size * size)) {
            currentPlayer.setWin(1);//increase the number of wins by 1
            writePlayer();//call writePlayer method
            new ScoreBoard().setVisible(true); //create a new scoreBoard and display it
            this.setVisible(false);//close the game panel
            System.out.println("win");//print win to console
        }
    }

    /**
     * The floodFill method is the method that controls the mass flipping of
     * tiles in minesweeper
     *
     * @param t = array of tiles to be checked
     * @param x = x coordinate of the tiles
     * @param y = y coordinate of the tiles
     */
    public void floodFill(Tile t[][], int x, int y) {
        Tile t1 = t[x][y]; //make a tile named t1 equal to the tile at the given x and y coordinate
        int b = 0;
        //if t1 is a bomb
        if (t1 instanceof Bomb) {
            currentPlayer.setLoss(1);//increases the number of losses by 1
            writePlayer();//calls writePlayer method
            new ScoreBoard().setVisible(true);//create a new scoreBoard and make it visible
            this.setVisible(false);//close the game panel
            System.out.println("lose");//print lose to the console
        }
        //if t1 is a bomb or the btn text is equal to 0
        if (t1 instanceof Bomb || btn[x][y].getText().equals("0")) {
            //do not go through recursion process
            
        } else {
            b = t1.getNumBombs(t, x, y, size);//set b equal to the number of bombs around the tile
            t1.flip(t1, btn[x][y], b); //flip the tile
            //if the tile has bombs around it
            
            if (t1.getNumBombs(t, x, y, size) != 0) {
                //do not recurse
                //if the tile is marked as a flag
            } else if (btn[x][y].getText().equals("F")) {
                //do not recurse
            } else {
                //if x is equal to 0
                
                
                if (x == 0) {
                    floodFill(t, x + 1, y);//floodfill the tile to the right 
                    floodFill(t, x, y);//floodfill the same tile
                    //if x is equal to size -1
                } else if (x == size - 1) {
                    floodFill(t, x, y);//floodfill the same tile
                    floodFill(t, x - 1, y);//floodfill the tile to the left
                    //if x is not on the border
                } else if (x < size && x > 0) {
                    floodFill(t, x + 1, y);//floodfill tile to the right
                    floodFill(t, x - 1, y);//floodfill tile to the left
                } else {
                    return;//end recursion
                }

                //if y is equal to 0
                if (y == 0) {
                    floodFill(t, x, y);//floodfill the same tile
                    floodFill(t, x, y + 1);//floodfill the tile above
                    //if y is equal to size - 1
                } else if (y == size - 1) {
                    floodFill(t, x, y - 1);//floodfill the tile below
                    floodFill(t, x, y);//floodfill the same tile
                    //if y is not on the border
                } else if (y < size && y > 0) {
                    floodFill(t, x, y - 1);//floodfill the tile below
                    floodFill(t, x, y + 1);//floodfill the tile above
                } else {
                    return;//end recursion
                }

            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exit = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();
        forfeit = new javax.swing.JButton();
        thing = new javax.swing.JLabel();
        faceLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exit.setBackground(new java.awt.Color(0, 255, 255));
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        gamePanel.setMaximumSize(new java.awt.Dimension(300, 250));

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        forfeit.setBackground(new java.awt.Color(0, 255, 255));
        forfeit.setText("Forfeit");
        forfeit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forfeitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(thing)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(forfeit)
                        .addGap(83, 83, 83)
                        .addComponent(faceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                        .addComponent(exit)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(faceLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(forfeit)
                        .addComponent(exit)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thing)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    /**
     * This method is called when the user pressed the forfeit button
     *
     * @param evt = button being pressed
     */
    private void forfeitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forfeitActionPerformed

        new MainMenu().setVisible(true);//create a main menu and make it visible
        setVisible(false);//close the game window
    }//GEN-LAST:event_forfeitActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exit;
    private javax.swing.JLabel faceLabel;
    private javax.swing.JButton forfeit;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel thing;
    // End of variables declaration//GEN-END:variables
}
