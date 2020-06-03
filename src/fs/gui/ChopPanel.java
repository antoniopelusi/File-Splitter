package fs.gui;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Contain the graphic elements of ChopPanel
 * @author antoniopelusi
 *
 */
public class ChopPanel extends JPanel implements ItemListener, ActionListener
{
	private JButton b1, b2;
	private static JTextField t1, t2, t4;
	private static JCheckBox c1;
	private static JRadioButton R1, R2, R3;
	private ButtonGroup grp2;
	private JLabel l1;
	static JComboBox<String> C1;
	private JFileChooser fc;
	private JScrollPane s1;
	
	String[] combobox = {"B", "KB", "MB", "GB"};
	
	private int returnVal;
	
	private String output;
	private static boolean getSplitTypeVal;
	private static String getCryptVal;
	private static boolean getCompressVal;
	private static int getSplitValVal;
	
	private static Object row[];
	
	static File file[];
	
	/**
	 * the ChopPanel constructor 
	 * provides all the graphic items
	 * to obtain the actions that
	 * have to be performed
	 * to the selected file.
	 */
	public ChopPanel()
	{
		super();
		
		setLayout(null);
		setBounds(5, 5, 340, 260);
		
		TitledBorder titleBorder = new TitledBorder(new LineBorder(new Color(128, 128, 128)));
		titleBorder.setTitle("Chop");
	    titleBorder.setTitleJustification(TitledBorder.CENTER);
	    setBorder(titleBorder);
		
	    //componenti
		b1 = new JButton("Select file");	
		b1.setBounds(15, 20, 110, 40);
		b1.addActionListener(this);
		
		t1 = new JTextField();
		t1.setEditable(false);
		
		s1 = new JScrollPane(t1);
		s1.setBounds(140, 20, 190, 40);
		s1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		c1 = new JCheckBox("Split");
		c1.setBounds(13, 80, 60, 25);
		c1.addItemListener(this);
		
		l1 = new JLabel("Part size");
		l1.setBounds(140, 80, 65, 25);
		
		t2 = new JTextField();
		t2.setBounds(210, 80, 60, 25);
		t2.setEditable(false);
		
		C1 = new JComboBox<String>(combobox);
		C1.setBounds(275, 80, 50, 25);
		C1.setSelectedIndex(2);
		
		R3 = new JRadioButton("Nothing");
		R3.setBounds(13, 120, 100, 25);
		R3.setSelected(true);
		
		R1 = new JRadioButton("Compress");
		R1.setBounds(13, 150, 100, 25);
		
		R2 = new JRadioButton("Encrypt");
		R2.setBounds(13, 180, 80, 25);
		R2.addItemListener(this);
		
		grp2 = new ButtonGroup();
		grp2.add(R1);
		grp2.add(R2);
		grp2.add(R3);
		
		t4 = new JTextField();
		t4.setBounds(140, 180, 125, 25);
		t4.setHorizontalAlignment(JTextField.CENTER);
		t4.setEditable(false);
		
		b2 = new JButton("Create job");
		b2.setBounds(220, 220, 110, 25);
		b2.addActionListener(this);
		
		add(b1);
		add(s1);
		add(c1);
		add(l1);
		add(t2);
		add(C1);
		add(R1);
		add(R2);
		add(R3);
		add(t4);
		add(b2);		
	}
	
	/**
	 * @return the boolean value referred to the split type
	 */
	public static boolean getSplitType() //3
	{
		if(c1.isSelected())
			getSplitTypeVal = true;
		else
			getSplitTypeVal = false;
		
		return getSplitTypeVal;
	}
	
	/**
	 * @return the size in which the files will be splitted
	 */
	public static int getSplitVal() //4
	{
		if(c1.isSelected())
		{
			if(!t2.getText().equals(""))
			{
				getSplitValVal = Integer.parseInt(t2.getText());
			}
		}
		return getSplitValVal;
	}
	
	/**
	 * @return the boolean value for the file compression
	 */
	public static boolean getCompress() //5
	{
		if(R1.isSelected())
		{
			getCompressVal = true;
		}
		else
		{
			getCompressVal = false;
		}
		
		return getCompressVal;
	}
	/**
	 * @return the key used to the Crypt algorithm for the file encryption
	 */
	public static String getCrypt() //6
	{
		if(R2.isSelected() == true && !t4.getText().equals(""))
		{
			getCryptVal = t4.getText();
		}
		else
		{
			getCryptVal = "";
		}
		
		return getCryptVal;
	}
	/**
	 * listener containing the ChopPanel GUI logic
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getItemSelectable() == c1) //split graphic logic
		{
			if(c1.isSelected() == true)
			{
				t2.setEditable(true);
			}
			
			else
			{
				t2.setEditable(false);
				
				t2.setText(null);
			}
		}
		
		if(e.getItemSelectable() == R2) //encrypt graphic logic
		{
			if(R2.isSelected())
			{
				t4.setEditable(true);
				t4.setText(null);
			}
			
			else
			{
				t4.setEditable(false);
				t4.setText(null);
			}
		}
	}

	/**
	 * listener that manage the file selection using the JFileChooser
	 * and provide the row creation.
	 * add the created row to the job list in the JobPanel's JTable.
	 * The selected files have to be added one by one in the job list.
	 * This file will be encrypted/compressed and splitted.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Select file") //select file
		{
			fc = new JFileChooser();
			fc.setMultiSelectionEnabled(true);
			
			returnVal = fc.showOpenDialog(null);
			
			//evito che i file selezionati vengano caricati quando viene cliccato cancel
	        if (returnVal == JFileChooser.APPROVE_OPTION)
	        {
	        	int n = fc.getSelectedFiles().length;
	        	file = fc.getSelectedFiles();
	        	
				StringBuilder sb = new StringBuilder();
				
				for(int i=0; i<n; i++)
				{
					sb.append(file[i].getName() + " (" + Long.toString(file[i].length()) + "B)");
					if(i<n-1)
					{
						sb.append(", ");						
					}
				}
				
				output = sb.toString();
	        	t1.setText(output);
	        }
	        
	        Frame.pb.setValue(0);
		}
		
		if(e.getActionCommand() == "Create job")
		{
			if(!t1.getText().equals(""))
			{
				if(!t2.getText().equals(""))
				{
			
					row = new Object[6];
				
					row[0] = "Chop";
					
					row[1] = null;
					
					if(getSplitType())
						row[2] = "split by size";
					else
						row[2] = "split by part";
					
					row[3] = getSplitVal();
					
					if(getCompress())
						row[4] = "yes";
					else
						row[4] = "";
					
						row[5] = getCrypt();					
				
					JobPanel.AddRow(row, file);
								
					t1.setText(null);
					c1.setSelected(false);
					grp2.clearSelection();
				}
			}		
		}
	}
}