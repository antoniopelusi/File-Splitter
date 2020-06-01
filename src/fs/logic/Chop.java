package fs.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
/**
 * Contain the Chop logic
 * @author antoniopelusi
 *
 */
public class Chop
{
	
	/**
	 * ChopFun() function work with I/O, like
	 * FileInputStream and FileOutputStream, to split a file.
	 * @param fileToChop is the File to chop
	 * @param size is the max size of all the splitted file pieces
	 */
	public static void ChopFun(File fileToChop, int size)
	{
		File inputFile = fileToChop;
		FileInputStream inputStream;
		
		String newFileName;
		
		FileOutputStream filePart;
		
		int fileSize = (int) inputFile.length();
		int nChunks = 0;
		int read = 0;
		int readLength = size;
		
		byte[] byteChunkPart;
		
		try
		{
				inputStream = new FileInputStream(inputFile);
				while (fileSize > 0)
				{
					if (fileSize <= size)
					{
						readLength = fileSize;
					}
					
					byteChunkPart = new byte[readLength];
					read = inputStream.read(byteChunkPart, 0, readLength);
					fileSize -= read;
					assert (read == byteChunkPart.length);
					nChunks++;
					
					newFileName = fileToChop.getName() + ".part" + (new DecimalFormat("00").format(nChunks - 1));
					filePart = new FileOutputStream(new File(newFileName));
					filePart.write(byteChunkPart);
					filePart.flush();
					filePart.close();
					
					byteChunkPart = null;
					filePart = null;
				}
				
				inputStream.close();
			}
		
		catch (IOException exception)
		{
				exception.printStackTrace();
		}
	}
}
