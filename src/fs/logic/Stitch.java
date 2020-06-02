package fs.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Contain the Stitch logic
 * @author antoniopelusi
 *
 */
public class Stitch
{
	
	/**
	 * StitchFun() function work with I/O, like
	 * FileInputStream and FileOutputStream, to merge a file.
	 * @param files is an array containing all the file that have to be stitched
	 * @param name is the name of the stitched file
	 * @return the stitched File
	 */
	public static File StitchFun(File[] files, String name)
	{
		File ofile = new File(name);
		
		FileOutputStream fos;
		FileInputStream fis;
		
		byte[] fileBytes;
		int bytesRead = 0;
		
		List<File> list = new ArrayList<File>();
		
		for(int i=0; i<files.length; i++)
		{
			list.add(files[i]);
		}
		
		try
		{
		    fos = new FileOutputStream(ofile, true);
		    
		    for (File file : list)
		    {
		        fis = new FileInputStream(file);
		        fileBytes = new byte[(int) file.length()];
		        bytesRead = fis.read(fileBytes, 0,(int)  file.length());
		        assert(bytesRead == fileBytes.length);
		        assert(bytesRead == (int) file.length());
		        
		        fos.write(fileBytes);
		        fos.flush();
		        
		        fileBytes = null;
		        fis.close();
		        fis = null;
		    }
		    
		    fos.close();
		    fos = null;
		    
		}
		
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		
		return ofile;
	}
}
