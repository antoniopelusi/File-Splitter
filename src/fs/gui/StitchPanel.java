package fs.gui;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Contain the graphic elements of StitchPanel
 * @author antoniopelusi
 *
 */
public class StitchPanel extends JPanel implements ActionListener, ItemListener
{
	private JButton b1, b2;
	private JTextField t1, t2;
	private JCheckBox c1;
	private JFileChooser fc;
	private JScrollPane s1;
	
	private int returnVal;
	static String output, getDecryptVal;
	
	private static File file2[];
	private static Object row2[];
	
	/**
	 * the StitchPanel constructor 
	 * provides all the graphic items
	 * to obtain the actions that
	 * have to be performed
	 * to the selected file.
	 */
	public StitchPanel()
	{
		super();
		
		setLayout(null);
		setBounds(345, 5, 350, 260);
		
		TitledBorder titleBorder = new TitledBorder(new LineBorder(new Color(128, 128, 128)));
		titleBorder.setTitle("Stitch");
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

		c1 = new JCheckBox("Decrypt");
		c1.setBounds(13, 80, 90, 25);
		c1.addItemListener(this);
		
		t2 = new JTextField();
		t2.setBounds(105, 80, 135, 25);
		t2.setHorizontalAlignment(JTextField.CENTER);
		t2.setEditable(false);
		
		b2 = new JButton("Create job");
		b2.setBounds(225, 220, 110, 25);
		b2.addActionListener(this);
		
		add(b1);
		add(s1);
		add(c1);
		add(t2);
		add(b2);
	}
	
	/**
	 * @return the key used to the Crypt algorithm for the file decryption
	 */
	public String getDecrypt()
	{
		if(c1.isSelected())
		{
			getDecryptVal = t2.getText();
		}
		return getDecryptVal;
	}
	
	/**
	 * Listener containing the StitchPanel GUI logic
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getItemSelectable() == c1) //split
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
	}
	
	/**
	 * listener that manage the file selection using the JFileChooser
	 * and provide the row creation.
	 * add the created row to the job list in the JobPanel's JTable.
	 * The selected files have to be added all togheter in the job list.
	 * This files will be merged and decompressed/decrypted.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Select file") //select file
		{
			fc = new JFileChooser();
			fc.setMultiSelectionEnabled(true);
			Action details = fc.getActionMap().get("viewTypeDetails");
			details.actionPerformed(null);
						
			returnVal = fc.showOpenDialog(null);
			
			//evito che i file selezionati vengano caricati quando viene cliccato cancel
	        if (returnVal == JFileChooser.APPROVE_OPTION)
	        {
	        	int n = fc.getSelectedFiles().length;
	        	file2 = fc.getSelectedFiles();
	        	
	        	StringBuilder sb = new StringBuilder();
				
				for(int i=0; i<n; i++)
				{
					sb.append(file2[i].getName());
					if(i!=n-1)
					{
						sb.append(", ");						
					}
				}
				
				output = sb.toString();
	        	t1.setText(output);
	        }
	        else if (returnVal == JFileChooser.CANCEL_OPTION)
	        {
	        	t1.setText(null);
	        }
	        
	        Frame.pb.setValue(0);
		}
		
		if(e.getActionCommand() == "Create job")
		{
			if(!t1.getText().equals(""))
			{
				row2 = new Object[6];
			
				row2[0] = "Stitch";
				row2[1] = t1.getText();
				row2[5] = getDecrypt();
				
				getDecryptVal = null;
				
				JobPanel.AddRow2(row2, file2);

				t1.setText(null);
				c1.setSelected(false);		
			}
		}
	}
}