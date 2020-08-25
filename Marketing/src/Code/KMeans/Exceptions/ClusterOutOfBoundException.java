package Code.KMeans.Exceptions;
/**
 * @author Giovanni Castellano
 */
public class ClusterOutOfBoundException extends Exception{
    public ClusterOutOfBoundException()
    {
        super();
    }
    @Override
    public String toString()
    {
        return "Cluster request out of bound";
    }
}
