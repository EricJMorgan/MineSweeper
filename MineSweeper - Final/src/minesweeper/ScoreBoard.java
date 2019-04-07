/**
 * JFrame that displays the scores of past players
 */
package minesweeper;

import java.io.BufferedReader;//Import buffered reader
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader; //import file reader
import java.io.IOException; //import io exception
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList; //import arraylist
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreBoard extends javax.swing.JFrame {

    ArrayList<Player> players = new ArrayList();

    /**
     * Creates new form ScoreBoard
     */
    public ScoreBoard() {
        initComponents();//build the JFrame
        readFile();//reads file
        sort();//sorts players
        scoreArea.setText(players.toString());//prints out sorted list of players
    }

    /**
     * this method reads in a file of players
     */
    public void readFile() {
        
        boolean endOfFile;
        String name;
        double win, loss;
        
        try {
            //try to create connection to file
            FileReader fr = new FileReader("src\\players.txt"); //file must be located in project src folder
            BufferedReader br = new BufferedReader(fr);
            String not = br.readLine();
            endOfFile = false;
            
            while (!endOfFile) { //loop until the end of file is reached
                name = br.readLine(); //read in first record
                
                if (name == null) { //check if read operation got no data
                    endOfFile = true;
                } else {//otherwise process rest of record

                    win = Double.parseDouble(br.readLine());
                    loss = Double.parseDouble(br.readLine());
                    not = br.readLine();

                    players.add(new Player(name, win, loss));

                }//end of if
            }//end of the while

            br.close(); //close the stream to the file when done
        } catch (IOException e) { //catch the exception caused if the file is not found
            System.out.println("Error: " + e.toString());
            
        }

    }

    /**
     * this method sets high and low and calls quik sort
     */
    public void sort() {
        int low = 0;//sets low
        int high = players.size() - 1;//sets high to array size -1

        quik(low, high);//calls quik sort method

    }

    /**
     * this method sorts the list of players based on the players score
     *
     * @param low
     * @param high
     */
    public void quik(int low, int high) {

        if (players == null || players.isEmpty()) {//checks if nothing is in array
            return;
        }

        if (low >= high) {//checks if low is greater than high
            return;
        }

        // pick the pivot
        int middle = low + (high - low) / 2;
        double pivot = players.get(middle).getScorePercent();

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            
            while (players.get(i).getScorePercent() > pivot) {
                i++;
            }

            while (players.get(j).getScorePercent() < pivot) {
                j--;
            }

            if (i <= j) {
                Player temp = players.get(i);
                players.set(i, players.get(j));
                players.set(j, temp);
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j) {
            quik(low, j);
        }

        if (high > i) {
            quik(i, high);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        scoreArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setFont(new java.awt.Font("Lilliput Steps", 3, 24)); // NOI18N
        title.setForeground(new java.awt.Color(0, 153, 153));
        title.setText("ScoreBoard");
        title.setAutoscrolls(true);
        title.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 255, 102), new java.awt.Color(255, 51, 51), new java.awt.Color(51, 153, 255), new java.awt.Color(0, 255, 51)));

        back.setBackground(new java.awt.Color(0, 255, 255));
        back.setText("Main Menu");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        exit.setBackground(new java.awt.Color(0, 255, 255));
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        scoreArea.setEditable(false);
        scoreArea.setColumns(20);
        scoreArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        scoreArea.setRows(5);
        jScrollPane1.setViewportView(scoreArea);

        jLabel1.setText("Wins");

        jLabel2.setText("Losses");

        jLabel3.setText("W/L Ratio");

        jLabel4.setText("Name");

        jLabel5.setText("Wins");

        jLabel6.setText("Losses");

        jLabel7.setText("W/L Ratio");

        jLabel8.setText("Name");

        jLabel9.setText("----------------------");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(title)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(back)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)))
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(title)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(back)
                            .addComponent(exit)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        //opens main menu
        new MainMenu().setVisible(true);
        //closes field choice
        setVisible(false);
    }//GEN-LAST:event_backActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        //closes program
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ScoreBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScoreBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScoreBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScoreBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ScoreBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea scoreArea;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
