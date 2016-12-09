package zad.one;

import javafx.scene.shape.Path;
import zad.one.Exceptions.ArgumentParserException;
import zad.one.Exceptions.ConstitutionException;

import java.nio.file.Paths;
//import java.nio.file.Path;

/**
 * Parser accepts flags:
 * One of these:
 * -a   display article
 * -ch  display chapter
 * -ar  display range of articles
 * And in any of these cases optional:
 * -rw  allow to specify recognition words
 *
 * After the flag you give arguments to be given to constructor and display method:
 * -a: (String) filepath (Int) first line of preable (Int) last line of preamble (Int) article no
 * -ch: (String) filepath (Int) first line of preable (Int) last line of preamble (Int) chapter no
 * -ar: (String) filepath (Int) first line of preable (Int) last line of preamble (Int) starting article no (Int) ending article no
 * When using -rw, at the end specify (String) chapter recognition word (String) article recognition word
 *
 */
public class ArgumentsParser {

    private String firstFlag;
    private String secondFlag;

    java.nio.file.Path filepath;
    private int firstLineOfPreamble;
    private int lastLineOfPreamble;

    private String result;


    public void parse(String []args) throws ArgumentParserException {
        lookForFlags(args);

        int shift = 0;
        if(secondFlag.equals("none")) shift = 0;
        else if(secondFlag.equals("-rw")) shift = 1;


        filepath = Paths.get(args[1 + shift]);
        try{
            firstLineOfPreamble = Integer.parseInt(args[2 + shift]);
            lastLineOfPreamble = Integer.parseInt(args[3 + shift]);
            Constitution myCons = new Constitution(filepath, new Set(firstLineOfPreamble, lastLineOfPreamble));

            if(firstFlag.equals("-a")){
                result = myCons.display(Integer.parseInt(args[4 + shift]));
            }
            else if(firstFlag.equals("-ch")){
                result = myCons.displayChapter(Integer.parseInt(args[4 + shift]));
            }
            else if(firstFlag.equals("-ar")){
                result = myCons.display(Integer.parseInt(args[4 + shift]), Integer.parseInt(args[5 + shift]));
            }else{throw new ArgumentParserException("Error while specifying flags.");}

        }catch(NumberFormatException | ConstitutionException e){
            throw new ArgumentParserException("Arguments parser error!" + '\n' + e.getMessage());
        }
    }

    public String getResult(){return result;}

    private void lookForFlags(String []args) throws ArgumentParserException {
        if(args.length < 4) throw new ArgumentParserException("To few arguments given");

        if(args[0].equals("-a") || args[0].equals("-ch") || args[0].equals("-ar"))
            firstFlag = args[0];
        else
            throw new ArgumentParserException("First argument must be one of these flags: -a -ch -ar.");
        if(args[1].equals("-rw"))
            secondFlag = args[1];
        else secondFlag = "none";

    }

    //zwraca wynik wywołania odpowiedniego displaya na konstytucji
}
