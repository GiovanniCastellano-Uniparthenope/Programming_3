package Code.KMeans.Exceptions;
/**
 * @author Giovanni Castellano
 */
public class MediatorException extends Exception{
    public MediatorException()
    {
        super();
    }
    @Override
    public String toString()
    {
        return "The K-Means algorithm cannot work with these input values";
    }
}
