package Code.TFIDF;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * @author Giovanni Castellano
 */
public class TFIDF {
    private WordBox box;
    private ArrayList<String> paths;
    private ArrayList<String> strings;
    private int howmany;
    private boolean state;
    
    /**
    * Costruttore che prende in input una serie di paths
    * @param  paths  Varargs di paths
    */
    public TFIDF(String... paths)
    {
        for(String path:paths)
            this.paths.add(path);
    }
    
    /**
    * Costruttore che prende in input una serie di paths
    * e un valore che indica quante parole mostrare
    * @param  paths  Varargs di paths
    * @param  howmany  Valore indicante quante stringhe ritornare in output
    */
    public TFIDF(int howmany, String... paths)
    {
        this.howmany=howmany;
        for(String path:paths)
            this.paths.add(path);
    }
    
    /**
    * Costruttore che prende in input una serie di stringhe
    * 
    * In tal caso, le stringhe sono date direttamente in input e non dovranno
    * essere lette da file
    * @param  strings  Iterable di stringhe da elaborare
    */
    public TFIDF(Iterable<String> strings)
    {
        this.state=true;
        for(String string:strings)
            this.strings.add(string);
    }
    
    /**
    * Costruttore che prende in input una serie di stringhe
    * e un valore che indica quante parole mostrare
    * 
    * In tal caso, le stringhe sono date direttamente in input e non dovranno
    * essere lette da file
    * @param  strings  Iterable di stringhe da elaborare
    * @param  howmany  Valore indicante quante stringhe ritornare in output
    */
    public TFIDF(int howmany, Iterable<String> strings)
    {
        this.state=true;
        this.howmany=howmany;
        for(String string:strings)
            this.strings.add(string);
    }
    
    /**
    * Inizializzatore eseguito prima di qualsivoglia costruttore
    */
    {
        this.box = new WordBox();
        this.paths=new ArrayList<>();
        this.strings=new ArrayList<>();
        this.howmany=0;
        this.state=false;
    }
    
    /**
    * L'algoritmo, in base a se gli son stati passati dei path o delle stringhe,
    * decide se deve leggere prima dei file o meno, per poi effettuare
    * le operazioni necessarie allo svolgimento del 
    * Term Frequency - Inverse Document Frequency
    * 
    * La parte "Term Frequency" è svolta dal WordExtractor, mentre la parte
    * "Inverse Document Frequency" è svolta direttamente in questo metodo,
    * dal momento che esegue un'analisi completa sulle analisi effettuate
    * dal WordExtractor, mettendole insieme
    * 
    * @return  WordBox contenente le parole estratte e contate dall'algoritmo
    * @see  WordExtractor
    */
    public WordBox doAnalysis()
    {
        if(state==false)
        {
            ArrayList<WordBox> filewords = new ArrayList<>();
            char[] arrayofseparators = {'.', '\n', '\r', ',', '?', '!', ',', ';', ':', '-', '\'', ' '};
            String string;
            for(String path:this.paths)
            {
                string="";
                try{
                    string = new String(Files.readAllBytes(Paths.get(path)));
                }catch(Exception E){}
                WordExtractor parser = new WordExtractor(string, arrayofseparators);
                filewords.add(parser.extractWords());
            }
            for(WordBox wordsbox:filewords)
                this.box.mergeBoxes(wordsbox);
            if(this.howmany>0 && this.howmany<this.box.getCount()-1)
            {
                ArrayList<String> keys = this.box.getKeys();
                long currentothers = 0;
                for(int i = this.howmany; i<keys.size(); i++)
                {
                    currentothers = currentothers + this.box.getVal(keys.get(i));
                }
                this.box.addKeyVal("others", currentothers);
                for(int i = this.howmany; i<keys.size(); i++)
                {
                    this.box.removeByKey(keys.get(i));
                }
            }
        }
        else
        {
            ArrayList<WordBox> filewords = new ArrayList<>();
            char[] arrayofseparators = {'.', '\n', '\r', ',', '?', '!', ',', ';', ':', '-', '\'', ' '};
            String string;
            for(String str:this.strings)
            {
                string="";
                string = string.concat(str);
                WordExtractor parser = new WordExtractor(string, arrayofseparators);
                filewords.add(parser.extractWords());
            }
            for(WordBox wordsbox:filewords)
                this.box.mergeBoxes(wordsbox);
            if(this.howmany>0 && this.howmany<this.box.getCount()-1)
            {
                ArrayList<String> keys = this.box.getKeys();
                long currentothers = 0;
                for(int i = this.howmany; i<keys.size(); i++)
                {
                    currentothers = currentothers + this.box.getVal(keys.get(i));
                }
                this.box.addKeyVal("others", currentothers);
                for(int i = this.howmany; i<keys.size(); i++)
                {
                    this.box.removeByKey(keys.get(i));
                }
            }
        }
        return this.box;
    }
    
    /**
    * Cambia quante stringhe devono essere scritte in output, 
    * a prescindere da quale costruttore si è utilizzato
    */
    public void changeHowMany(int howmany)
    {
        this.howmany=howmany;
    }
}
