package fs.logic;

import java.io.*;
import java.util.zip.*;
/**
 * Contain the zip/unzip logic
 * @author antoniopelusi
 *
 */
public class Compress
{
	
	/**
	 * This is the Zip logic function, needed to compress a file.
	 * @param f is the file that have to be compressed
	 * @param name is the name of the output compressed file
	 * @return the compressed file
	 * @throws IOException
	 */
	public static File Zip(File f, String name) throws IOException
	{
		String sourceFile = f.getAbsolutePath();
		
        FileOutputStream fos = new FileOutputStream(name);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        
        File fileToZip = new File(sourceFile);
        
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        
        zipOut.putNextEntry(zipEntry);
        
        byte[] bytes = new byte[1024];
        int length;
        
        while((length = fis.read(bytes)) >= 0)
        {
            zipOut.write(bytes, 0, length);
        }
        
        zipOut.close();
        fis.close();
        fos.close();   
                
        File zip = new File(System.getProperty("user.dir") + File.separator + name);
        
	    if (!zip.exists())
	    {
	        throw new FileNotFoundException("The created zip file could not be found");
	    }
	    
	    return zip;
	}
	
	/**
	 * This is the unzip logic function, needed to decompress a file.
	 * @param f is the file that have to be decompressed
	 * @param name is the name of the output decompressed file
	 * @throws IOException
	 */
	public static void UnZip(File f, String name) throws IOException
	{
		String fileZip = System.getProperty("user.dir") + File.separator + name;
	    File destDir = new File(System.getProperty("user.dir"));
	    
	    byte[] buffer = new byte[1024];
	    
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        
        while (zipEntry != null)
        {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;

            while ((len = zis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, len);
            }
           
            zipEntry = zis.getNextEntry();
            fos.close();

        }

        zis.closeEntry();
        zis.close();
    }
     
	/**
	 * Called by unzip() function, is used to
	 * create a new File (in this case the decompressed one)
	 * @param destinationDir contain the directory of the output file
	 * @param zipEntry is one of the function used to unzip a file
	 * @return return the output file
	 * @throws IOException
	 */
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException
    {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator))
        {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
	}
}