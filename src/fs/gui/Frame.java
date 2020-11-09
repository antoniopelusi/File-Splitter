package fs.gui;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import fs.logic.Chop;
import fs.logic.Compress;
import fs.logic.Crypt;
import fs.logic.CryptoException;
import fs.logic.Stitch;
/**
 * Frame class that contain the graphic elements and the basic logic of the File Splitter
 * @author antoniopelusi
 *
 */
public class Frame extends JFrame implements ActionListener
{	
	private static final long serialVersionUID = -7915256629979842614L;
	
	private JButton b1, b2, b3;
	static JProgressBar pb;
		
	private File zippedFile, StitchedFile, encryptedFile;
	
	int sizeCalculated;

	/**
	 * The Frame class extend JFrame and contain 3 JPanel:
	 * 1) ChopPanel that contain the Chop interface
	 * 2) StitchPanel that contain the Stitch interface
	 * 3) JobPanel that contain the dynamic Job list.
	 */
	public Frame()
	{
		super();
		
		setTitle("File Splitter");
		super.setSize(715, 690);
		setLocationRelativeTo(null);
		setResizable(false);
			
		setLayout(null);
		
		ChopPanel p1 = new ChopPanel();
		StitchPanel p2 = new StitchPanel();
		JobPanel p3 = new JobPanel();
		
		b1 = new JButton("Start jobs");
		b1.setBounds(225, 615, 110, 25);
		b1.addActionListener(this);
		
		b2 = new JButton("Clear list");
		b2.setBounds(360, 615, 110, 25);
		b2.addActionListener(this);
		
		b3 = new JButton("test");
		b3.setBounds(10,  615,  80,  25);
		b3.addActionListener(this);
		
		pb = new JProgressBar();
        pb.setValue(0);
        pb.setStringPainted(true);
        pb.setBounds(570, 615, 120, 25);
        
		add(p1);
		add(p2);
		add(p3);
		add(b1);
		add(b2);
		//add(b3); //test button, print in console the arrays
		add(pb);
		
		setVisible(true);
	}

	/**
	 * the printTest function print
	 * all the files stored in both ArrayList
	 * used in the File Splitter:
	 * 1) Files that contain the Chop jobs
	 * 2) Files2[] that contain the Stitch jobs
	 * both are dynamic ArrayList stored in JobPanel.
	 */
	public static void printTest()
	{
		System.out.println("---Chop array---");
		for(int i=0; i<JobPanel.files.size(); i++)
		{
			System.out.println(i+1 + ") [files] " + JobPanel.files.get(i).getName());
		}
			
		System.out.println("[files size] " + JobPanel.files.size());
		System.out.println();
		
		System.out.println("---Stitch array---");
		for(int i=0; i<JobPanel.files2.size(); i++)
		{
			for(int j=0; j<JobPanel.files2.get(i).length; j++)
			{
				System.out.println((i+1) + " " + (j+1) + ") [files2] " + JobPanel.files2.get(i)[j].getName());
			}
		}
		System.out.println("[files2 size] " + JobPanel.files2.size());
		System.out.println();


	}
	
	/**
	 * Clear the 2 ArrayList and
	 * delete the rows of the job list
	 */
	public void clearJobList()
	{
		JobPanel.tModel.setRowCount(0);
		
		JobPanel.files.clear();
		
		JobPanel.files2.clear();
	}
	
	//--------------------------------------------------------------- logic function -----------------------------------------------------------------------
	
	/*	 	 <( LOGIC )>
	 *
	 *	---------Chop---------
	 *	    Crypt --> Chop
	 *		     or
	 *	     zip --> Chop
	 *	----------------------
	 *
	 *	--------Stitch--------
	 *	  Stitch --> decrypt
	 *			 or
	 *	   Stitch --> unzip
	 *	----------------------
	 */
	
	
	
	/**
	 * function that convert byte to selected unit of measure
	 */
	public void checkSize(int i)
	{
		if(ChopPanel.C1.getSelectedIndex() == 0) //B
		{
			sizeCalculated = (int)JobPanel.t.getValueAt(i, 3);
		}
		
		else if(ChopPanel.C1.getSelectedIndex() == 1) //KB
		{
			sizeCalculated = (int)JobPanel.t.getValueAt(i, 3)*1024;
		}
		
		else if(ChopPanel.C1.getSelectedIndex() == 2) //MB
		{
			sizeCalculated = (int)JobPanel.t.getValueAt(i, 3)*1024*1024;
		}
		
		else if(ChopPanel.C1.getSelectedIndex() == 3) //GB
		{
			sizeCalculated = (int)JobPanel.t.getValueAt(i, 3)*1024*1024*1024;
		}
	}
	
