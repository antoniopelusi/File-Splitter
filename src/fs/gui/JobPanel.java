package fs.gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.util.*;
/**
 * Contain the job list implemented with a JTable
 * @author antoniopelusi
 *
 */
public class JobPanel extends JPanel //implements ActionListener
{
	private Object header[] = {"Type", "File", "Split type", "Split val", "Compress", "Crypt"};
	static DefaultTableModel tModel;
	static JTable t;
	
	static ArrayList<File> files = new ArrayList<File>();
	static ArrayList<File[]> files2 = new ArrayList<File[]>();
	
	/**
	 * the JobPanel constructor 
	 * provides the creation of the JTable
	 * used as graphic job list.
	 * The job list management
	 * is assigned to the two functions:
	 * addRow() that add Chop type jobs;
	 * addRow2() that add Stitch type jobs.
	 */
	public JobPanel()
	{
		
		setLayout(new BorderLayout());
		setBounds(5, 265, 690, 344);
		
		TitledBorder titleBorder = new TitledBorder(new LineBorder(new Color(128, 128, 128)));
		titleBorder.setTitle("Jobs list");
		titleBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(titleBorder);
		
		tModel = new DefaultTableModel()
		  {
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;
		    }
		  };
		  
		t = new JTable(tModel);
		t.setBackground(null);
        t.setRowHeight(20);
                
		tModel.setColumnIdentifiers(header);
        
        t.getTableHeader().setReorderingAllowed(false);
        t.setRowSelectionAllowed(false);
		add(new JScrollPane(t));
	}
	/**
	 * Called by ChopPanel,
	 * is used to add Chop jobs to the list.
	 * @param row is the Object array containing all the rows data
	 * @param file is the file array containing all the file that have to be added one by one
	 */
	public static void AddRow(Object row[], File file[])
	{
		for(int i=0; i<file.length; i++)
		{
			row[1] = ChopPanel.file[i].getName();
			
			tModel.addRow(row);
			
			files.add(file[i]);
			
			Frame.printTest();
		}
	}
	/**
	 * Called by StitchPanel, is used to add Stitch jobs to the list.
	 * @param row is the Object array containing all the rows data
	 * @param file2 is the file array containing all the filw that have to be added all togheter
	 */
	public static void AddRow2(Object row[], File file2[])
	{
		row[1] = StitchPanel.output;
		
		tModel.addRow(row);
					
		files2.add(files2.size(), file2);

		Frame.printTest();
	}
}
