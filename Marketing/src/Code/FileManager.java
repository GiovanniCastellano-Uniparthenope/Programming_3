package Code;

import Code.Exceptions.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Giovanni Castellano
 */
public class FileManager {
    private FileManager()
    {}
    
    /**
    * Metodo statico per la lettura del file products.txt
    * Il file contiene i prodotti attualmente sul mercato
    * 
    * @return  I prodotti sottoforma di ArrayList
    * @see Product
    */
    public static ArrayList<Product> readProductFile()
    {
        List<String> stringlist = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        try{
            if(Files.exists(Paths.get("products.txt")))
                stringlist=Files.readAllLines(Paths.get("products.txt"));
        }catch(Exception E)
        {
            System.err.println("Errore nel caricamento dei prodotti: "); E.printStackTrace();
        }
        try
        {
            if(stringlist.size()>0)
            {
                String[] s = new String[6];
                int j=0;
                for(String string:stringlist)
                {
                    s[j]=string;
                    j++;
                    if(j==6)
                    {
                       products.add(new Product(s[0], s[1], s[2], Integer.parseInt(s[3]), Double.parseDouble(s[4]), s[5]));
                       j=0;
                    }
                }
                if(j!=0)
                    throw new FileException();
            }
        }catch(FileException FE)
        {
            System.out.println(FE + ": products.txt file number of rows mismatches with the expected number of rows");
        }
        return products;
    }
    
    /**
    * Metodo statico per la scrittura sul file products.txt 
    * dei prodotti attualmente contenuti in products, 
    * in modo da salvare tutti i cambiamenti avvenuti
    * 
    * @param  products  L'ArrayList di prodotti da tramutare in stringhe e da
    * scrivere su file
    * @see Product
    */
    public static void writeProductFile(ArrayList<Product> products)
    {
        List<String> stringlist = new ArrayList<>();
        for(Product product:products)
        {
            stringlist.add(new String(product.getID()));
            stringlist.add(new String(product.getName()));
            stringlist.add(new String(product.getDescription()));
            stringlist.add(new String(Integer.toString(product.getStock())));
            stringlist.add(new String(Double.toString(product.getCost())));
            stringlist.add(new String(product.getCategory()));
        }
        try{
            Files.write(Paths.get("products.txt"), stringlist);
        }catch(Exception E)
        {
            System.err.println("Errore nel salvataggio dei prodotti: "); E.printStackTrace();
        }
    }
    
    /**
    * Metodo statico per la lettura del file users.txt
    * Il file contiene gli username e gli hash di ogni utente
    * 
    * @return  Le credenziali degli utenti messe in un ArrayList di User
    * @see User
    */
    public static ArrayList<User> readUserFile()
    {
        ArrayList<User> users = new ArrayList<>();
        users.add(new Admin("admin", FileManager.hash("admin")));
        List<String> stringlist = new ArrayList<>();
        try{
            if(Files.exists(Paths.get("users.txt")))
                stringlist=Files.readAllLines(Paths.get("users.txt"));
        }catch(Exception E)
        {
            System.err.println("Errore nel caricamento degli utenti: "); E.printStackTrace();
        }
        String[] s = new String[3];
        int j=0;
        for(String string:stringlist)
        {
            s[j]=string;
            j++;
            if(j==3)
            {
                if("Guest".equals(s[0]))
                    users.add(new Guest(s[1], Long.parseLong(s[2])));
                else
                    users.add(new Admin(s[1], Long.parseLong(s[2])));
                j=0;
            }
        }
        try{
        if(j!=0)
            throw new FileException();
        }catch(FileException FE)
        {
            System.out.println(FE + ": users.txt file number of rows mismatches with the expected number of rows");
        }
        
        return new ArrayList<User>(users);
    }
    