	/**
	 * One listener for all the object int the Frame,
	 * contain the Chop logic and the Stitch logic.
	 * This function calls sequentially all the function
	 * needed to chop, zip, crypt, stitch, unzip and decrypt.
	 * Read the right ArrayList and the relative row in the job list
	 * to get the necessary information.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Start jobs")
		{
			int nChop = 0;
			int nStitch = 0;
			
			if (JobPanel.t.getRowCount() != 0)
			{
				pb.setMinimum(0);
				pb.setMaximum(JobPanel.t.getRowCount());
				
				for(int i=0; i<JobPanel.t.getRowCount(); i++)
				{	
					if(JobPanel.t.getValueAt(i, 0) == "Chop") //Chop logic ---------------------------------------------------------------------
					{
						if(JobPanel.t.getValueAt(i, 5) != "") //if crypt
						{							
							String key = (String) JobPanel.t.getValueAt(i, 5);
					        File inputFile = JobPanel.files.get(nChop);
							encryptedFile = new File("c." + (String)JobPanel.t.getValueAt(i, 1));

							try
							{
								System.out.println("---encrypt started---");
								Crypt.encrypt(key, inputFile, encryptedFile);
								System.out.println("---encrypt ended---");
							}
							
							catch(CryptoException ex)
							{
								System.out.println(ex.getMessage());
								ex.printStackTrace();
							}
							
							System.out.println("---chop started---");
							
							checkSize(i);
							Chop.ChopFun(encryptedFile, sizeCalculated);
							
							System.out.println("---chop ended---");
							
							encryptedFile.delete();
						}
						else if(JobPanel.t.getValueAt(i, 4) == "yes") //if compress
						{							
							try
							{
								System.out.println("---compress started---");
								zippedFile = Compress.Zip(JobPanel.files.get(nChop), "z." + JobPanel.files.get(nChop).getName() + ".zip");
								System.out.println("---compress ended---");
							}
							catch (IOException e1)
							{
								e1.printStackTrace();
							}
							
							System.out.println("---Chop started---");
							
							checkSize(i);
							Chop.ChopFun(zippedFile, sizeCalculated);
							
							System.out.println("---Chop ended---");
							
							zippedFile.delete();
						}
						else
						{
							System.out.println("---Chop started---");

							checkSize(i);
							Chop.ChopFun(JobPanel.files.get(nChop), sizeCalculated);
							
							System.out.println("---Chop ended---");
						}
						
						nChop++;
					}
					pb.setValue(pb.getValue() + 1);

					if(JobPanel.t.getValueAt(i, 0) == "Stitch") //Stitch logic ------------------------------------------------------
					{
						if(JobPanel.t.getValueAt(i, 5) != null) //if crypted
						{
							System.out.println("---Stitch started---");
							String str = JobPanel.files2.get(nStitch)[0].getName();
							System.out.println(str);
							StitchedFile = Stitch.StitchFun(JobPanel.files2.get(nStitch), str.substring(0, str.length() - 7));
							
							System.out.println("---Stitch ended---");
							
							String key = (String) JobPanel.t.getValueAt(i, 5);
							File encryptedFile = StitchedFile;
							File decryptedFile = new File(str.substring(2, str.length() - 7));
							
							try
							{
								System.out.println("---decrypt started---");
								Crypt.decrypt(key, encryptedFile, decryptedFile);
								System.out.println("---decrypt ended---");

							}
							catch(CryptoException ex)
							{
								System.out.println(ex.getMessage());
								ex.printStackTrace();
							}
							
							StitchedFile.delete();
						}
						else if(JobPanel.files2.get(nStitch)[0].getName().charAt(0) == 'z' && JobPanel.files2.get(nStitch)[0].getName().charAt(1) == '.') //if compressed
						{
							System.out.println("---Stitch started---");
							String str = JobPanel.files2.get(nStitch)[0].getName();
							StitchedFile = Stitch.StitchFun(JobPanel.files2.get(nStitch), str.substring(0, str.length() - 7));
							System.out.println("---Stitch ended---");
							
							System.out.println("---decompress started---");
							try
							{
								Compress.UnZip(StitchedFile, str.substring(0, str.length() - 7));
							}
							catch (IOException e1)
							{
								e1.printStackTrace();
							}
							System.out.println("---decompress ended---");
							
							StitchedFile.delete();
						}
						
						else
						{
							System.out.println("---Stitch started---");
							String s = JobPanel.files2.get(nStitch)[0].getName();
							StitchedFile = Stitch.StitchFun(JobPanel.files2.get(nStitch), s.substring(0, s.length() - 7));
							System.out.println("---Stitch ended---");
						}
						
						nStitch++;
					}
					
					pb.setValue(pb.getValue() + 1);
				}
				
				// remove rows from the model and the file from the arrays (files and files2)
				clearJobList();
			}
		}
		
		if(e.getActionCommand() == "Clear list")
		{
			clearJobList();
	        Frame.pb.setValue(0);
		}
		printTest();
	}
}