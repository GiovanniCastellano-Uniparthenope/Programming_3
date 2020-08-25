package Code;
/**
 * @author Giovanni Castellano
 */
public interface User {
    public String getUserLoginName();
    public long getUserHash();
    @Override
    public String toString();
}
