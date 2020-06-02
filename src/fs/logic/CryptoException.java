package fs.logic;
/**
 * Avoid messy throws in Crypt function
 * @author antoniopelusi
 *
 */
public class CryptoException extends Exception
{
	
	/**
	 * print the CryptoException
	 * @param message is a String containing a encrypt/decrypt error message
	 * @param throwable contain the Throwable IOException
	 */
    public CryptoException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}