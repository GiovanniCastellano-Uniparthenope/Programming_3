package Code.KMeans;
/**
 * @author Giovanni Castellano
 */
public class Point {
    private double x;
    private double y;
    
    /**
    * Costruttore che prende in input le coordinate x e y
    * @param  x  Coordinata x del punto
    * @param  y  Coordinata y del punto
    */
    public Point(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    
    /**
    * Costruttore che prende in input un Point point da cui estrapolare le
    * coordinate
    * @param  point  Punto da cui verranno copiate le coordinate x e y
    */
    public Point(Point point)
    {
        this.x=point.getX();
        this.y=point.getY();
    }
    
    /**
    * @return  Coordinata x
    */
    public double getX()
    {
        return this.x;
    }
    
    /**
    * @return  Coordinata y
    */
    public double getY()
    {
        return this.y;
    }
    
    /**
    * Metodo toString che permette di visualizzare il punto 
    * senza doverne estrapolare le coordinate manualmente
    * @return "X-Y"
    */
    @Override
    public String toString()
    {
        return (this.getX() + "-" + this.getY());
    }
}