    /**
    * Metodo statico per la scrittura del file products.txt
    * con gli utenti contenuti in users
    * 
    * @param  users  ArrayList di utenti da scrivere su file
    * @see User
    */
    public static void writeUserFile(ArrayList<User> users)
    {
        List<String> usersdata = new ArrayList<>();
        for(User user:users)
        {
            if(!("admin".equals(user.getUserLoginName().toLowerCase())))
            {
                try{
                if(user instanceof Guest)
                {
                    usersdata.add("Guest");
                }
                else if(user instanceof Admin)
                {
                    usersdata.add("Admin");
                }
                else
                    throw new FileException();

                usersdata.add(user.getUserLoginName().toLowerCase());
                usersdata.add(Long.toString(user.getUserHash()));

                }catch(FileException FE)
                {
                    System.out.println(FE + ": Errore nel riconoscimento dell'utente (Admin/Guest)");
                }
            }
        }
        try{
            Files.write(Paths.get("users.txt"), usersdata);
        }catch(Exception E)
        {
            System.err.println("Errore nel salvataggio degli utenti: "); E.printStackTrace();
        }
    }
    
    /**
    * Metodo statico per la lettura del file username.txt
    * 
    * username è estrapolato in base al parametro user
    * 
    * @param  user  L'user del quale leggere il file dei prodotti comprati
    * @return  I prodotti comprati sottoforma di ArrayList
    * @see User
    * @see Product
    */
    public static ArrayList<Product> readPurchasedFile(User user)
    {
        List<String> stringlist = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        try{
        String path = "history files/";
        path=path.concat(user.getUserLoginName() + ".txt");
        if(Files.exists(Paths.get(path)))
            stringlist=Files.readAllLines(Paths.get(path));
        }catch(Exception E)
        {
            System.err.println("Errore nel caricamento dei dati di acquisto: "); E.printStackTrace();
        }
        try
        {
            if(stringlist.size()>0)
            {
                String[] s = new String[6];
                int j=0;
                for(String string:stringlist)
                {
                    s[j]=string;
                    j++;
                    if(j==6)
                    {
                       products.add(new Product(s[0], s[1], s[2], Integer.parseInt(s[3]), Double.parseDouble(s[4]), s[5]));
                       j=0;
                    }
                }
                if(j!=0)
                    throw new FileException();
            }
        }catch(FileException FE)
        {
            System.out.println(FE + ": products.txt file number of rows mismatches with the expected number of rows");
        }
        return products;
    }
    
    /**
    * Metodo statico per la scrittura del file username.txt
    * 
    * username è estrapolato in base al parametro user
    * 
    * @param  user  L'user del quale scrivere il file dei prodotti comprati
    * @param  products  I prodotti comprati dall'utente, da scrivere su file
    * @see User
    * @see Product
    */
    public static void writePurchasedFile(ArrayList<Product> products, User user)
    {
        String path = "history files/";
        path=path.concat(user.getUserLoginName() + ".txt");
        List<String> stringlist = new ArrayList<>();
        for(Product product:products)
        {
            stringlist.add(new String(product.getID()));
            stringlist.add(new String(product.getName()));
            stringlist.add(new String(product.getDescription()));
            stringlist.add(new String(Integer.toString(product.getStock())));
            stringlist.add(new String(Double.toString(product.getCost())));
            stringlist.add(new String(product.getCategory()));
        }
        try{
            if(Files.notExists(Paths.get("history files")))
            {
                Files.createDirectory(Paths.get("history files"));
            }
            Files.write(Paths.get(path), stringlist);
        }catch(Exception E)
        {
            System.err.println("Errore nel salvataggio dei dati di acquisto: "); E.printStackTrace();
        }
    }
    
    /**
    * Metodo statico per la cancellazione del file username.txt
    * 
    * username è estrapolato in base al parametro user
    * 
    * @param  user  L'user del quale cancellare il file dei prodotti comprati
    * @see User
    * @see Product
    */
    public static void deletePurchasedFile(User user)
    {
        String path = "history files/";
        path=path.concat(user.getUserLoginName() + ".txt");
        try{
                Files.deleteIfExists(Paths.get(path));
        }catch(Exception E)
        {
            System.err.println("Errore nella cancellazione dei dati di acquisto per"+ user.getUserLoginName() +": "); E.printStackTrace();
        }
    }
    
