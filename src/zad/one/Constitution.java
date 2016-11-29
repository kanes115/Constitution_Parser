package zad.one;

import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kanes on 29.11.2016.
 */
public class Constitution implements IConstitution{
    private FilePath filepath;                                                    //filepath to the constitution.txt
    private FileReader reader;
    private Map<Integer, Chapter> chapters = new HashMap<Integer, Chapter>();     //List of chapters of constitution
    private String preamble;                                              //Text of preambule
    private int amountOfChapters;

    public Constitution(FilePath filepath){
        this.filepath = filepath;
        openConstitutionFile();
        clearNotNeededLines();
        concatenateWordsMovedToNextLine();
        devideConstitution();
    }

    private void openConstitutionFile(){}

    private void clearNotNeededLines(){}

    private void concatenateWordsMovedToNextLine(){}

    private void devideConstitution(){}

    private void addChapter(Chapter chapter){}

    private Chapter getChapter(int chapterNo){}

    private Article getArticle(int chapterNo, int articleNo){}



    public void display(int startArticle, int endArticle){}

    public void display(int articleNo){}

    public void displayChapter(int chapterNo){}




}

