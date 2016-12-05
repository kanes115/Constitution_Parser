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
    private String preamble;                                                 //Text of preambule
    private int amountOfChapters;

    public Constitution(Path filepath) {
        Parser parser = new Parser(filepath);
        parser.parse();
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

        public Parser(Path filepath) {
            this.filepath = filepath;
        }

        public void parse() {
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

            for (String l : lines) {
                if (!(l.equals("©Kancelaria Sejmu") || l.equals("2009-11-16"))) {    //todo nie usuwac linii, tylko zwracać listę linii, które zostawiamy i podmieni
                    tmp.add(l);
                }
            }
            lines = tmp;
        }

        private void concatenateWordsMovedToNextLine() {
            int slen = lines.size();
            List<String> tmp = new LinkedList<>();

            for(int i = 0; i < slen; i++){
                if(lines.get(i).endsWith("-")){
                    //if(lines.size() == i+1) throw new Exception("Documents ends with \"-\", but it shouldn't");
                    String nextLine = lines.get(i+1);
                    String currentLine = lines.get(i);

                    String []toReplace = nextLine.split(" ", 2);
                    currentLine = currentLine.substring(0, currentLine.length() - 2);
                    tmp.add("" + currentLine + toReplace[0]);
                    tmp.add(toReplace[1]);
                }else{
                    tmp.add(lines.get(i));
                }
            }

            lines = tmp;
            System.out.println(lines.get(12).toString());
            System.out.println(lines.get(13).toString());
            System.out.println(lines.get(14).toString());
        }

        private void devideConstitution() {
        }
    }
}


