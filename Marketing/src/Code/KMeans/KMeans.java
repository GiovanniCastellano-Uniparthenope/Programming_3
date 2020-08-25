package Code.KMeans;
import Code.KMeans.Exceptions.*;
import java.util.ArrayList; //ArrayList
import java.util.Iterator;  //Iterator
import java.util.Random;    //Random
/**
 * @author Giovanni Castellano
 */
public class KMeans {
    Clusters clusters;
    private int noclusters;
    private ArrayList<Point> points;
    
    /**
    * Costruttore che prende in input un numero di cluster noclusters
    * e un insieme di punti sottoforma di varargs points
    * @param  noclusters  numero di cluster previsti
    * @param  points  punti da aggiungere alla lista di punti
    */
    public KMeans(int noclusters, Point... points)
    {
        try{
        if(noclusters<=0)
            throw new NumberOfClusterException();
        }catch(NumberOfClusterException NOCE)
        {
            System.out.println(NOCE + "\nThe number of clusters must be higher than zero");
        }
        this.noclusters=Math.abs(noclusters);
        clusters = new Clusters(this.noclusters);
        this.points=new ArrayList<>();
        
        for(Point point:points)
            this.points.add(new Point(point));
    }
    /**
    * Costruttore che prende in input un numero di cluster noclusters
    * e un insieme di punti sottoforma di Iterable points
    * @param  noclusters  numero di cluster previsti
    * @param  points  punti da aggiungere alla lista di punti
    */
    public KMeans(int noclusters, Iterable<Point> points)
    {
        try{
        if(noclusters<0)
            throw new NumberOfClusterException();
        }catch(NumberOfClusterException NOCE)
        {
            System.out.println(NOCE + "\nIt has been taken as absolute value");
        }
        this.noclusters=Math.abs(noclusters);
        clusters = new Clusters(this.noclusters);
        this.points=new ArrayList<>();
        Iterator iter = points.iterator();
        
        while(iter.hasNext())
        {
            this.points.add(new Point((Point)iter.next()));
        }
    }
    
    /**
    * Metodo interno privato utile al calcolo della distanza tra due punti
    * @param  p1  Punto di cui calcolare la distanza dal punto p2
    * @param  p2  Punto di cui calcolare la distanza dal punto p1
    * @return  La distanza tra i punti
    */
    private double calculateDistanceBetweenPoints(Point p1, Point p2)
    {
        return Math.sqrt(Math.abs(Math.pow(p1.getX(), 2) - Math.pow(p2.getX(), 2) + Math.pow(p1.getY(), 2) - Math.pow(p2.getY(), 2)));
    }
    
    /**
    * Metodo principale che esegue l'algoritmo K-Means
    * 
    * A partire semplicemente dall'Array di punti, ne calcola
    * i centroidi e ne calcola la distanza da ognuno per definire in quale
    * cluster devono essere inseriti, per poi continuare finchè il risultato
    * precedente non coincide con il risultato calcolato
    * 
    * @return  I cluster contenenti i punti secondo una delle soluzioni ottime
    */
    public Clusters doClustering() throws Exception
    {
	ArrayList<Point> centroids = new ArrayList<>();
	Clusters clusthelper = new Clusters(this.clusters.getNOClusters());
	Random random = new Random();
	int val;
	boolean isnew;
	for(int i=0; i<this.noclusters; i++)
	{
            do{
            isnew=true;
            val = random.nextInt(this.points.size());
            for(Point center:centroids)
                    if(points.get(val).getY()==center.getY()&&points.get(val).getX()==center.getX())
                            isnew=false;
            }while(isnew==false);
            centroids.add(new Point(points.get(val)));
	}
	
	double mindistance;
	int minindex=0;
	double othervalue;
	for(int i=0; i<this.points.size(); i++)
        {
            if(centroids.indexOf(points.get(i))==-1)
            {
                    mindistance=99999;
                    for(int j=0; j<noclusters; j++)
                    {
                                    othervalue=this.calculateDistanceBetweenPoints(centroids.get(j), this.points.get(i));
                                    if(mindistance >= othervalue)
                                    {
                                            mindistance = othervalue;
                                            minindex=j;
                                    }
                    }
            clusters.addPoint(minindex, new Point(this.points.get(i)));
            }
	}
	double avgx;
	double avgy;
	while(clusters.isEqual(clusthelper)==false)
	{
            clusthelper.clearAll();
            clusthelper=new Clusters(clusters);
            centroids.clear();
            for(int i=0; i<this.noclusters; i++)
            {
                int j;
                avgx=0;
                avgy=0;
                for(j=0; j<clusters.getCluster(i).size(); j++)
                {
                        avgx+=clusters.getPoint(i,j).getX();
                        avgy+=clusters.getPoint(i,j).getY();
                }
                avgx=avgx/j;
                avgy=avgy/j;
                centroids.add(new Point(avgx, avgy));
            }
            clusters.clearClusters();
            for(int i=0; i<this.points.size(); i++)
            {
                mindistance=99999;
                if(centroids.indexOf(points.get(i))==-1)
                {
                    for(int j=0; j<noclusters; j++)
                    {
                        othervalue=this.calculateDistanceBetweenPoints(centroids.get(j), this.points.get(i));
                        if(mindistance >= othervalue)
                        {
                            mindistance = othervalue;
                            minindex=j;
                        }  
                    }
                    clusters.addPoint(minindex, new Point(this.points.get(i)));
                }
            }
	}
	return clusters;
    }
}
