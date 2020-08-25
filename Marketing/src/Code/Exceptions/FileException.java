package Code.Exceptions;
/**
 * @author Giovanni Castellano
 */
public class FileException extends Exception{
    public FileException()
    {
        super();
    }
    public String toString()
    {
        return "Error occured while managing files";
    }
}
