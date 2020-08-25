package Code.TFIDF;

import java.util.ArrayList;

/**
 * @author Giovanni Castellano
 */
public class WordExtractor {
    private WordBox words;
    private ArrayList<Character> separators;
    private String string;
    
    /**
    * Costruttore che prende, oltre alla stringa string da analizzare,
    * anche i separatori delle parole sottoforma di varargs
    * @param  string  Stringa da cui estrarre le parole
    * @param  separators  Varargs di char che definiscono i separatori di parole
    */
    public WordExtractor(String string, char... separators)
    {
        this.string=string;
        for(Character separator:separators)
            this.separators.add(separator);
    }
    
    /**
    * Costruttore che prende, oltre alla stringa string da analizzare,
    * anche i separatori delle parole sottoforma di struttura iterabile
    * @param  string  Stringa da cui estrarre le parole
    * @param  separators  Struttura iterabile contenente i separatori (char)
    */
    public WordExtractor(String string, Iterable<Character> separators)
    {
        this.string=string;
        for(Character separator:separators)
            this.separators.add(separator);
    }
    
    /**
    * Inizializzatore che alloca gli attributi prima di qualsivoglia 
    * costruttore
    */
    {
        string=new String();
        separators=new ArrayList<>();
        this.words = new WordBox();
    }
    
    /**
    * Metodo che elabora la stringa presa in input dal costruttore per poi 
    * estrapolarne le parole, contandole, e successivamente inserirle
    * in una WordBox con le relative frequenze
    * 
    * @return La WordBox contenente tutte le parole estratte con le loro
    * relative frequenze
    * @see WordBox
    */
    public WordBox extractWords()
    {
        String word=new String();
        for(int i=0; i<this.string.length(); i++)
        {
            if(this.separators.contains(this.string.charAt(i)))
            {
                if(word.length()>0)
                {
                    this.words.addKeyVal(word, 1L);
                    word=new String();
                }
            }
            else
            {
                word=word.concat(Character.toString(this.string.charAt(i)));
            }
        }
        if(word.length()>0)
        {
            this.words.addKeyVal(word, 1L);
        }
        return this.words;
    }
    
    /**
    * Metodo che ritorna il numero di parole contenute nella WordBox, oppure
    * ritorna 0 se non è ancora stato chiamato il metodo extractWords()
    * 
    * @return  Il numero di parole contenute nella WordBox
    */
    public Long getWordCount()
    {
        return this.words.getCount();
    }
}
