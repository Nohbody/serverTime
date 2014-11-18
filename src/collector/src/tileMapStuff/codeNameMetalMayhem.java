package collector.src.tileMapStuff;

import main.DBOps;

import javax.swing.*;
import java.io.FileNotFoundException;


public class codeNameMetalMayhem extends JFrame
{

    /**
     * @param args
     */

    public static void main(String[] args) throws FileNotFoundException
    {
        DBOps.connect();
    Game zombiePanel = new Game();
        //ZombiePanel zombiePanel = new ZombiePanel();
        JFrame codeNameMetalMayhem = new JFrame ("The Mayhem is Metal...ah!");
        codeNameMetalMayhem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        codeNameMetalMayhem.getContentPane().add(zombiePanel);
        codeNameMetalMayhem.pack();
        codeNameMetalMayhem.setVisible(true);

    }

}
