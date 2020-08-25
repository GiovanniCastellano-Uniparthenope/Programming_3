package Code;
/**
 * @author Giovanni Castellano
 */
public class Product {
    private String id;
    private String name;
    private String description;
    private int stock;
    private double cost;
    private String category;
    
    /**
    * Costruttore unico che prende in input tutte le caratteristiche del 
    * prodotto
    * 
    * @param  id    Id del prodotto
    * @param  name  Nome del prodotto
    * @param  description  Descrizione del prodotto
    * @param  stock  Quantità immagazzinata del prodotto
    * @param  cost  Costo del prodotto
    * @param  category  Categoria del prodotto
    * @see  AdminForm per capire quali categorie esistono, sono definite
    * nella comboBox della pagina "Manage Market"
    */
    public Product(String id, String name, String description, int stock, double cost, String category)
    {
        this.id=new String(id);
        this.name=new String(name);
        this.description=new String(description);
        this.stock=stock;
        this.cost=cost;
        this.category=new String(category);
    }
    
    /**
    * @return ID
    */
    public String getID()
    {
        return new String(this.id);
    }
    
    /**
    * @return Nome 
    */
    public String getName()
    {
        return new String(this.name);
    }
    
    /**
    * @return Descrizione
    */
    public String getDescription()
    {
        return new String(this.description);
    }
    
    /**
    * @return Quantità immagazzinata
    */
    public int getStock()
    {
        return this.stock;
    }
    
    /**
    * @return Costo
    */
    public double getCost()
    {
        return this.cost;
    }
    
    /**
    * @return Categoria
    */
    public String getCategory()
    {
        return new String(this.category);
    }
    
    /**
    * @param  ID  ID da settare
    */
    public void setID(String ID)
    {
        this.id=new String(ID);
    }
    
    /**
    * @param  name  Nome da settare
    */
    public void setName(String name)
    {
        this.name=new String(name);
    }
    
    /**
    * @param  description  Descrizione da settare
    */
    public void setDescription(String description)
    {
        this.description=new String(description);
    }
    
    /**
    * @param  count  Quantità immagazzinata da settare
    */
    public void setStock(int count)
    {
        this.stock=count;
    }
    
    /**
    * @param  cost  Costo da settare
    */
    public void setCost(double cost)
    {
        this.cost=cost;
    }
    
    /**
    * @param  category  Categoria da settare
    */
    public void setCategory(String category)
    {
        this.category=new String(category);
    }
    
    /**
    * @return  Stringa contenente le informazioni del prodotto
    */
    @Override
    public String toString()    
    {
        return "\n-------------------------------------------------\n" + this.getID() + "\n" + this.getName() + "\n" + this.getDescription() + "\n" + this.getStock() + "\n" + this.getCost() + "\n" + this.getCategory() + "\n-------------------------------------------------";
    }
}
