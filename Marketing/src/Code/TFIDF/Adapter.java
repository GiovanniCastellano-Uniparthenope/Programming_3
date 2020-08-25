package Code.TFIDF;

import Code.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Giovanni Castellano
 */
public class Adapter {
    private String string;
    
    /**
    * Unico costruttore disponibile che prende in input products, un
    * ArrayList di Product, e ne estrapola la stringa corrispondente in
    * modo tale da poter essere elaborata dall'algoritmo TFIDF, che lavora
    * esclusivamente su stringhe e documenti
    * 
    * @param  products  L'ArrayList di Product delle quali categorie si vuole 
    * calcolare la frequenza, trasformato in un'unica stringa per poter essere
    * un valido input all'algoritmo TFIDF
    * @see  TFIDF
    * @see  WordExtractor
    */
    public Adapter(ArrayList<Product> products)
    {
        this.string = "";
        for(Product product:products)
            for(int i=0; i<product.getStock(); i++)
                this.string=this.string.concat(product.getCategory() + " ");
    }
    
    /**
    * Il metodo adatta l'output dell'algoritmo TFIDF ad una 
    * HashMap<String, Long>, che è difatti ciò che viene ritornato
    * 
    * @return  Una HashMap<String, Long> contenente le parole (chiavi) con
    * i corrispettivi valori (frequenze) generati dall'algoritmo TFIDF
    * @see  TFIDF
    * @see  WordExtractor
    */
    public HashMap<String, Long> adaptTFIDFOutput()
    {
        ArrayList<String> str = new ArrayList<>();
        str.add(this.string);
        TFIDF algorithm = new TFIDF(3, str);
        HashMap<String, Long> map = new HashMap<>();
        WordBox words = algorithm.doAnalysis();
        
        ArrayList<String> strings = words.getKeys();
        ArrayList<Long> values = new ArrayList<>();
        
        for(String string:strings)
            values.add(words.getVal(string));

        for(int i=0; i<strings.size(); i++)
            map.put(strings.get(i), values.get(i));
        
        return map;
    }
}
