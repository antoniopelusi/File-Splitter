package fs.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.File;
import java.security.SecureRandom;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Contain the graphic elements of ChopPanel
 * @author antoniopelusi
 *
 */
public class ChopPanel extends JPanel implements ItemListener, ActionListener
{
	private JButton b1, b2, b3;
	private static JTextField t1, t2, t3;
	private static JRadioButton R1, R2, R3;
	private ButtonGroup grp2;
	private JLabel l1, l2;
	static JComboBox<String> C1;
	private JFileChooser fc;
	private JScrollPane s1;
	
	String[] combobox = {"B", "KB", "MB", "GB"};
	
	private int returnVal;
	
	private static String getCryptVal;
	private static boolean getCompressVal;
	private static int getSplitValVal;
	
	private static Object row[];
	
	static File file[];
	
	static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
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
		
		l1 = new JLabel("Part size");
		l1.setBounds(15, 80, 65, 25);

		t2 = new JTextField();
		t2.setBounds(95, 80, 60, 25);
		t2.setHorizontalAlignment(JTextField.CENTER);
		t2.setEditable(false);
		
		C1 = new JComboBox<String>(combobox);
		C1.setBounds(160, 80, 50, 25);
		C1.setSelectedIndex(2);
		
		R3 = new JRadioButton("Nothing");
		R3.setBounds(13, 115, 100, 25);
		R3.setSelected(true);
		
		R1 = new JRadioButton("Compress");
		R1.setBounds(13, 145, 100, 25);
		
		R2 = new JRadioButton("Encrypt");
		R2.setBounds(13, 175, 80, 25);
		R2.addItemListener(this);
		
		grp2 = new ButtonGroup();
		grp2.add(R1);
		grp2.add(R2);
		grp2.add(R3);
		
		t3 = new JTextField();
		t3.setBounds(95, 175, 135, 25);
		t3.setHorizontalAlignment(JTextField.CENTER);
		t3.setEditable(false);
		
		b3 = new JButton("Random");
		b3.setBounds(235, 175, 95, 25);
		b3.addActionListener(this);
        b3.setEnabled(false);
		
		b2 = new JButton("Create job");
		b2.setBounds(220, 220, 110, 25);
		b2.addActionListener(this);
		
		l2 = new JLabel();
		l2.setBounds(105, 195, 120, 25);
		l2.setFont(new Font(null, Font.ITALIC, 11));
		
		add(b1);
		add(s1);
		add(l1);
		add(t2);
		add(C1);
		add(R1);
		add(R2);
		add(R3);
		add(t3);
		add(b2);
		add(b3);
		add(l2);
	}
	
	/**
	 * @return the size in which the files will be splitted
	 */
	public static int getSplitVal() //4
	{
		if(!t2.getText().equals(""))
		{
			getSplitValVal = Integer.parseInt(t2.getText());
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
		if(R2.isSelected() == true && !t3.getText().equals(""))
		{
			getCryptVal = t3.getText();
		}
		else
		{
			getCryptVal = "";
		}
		
		return getCryptVal;
	}
	
	/**
	 * 
	 * @param size
	 * @return the size in the right unit of measurement
	 */
	public static String formatFileSize(long size) {
	    String hrSize = null;

	    double b = size;
	    double k = size/1024.0;
	    double m = ((size/1024.0)/1024.0);
	    double g = (((size/1024.0)/1024.0)/1024.0);
	    double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

	    DecimalFormat dec = new DecimalFormat("0.00");

	    if ( t>1 ) {
	        hrSize = dec.format(t).concat(" TB");
	    } else if ( g>1 ) {
	        hrSize = dec.format(g).concat(" GB");
	    } else if ( m>1 ) {
	        hrSize = dec.format(m).concat(" MB");
	    } else if ( k>1 ) {
	        hrSize = dec.format(k).concat(" KB");
	    } else {
	        hrSize = dec.format(b).concat(" Bytes");
	    }

	    return hrSize;
	}

	/**
	 * 
	 * @param len
	 * @return the 128bit random-generated password for AES
	 */
	String randomString(int len)
	{
		StringBuilder sb = new StringBuilder(len);
		
		for(int i=0; i<len; i++) 
			sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
		
		return sb.toString();
}

	/**
	 * listener containing the ChopPanel GUI logic
	 */
	public void itemStateChanged(ItemEvent e)
	{	
		if(e.getItemSelectable() == R2) //encrypt graphic logic
		{
			if(R2.isSelected())
			{
				t3.setEditable(true);
				t3.setText(null);
				b3.setEnabled(true);
			}
			
			else
			{
				t3.setEditable(false);
				t3.setText(null);
				b3.setEnabled(false);
				l2.setText(null);
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
			Action details = fc.getActionMap().get("viewTypeDetails");
			details.actionPerformed(null);
			
			returnVal = fc.showOpenDialog(null);
			
			//evito che i file selezionati vengano caricati quando viene cliccato cancel
	        if (returnVal == JFileChooser.APPROVE_OPTION)
	        {
	        	int n = fc.getSelectedFiles().length;
	        	file = fc.getSelectedFiles();
	        	
				StringBuilder sb = new StringBuilder();
				
				for(int i=0; i<n; i++)
				{
					sb.append(file[i].getName() + " (" + formatFileSize(file[i].length()) + ")");
					if(i!=n-1) //last file don't need "," at the end
					{
						sb.append(", ");						
					}
				}
				
	        	t1.setText(sb.toString());
	        	t2.setEditable(true);
	        }
	        else if (returnVal == JFileChooser.CANCEL_OPTION)
	        {
	        	t1.setText(null);
	        }
	        
	        Frame.pb.setValue(0);
		}
		
		if(e.getActionCommand() == "Random")
		{
			t3.setEditable(true);
			t3.setText(randomString(16));
			
			StringSelection stringSelection = new StringSelection(t3.getText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			l2.setText("copied to clipboard");
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
					
					row[2] = "split by size";

					
					row[3] = getSplitVal();
					
					if(getCompress())
						row[4] = "yes";
					else
						row[4] = "";
					
						row[5] = getCrypt();					
				
					JobPanel.AddRow(row, file);
								
					t1.setText(null);
					t2.setText(null);
					t2.setEditable(false);
					R3.setSelected(true);
					l2.setText(null);
				}
			}		
		}
	}
}