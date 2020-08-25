package Code.TFIDF;

import java.util.ArrayList;
/**
 * @author Giovanni Castellano
 */
public class WordBox {
    private ArrayList<String> keys;
    private ArrayList<Long> vals;
    private Long count;
    
    /**
    * Costruttore che inizializza gli attributi ed unico disponibile
    */
    public WordBox()
    {
        this.keys=new ArrayList<>();
        this.vals=new ArrayList<>();
        count=0L;
    }
    
    /**
    * Metodo interno utile a riordinare le parole estratte
    * secondo la loro frequenza in ordine decrescente.
    * Dato che l'algoritmo TFIDF è stato strutturato in modo tale
    * da unire tutte le parole oltre un certo numero scelto dall'utente
    * sotto un'unica parola chiamata "others", l'algoritmo andrà a porre
    * la stringa "others" alla fine, anche laddove essa abbia, in totale,
    * una frequenza maggiore rispetto alle altre parole.
    * @see TFIDF
    */
    private void sort()
    {
        Long max;
        int index;
        for(int i=0; i<this.count-1; i++)
        {
            max=Long.MIN_VALUE;
            index=i+1;
            for(int j=i+1; j<this.count; j++)
            {
                if(vals.get(j)>max && "others".equals(keys.get(j))==false)
                {
                    max=vals.get(j);
                    index=j;
                }
            }
            if(vals.get(i)<max)
            {
                Long app=vals.get(i);
                vals.set(i, max);
                vals.set(index, app);
                
                String apps=keys.get(i);
                keys.set(i, keys.get(index));
                keys.set(index, apps);
            }
        }
        
    }
    
    /**
    * Unisce alla WordBox chiamante il metodo la WordBox  
    * passata come parametro
    * @param  otherbox  l'altra WordBox da unire a questa
    */
    public void mergeBoxes(WordBox otherbox)
    {
        ArrayList<String> otherkeys=otherbox.getKeys();
        
        for(String otherkey:otherkeys)
            this.addKeyVal(otherkey, otherbox.getVal(otherkey));
    }
    
    /**
    * Il metodo aggiunge una combinazione parola-frequenza a questa WordBox
    * laddove la chiave non sia già presente nella WordBox, altrimenti, 
    * incrementerà il valore della sua frequenza di 1 unità
    * @param  key  La stringa che verrà memorizzata come chiave di ricerca
    * @param  val  Il valore che verrà memorizzato ed identificato dalla 
    * chiave che indica la frequenza della stringa chiave
    */
    public void addKeyVal(String key, Long val)
    {
        key=key.toLowerCase();
        int index = keys.indexOf(key);
        if(index<0)
        {
            this.keys.add(key);
            this.vals.add(val);
            this.count++;
        }
        else
        {
            this.vals.set(index, vals.get(index)+val);
        }
    }
    
    /**
    * Il metodo rimuove una combinazione parola-frequenza da questa WordBox
    * laddove la chiave sia presente nella WordBox e abbia esattamente val
    * come frequenza
    * @param  key  La stringa memorizzata come chiave di ricerca
    * @param  val  Il valore memorizzato ed identificato dalla chiave che
    * indica la frequenza della stringa chiave
    */
    public void removeKeyVal(String key, Long val)
    {
        key=key.toLowerCase();
        for(int i=0; i<this.count; i++)
            if(this.keys.get(i).equals(key) && this.vals.get(i).compareTo(val)==0)
            {
                keys.remove(i);
                vals.remove(i);
                count--;
            }
    }
    
    /**
    * Il metodo rimuove una combinazione parola-frequenza da questa WordBox
    * laddove la chiave sia presente nella WordBox con qualsiasi valore di
    * frequenza associato
    * @param  key  La stringa memorizzata come chiave di ricerca
    */
    public void removeByKey(String key)
    {
        key=key.toLowerCase();
        int index = this.keys.indexOf(key);
        if(index>=0)
        {
            keys.remove(index);
            vals.remove(index);
            count--;
        }
    }
    
    /**
    * Il metodo rimuove tutte le combinazioni parola-frequenza da questa WordBox
    * che dovessero avere val come valore frequenza
    * @param  val  La stringa memorizzata come chiave di ricerca
    */
    public void removeByVal(Long val)
    {
        for(int i=0; i<this.count; i++)
            if(this.vals.get(i).equals(val))
            {
                keys.remove(i);
                vals.remove(i);
                count--;
            }
    }
    
    /**
    * Il metodo ritorna un ArrayList contenente tutte le parole (chiavi)
    * registrate fino al momento della chiamata
    * @return  ArrayList di stringhe contenente tutte le parole della WordBox 
    */
    public ArrayList<String> getKeys()
    {
        this.sort();
        ArrayList<String> keyarray = new ArrayList();
        for(String key:keys)
            keyarray.add(key);
        
        return keyarray;
    }
    
    /**
    * Il metodo ritorna il valore associato alla parola passata come parametro,
    * oppure 0 se la parola non esiste nella wordbox
    * @param  key  Parola da cercare nella WordBox, di cui ritornare il valore
    * @return  La frequenza della parola se trovata, 0 altrimenti
    */
    public Long getVal(String key)
    {
        key=key.toLowerCase();
        int index = this.keys.indexOf(key);
        if(index<0)
            return 0L;
        else
            return this.vals.get(index);
    }
    
    /**
    * Il metodo ritorna il numero di parole presenti nella WordBox
    * @return  Il numero di parole presenti nella WordBox
    */
    public Long getCount()
    {
        return this.count;
    }
}
