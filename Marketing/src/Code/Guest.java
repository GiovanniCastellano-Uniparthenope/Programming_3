package Code;

import java.util.ArrayList;

/**
 * @author Giovanni
 */
public class Guest implements User{
    private String login;
    private long hash;
    
    /**
    * Costruttore unico che prende in input un login ed una password
    * e li assegna a this utente
    * 
    * L'utente creato sarà di tipo Guest, con conseguenti privilegi
    * 
    * @param  login  L'username dell'utente usato per accedere
    * @param  hash   L'hash della password usata per accedere
    */
    public Guest(String login, long hash)
    {
        this.login = new String(login);
        this.hash=hash;
    }
    
    /**
    * Il metodo è un Override perchè proviene dall'interfaccia User
    * @return Ritorna l'username
    */
    @Override
    public String getUserLoginName()
    {
        return this.login;
    }
    
    /**
    * Il metodo è un Override perchè proviene dall'interfaccia User
    * @return Ritorna l'hash della password
    */
    @Override
    public long getUserHash()
    {
        return this.hash;
    }
    
    /**
    * Nel caso in cui l'utente venga chiamato da una print, scriverà
    * l'username a video
    * @return "Username"
    */
    @Override
    public String toString()
    {
        return new String(this.login);
    }
}
