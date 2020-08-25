package Code.KMeans;

import Code.*;
import Code.KMeans.Exceptions.*;
import java.util.ArrayList;

/**
 * @author Giovanni Castellano
 */
public class Mediator {
    private ArrayList<User> users;
    private int K;
    private boolean ok;
    
    /**
    * Costruttore della classe che prende in input quanti cluster richiedere
    * all'algoritmo K-Means, ovvero K, e un Iterable di utenti users
    * @param  K  Numero di cluster da richiedere all'algoritmo K-Means
    * @param  users  Utenti trasformati in punti per l'algoritmo K-Means
    */
    public Mediator(int K, Iterable<User> users)
    {
        this.K=K;
        for(User user:users)
        {
            if(user instanceof Guest)
            this.users.add(user);
        }
        try{
        if(this.K==0||this.K>this.users.size())
            throw new MediatorException();
        }catch(MediatorException E)
        {
            System.err.println(E.toString());
            ok=false;
        }
    }
    
    /**
    * Inizializzatore eseguito prima del costruttore
    */
    {
        K=1;
        users = new ArrayList<>();
        ok=true;
    }
    
    /**
    * Il metodo fa da tramite con l'algoritmo K-Means, il che ritornerebbe
    * una struttura Clusters, mentre in questo caso la struttura viene tradotta
    * sottoforma di ArrayList<ArrayList<double[]>> in modo da essere leggibile
    * e riutilizzabile dall'utente
    * 
    * Inoltre, l'utente non deve curarsi dei punti di tipo Point da passare all'
    * algoritmo, dal momento che gli utenti passati precedentemente come 
    * parametro al costruttore sono prima tradotti sottoforma di Point, poi
    * passati all'algoritmo K-Means
    * 
    * @return  Un ArrayList contenente tutti i cluster, con i punti sottoforma
    * di array[2] di double
    */
    public ArrayList<ArrayList<double[]>> mediateKMeans() throws Exception
    {
        ArrayList<ArrayList<double[]>> output = new ArrayList<>();
        if(ok)
        {
            ArrayList<Point> points = new ArrayList<>();
            ArrayList<Product> userpurchased;
            for(User user:this.users)
            {
                int nopurchases=0;
                double moneyspent=0f;
                if(user instanceof Guest)
                {
                    userpurchased = FileManager.readPurchasedFile(user);
                    for(Product product:userpurchased)
                    {
                        nopurchases++;
                        moneyspent+=product.getCost();
                    }
                    points.add(new Point(nopurchases, moneyspent));
                }
            }
            KMeans algorithm = new KMeans(this.K, points);
            Clusters clusters = algorithm.doClustering();
            for(int i=0; i<clusters.getNOClusters(); i++)
            {
                points=clusters.getCluster(i);
                output.add(new ArrayList<>());
                for(int j=0; j<points.size(); j++)
                {
                    double[] val = new double[2];
                    val[0]=points.get(j).getX();
                    val[1]=points.get(j).getY();
                    output.get(i).add(val);
                }
            }
        }
        return output;
    }
}