    /**
    * Metodo statico per la lettura del file username.txt
    * 
    * username è estrapolato in base al parametro user
    * 
    * @param  user  L'user del quale leggere il file delle offerte
    * @return  Le offerte sottoforma di ArrayList
    * @see User
    * @see Product
    */
    public static ArrayList<Product> readOffersFile(User user)
    {
        List<String> stringlist = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        String path = "offers files/";
        path=path.concat(user.getUserLoginName() + ".txt");
        try{
            if(Files.notExists(Paths.get("offers files")))
            {
               Files.createDirectory(Paths.get("offers files"));
            }
        if(Files.exists(Paths.get(path)))
            stringlist=Files.readAllLines(Paths.get(path));
        }catch(Exception E)
        {
            System.err.println("Errore nel caricamento delle offerte in corso: "); E.printStackTrace();
        }
        try
        {
            if(stringlist.size()>0)
            {
                String[] s = new String[6];
                int j=0;
                for(String string:stringlist)
                {
                    s[j]=string;
                    j++;
                    if(j==6)
                    {
                       products.add(new Product(s[0], s[1], s[2], Integer.parseInt(s[3]), Double.parseDouble(s[4]), s[5]));
                       j=0;
                    }
                }
                if(j!=0)
                    throw new FileException();
            }
        }catch(FileException FE)
        {
            System.out.println(FE + ": offers.txt file number of rows mismatches with the expected number of rows");
        }
        return products;
    }
    
    /**
    * Metodo statico per la scrittura del file username.txt
    * 
    * username è estrapolato in base al parametro user
    * 
    * @param  user  L'user del quale scrivere il file delle offerte
    * @param  products  I prodotti in offerta per user
    * @see User
    * @see Product
    */
    public static void writeOffersFile(ArrayList<Product> products, User user)
    {
        String path = "offers files/";
        path=path.concat(user.getUserLoginName() + ".txt");
        try{
            if(Files.notExists(Paths.get("offers files")))
            {
               Files.createDirectory(Paths.get("offers files"));
            }
            List<String> stringlist = new ArrayList<>();
            for(Product product:products)
            {
                stringlist.add(new String(product.getID()));
                stringlist.add(new String(product.getName()));
                stringlist.add(new String(product.getDescription()));
                stringlist.add(new String(Integer.toString(product.getStock())));
                stringlist.add(new String(Double.toString(product.getCost())));
                stringlist.add(new String(product.getCategory()));
            }
            Files.write(Paths.get(path), stringlist);
        }catch(Exception E)
        {
            System.err.println("Errore nel salvataggio delle offerte in corso: "); E.printStackTrace();
        }
    }
    
    /**
    * Metodo statico per la cancellazione del file username.txt
    * 
    * username è estrapolato in base al parametro user
    * 
    * @param  user  L'user del quale cancellare il file delle offerte
    * @see User
    * @see Product
    */
    public static void deleteOffersFile(User user)
    {
        String path = "offers files/";
        path=path.concat(user.getUserLoginName() + ".txt");
        try{
                Files.deleteIfExists(Paths.get(path));
        }catch(Exception E)
        {
            System.err.println("Errore nella cancellazione dei dati delle offerte per"+ user.getUserLoginName() +": "); E.printStackTrace();
        }
    }
    
    /**
    * Metodo statico che ritorna l'hash di una stringa
    * 
    * L'hash è calcolato moltiplicando ogni carattere della stringa
    * per la sua posizione, sommando tutti questi prodotti in un unico hash
    * 
    * @param  string  La stringa di cui calcolare l'hash
    * @return  L'hash della stringa
    */
    public static int hash(String string)
    {
        int hash = 0;
        for(int i=0; i<string.length(); i++)
        {
            hash+=(int)string.charAt(i)*(i+1);
        }
        return Math.abs(hash);
    }
}
