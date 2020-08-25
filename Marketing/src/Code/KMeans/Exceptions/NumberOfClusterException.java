package Code.KMeans.Exceptions;
/**
 * @author Giovanni Castellano
 */
public class NumberOfClusterException extends Exception{
    public NumberOfClusterException()
    {
        super();
    }
    @Override
    public String toString()
    {
        return "Negative number in input as number of clusters";
    }
}
