package Code.KMeans;
import java.util.ArrayList;
import Code.KMeans.Exceptions.*;
/**
 * @author Giovanni Castellano
 */
public class Clusters {
    private int noclusters;
    private ArrayList<ArrayList<Point>> clusters;
    
    /**
    * Costruttore che prende in input solamente il numero di cluster
    * con cui inizializzare l'ArrayList
    * @param  noclusters  Numero di cluster inizializzati
    */
    public Clusters(int noclusters)
    {
        try{
        if(noclusters<0)
            throw new NumberOfClusterException();
        }catch(NumberOfClusterException NOCE)
        {
            System.out.println(NOCE + "\nThe absolute value has been taken");
        }
        this.noclusters = Math.abs(noclusters);
        
        for(int i=0; i<noclusters; i++)
            this.clusters.add(new ArrayList<>());
    }
    
    /**
    * Costruttore che prende direttamente un ArrayList in input
    * e ne copia tutti gli elementi nel proprio
    * @param  clusters  Array di punti, quindi cluster, ma secondo i mezzi
    * dell'utente
    */
    public Clusters(ArrayList<ArrayList<Point>> clusters)
    {
        for(int i=0; i<clusters.size(); i++)
        {
            this.clusters.add(new ArrayList<>());
            noclusters++;
            for(Point point:clusters.get(i))
                this.clusters.get(i).add(new Point(point));
        }       
    }
    
    /**
    * Costruttore che prende in input un altra classe Clusters
    * e ne copia tutti i cluster contenuti
    * @param  clusters  Struttura di gestione dei cluster
    */
    public Clusters(Clusters clusters) throws Exception
    {
        for(int i=0; i<clusters.size(); i++)
        {
            this.clusters.add(new ArrayList<>());
            noclusters++;
            for(int j=0; j<clusters.getCluster(i).size(); j++)
                this.clusters.get(i).add(new Point(clusters.getPoint(i, j)));
        }       
    }
    
    /**
    * Inizializzatore eseguito prima di qualsiasi costruttore
    */
    {
        this.clusters=new ArrayList<>();
        noclusters=0;
    }
    
    /**
    * Aggiunge un cluster all'insieme dei cluster,
    * e incrementa il contatore di cluster
    * @param  points  ArrayList<Point>, quindi un cluster, da aggiungere
    * all'attributo locale
    */
    public void addCluster(ArrayList<Point> points)
    {
        this.clusters.add(new ArrayList<>());
        for(Point point:points)
            this.clusters.get(this.clusters.size()-1).add(point);
        noclusters++;
    }
    
    /**
    * Sostituisce un cluster all'indice index con il nuovo cluster points
    * @param  index  indice del cluster da sostituire
    * @param  points  cluster che andrà a sostituire uno dei cluster locali
    */
    public void setCluster(int index, ArrayList<Point> points)
    {
        try
        {
            if(index>=this.noclusters)
                throw new ClusterOutOfBoundException();
            for(Point point:points)
                this.clusters.get(index).add(point);
        }catch(ClusterOutOfBoundException COOBE)
        {
            System.out.println(COOBE + ": requested for index " + index + " while having " + this.noclusters + " clusters (max index is " + (this.noclusters-1) + ")");
        }
        catch(Exception E)
        {
            E.printStackTrace();
            System.exit(0);
        }
    }
    
    /**
    * Cancella il cluster all'indice index
    * @param  index  indice del cluster da eliminare
    */
    public void deleteCluster(int index)
    {
        try
        {
            if(index>=this.noclusters)
                throw new ClusterOutOfBoundException();
            this.clusters.remove(index);
            noclusters--;
        }catch(ClusterOutOfBoundException COOBE)
        {
            System.out.println(COOBE + ": requested for index " + index + " while having " + this.noclusters + " clusters (max index is " + (this.noclusters-1) + ")");
        }
        catch(Exception E)
        {
            E.printStackTrace();
            System.exit(0);
        }
    }
    
    /**
    * Aggiunge un punto point al cluster in posizione index
    * @param  point  Punto da aggiungere ad uno dei cluster
    * @param  index  Indice del cluster a cui verrà aggiunto il punto
    */
    public void addPoint(int index, Point point)
    {
        try
        {
            if(index>=this.noclusters)
                throw new ClusterOutOfBoundException();
        this.clusters.get(index).add(new Point(point));
        }catch(ClusterOutOfBoundException COOBE)
        {
            System.out.println(COOBE + ": requested for index " + index + " while having " + this.noclusters + " clusters (max index is " + (this.noclusters-1) + ")");
        }
        catch(Exception E)
        {
            E.printStackTrace();
            System.exit(0);
        }
    }
    
