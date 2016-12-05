package zad.one;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Kanes on 29.11.2016.
 */
public class Constitution implements IConstitution {
    //filepath to the constitution.txt
    private List<Chapter> chapters = new LinkedList<>();               //List of chapters of constitution
    private String preamble = "";                                                 //Text of preambule
    private int amountOfChapters;

    public Constitution(Path filepath, Set preambleSet) {   //filepath - path to a txt file with constitution, preambuleSet - Set with no. of lines of preambule (counting from 0)
        Parser parser = new Parser(filepath);
        parser.parse(preambleSet);
    }


    private void addChapter(Chapter chapter) {
    }

    private Chapter getChapter(int chapterNo) {
        return new Chapter(12, "", new LinkedList<Article>());
    }

    private Article getArticle(int chapterNo, int articleNo) {
        return new Article(12, "S");
    }


    public void display(int startArticle, int endArticle) {
    }

    public void display(int articleNo) {
    }

    public void displayChapter(int chapterNo) {
    }








    private class Parser {
        private FileReader reader;
        private Path filepath;
        private List<String> lines = new LinkedList<>();
        private Set preambleSet;



        public Parser(Path filepath) {
            this.filepath = filepath;
        }

        public void parse(Set preambleSet) {
            this.preambleSet = preambleSet;
            openConstitutionFile();
            clearNotNeededLines();
            concatenateWordsMovedToNextLine();
            devideConstitution();
        }




        private void openConstitutionFile() {
            try {
                lines = Files.lines(filepath).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void clearNotNeededLines() {
            List<String> tmp = new LinkedList<>();

            for (int i = 0; i < lines.size(); i++) {
                String l = lines.get(i);
                if (!l.equals("©Kancelaria Sejmu") && !l.equals("2009-11-16")) {
                    tmp.add(l);
                }else{
                    updatePreambleSet(i);
                }
            }
            lines = tmp;
        }

        private void concatenateWordsMovedToNextLine() {
            List<String> tmp = new LinkedList<>();

            boolean toPass = false;             //czy omijamy wstawianie nastepnej linii

            for(int i = 0; i < lines.size()-1; i++){

                String nextLine = lines.get(i+1);
                String currentLine = lines.get(i);

                if(currentLine.endsWith("-")){

                    String []toReplace = nextLine.split(" ", 2);
                    currentLine = currentLine.substring(0, currentLine.length() - 1);
                    tmp.add("" + currentLine + toReplace[0]);
                    if(toReplace.length == 2) {
                        tmp.add(toReplace[1]);
                        toPass = true;              //dodajemy już przyszłą linię, więc w przyszłej iteracji omijamy wstawianie
                    }
                    else {
                        updatePreambleSet(i + 1);       //jeżeli połączyliśmy końcówkę słowa, które było jedynym w linii, to znaczy, że usuwamy linię i+1 - szą
                    }
                }else{
                    if(!toPass) tmp.add(currentLine);
                    toPass = false;
                }
            }

            lines = tmp;
        }

        private void updatePreambleSet(int x){      //updates preambleSet after removng x-th line in document
            if (preambleSet.isBetween(x)) {
                preambleSet.decY();                 //jesli przenieslismy jedno slwo z nastepnej linii, traicmy jedna linie
            }
            else if(preambleSet.getY() > x){
                preambleSet.decY();
                preambleSet.decX();
            }
        }

        private void devideConstitution() {
            for(int i = preambleSet.getX(); i <= preambleSet.getY(); i++)
                preamble = preamble.concat(lines.get(i) + " ");


        }
    }
}


