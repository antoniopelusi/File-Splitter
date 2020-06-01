package fs.main;

import javax.swing.*;

import fs.gui.Frame;
/**
 * Main function
 * @author antoniopelusi
 *
 */
public class Main
{	
	/**
	 * Create a Frame object
	 */
	public static void main(String[] args)
	{			
		Frame f = new Frame();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}
}