    /**
    * Cancella un punto da qualsiasi cluster
    * La cancellazione può essere multipla, se il punto dovesse
    * risultare appartenere a più cluster
    * @param  point  Punto da eliminare da qualsiasi cluster lo contenga
    */
    public void deletePoint(Point point)
    {
        for(int i=0; i<noclusters; i++)
        {
            for(int j=0; j<this.clusters.get(i).size(); j++)
            {
                if(this.clusters.get(i).get(j).getX()==point.getX())
                    if(this.clusters.get(i).get(j).getY()==point.getY())
                        this.clusters.get(i).remove(j);
            }
        }
    }
    
    /**
    * Ritorna un punto di un cluster definito da row 
    * e in posizione column in quel cluster
    * @param  row  Indice del cluster da cui prelevare il punto
    * @param column Indice del punto, riguardante il cluster definito da row
    * @returns  Il punto trovato
    */
    public Point getPoint(int row, int column) throws Exception
    {
        int badval=0;
        try{
            if(row>=this.noclusters)
            {
                badval=row;
                throw new ClusterOutOfBoundException();
            }
            if(column>=this.clusters.get(row).size())
            {
                badval=column;
                throw new ClusterOutOfBoundException();
            }
        return this.clusters.get(row).get(column);
        }catch(ClusterOutOfBoundException COOBE)
        {
            if(badval==row)
                System.out.println(COOBE + ": requested for index " + row + " while having " + this.noclusters + " clusters (max index is " + (this.noclusters-1) + ")");
            else if(badval==column)
                System.out.println(COOBE + ": requested for point at index " + column + " while having " + this.clusters.get(row).size() + " points (max index is " + (this.clusters.get(row).size()-1) + ")");
            else
                throw new Exception();
            System.exit(0);
        }
        catch(Exception E)
        {
            E.printStackTrace();
            System.exit(0);
        }
        return this.clusters.get(row).get(column);
    }
    
    /**
    * Ritorna un cluster intero, identificato da index
    * @param  index  Indice che identifica il cluster da ritornare
    * @return Il cluster trovato
    */
    public ArrayList<Point> getCluster(int index)
    {
        try{
            if(index>=this.noclusters)
                throw new ClusterOutOfBoundException();
            return this.clusters.get(index);
        }catch(ClusterOutOfBoundException COOBE)
        {
            System.out.println(COOBE + ": requested for index " + index + " while having " + this.noclusters + " clusters (max index is " + (this.noclusters-1) + ")");
            System.exit(0);
        }
        catch(Exception E)
        {
            E.printStackTrace();
            System.exit(0);
        }
        return this.clusters.get(index);
    }
    
    /**
    * Ritorna il numero di cluster presenti nella struttura
    * @return Il numero di cluster presenti nella struttura
    */
    public int size()
    {
        return this.clusters.size();
    }
    /**
    * Ritorna il numero di cluster presenti nella struttura
    * secondo l'attributo variabile noclusters
    * @return Il numero di cluster presenti nella struttura
    */
    public int getNOClusters()
    {
        return this.noclusters;
    }
    
    /**
    * Confronta questa struttura ad una struttura passata come parametro
    * e ritorna true nel caso siano uguali (in termini di cluster e punti)
    * @param  clusters  Struttura da confrontare a this
    * @return booleano indicante l'esito del confronto
    */
    public boolean isEqual(Clusters clusters) throws Exception
    {
        boolean isequal=true;
        if(this.clusters.size()==clusters.size())
        {
            for(int i=0; i<this.clusters.size()&&isequal==true; i++)
            {
                if(this.clusters.get(i).size()==clusters.getCluster(i).size())
                {
                    for(int j=0; j<this.clusters.get(i).size(); j++)
                    {
                        if(this.clusters.get(i).get(j).getX()!=clusters.getPoint(i, j).getX())
                        {
                            isequal=false;
                            break;
                        }
                        else if(this.clusters.get(i).get(j).getY()!=clusters.getPoint(i, j).getY())
                        {
                            isequal=false;
                            break;
                        }
                    }
                }
                else
                {
                    isequal=false;
                    break;
                }
            }
        }
        else
            isequal=false;
        return isequal;
    }
    
    /**
    * Pulisce tutti i cluster svuotandoli, ma essi permangono allocati
    */
    public void clearClusters()
    {
        for(ArrayList<Point> cluster:this.clusters)
        {
            cluster.clear();
        }
    }
    
    /**
    * Pulisce tutti i cluster e li dealloca
    */
    public void clearAll()
    {
        for(ArrayList<Point> cluster:this.clusters)
        {
            cluster.clear();
        }
        this.clusters.clear();
    }
}
