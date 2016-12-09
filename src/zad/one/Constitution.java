package zad.one;

import zad.one.Exceptions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class Constitution
 * It can store a document of a certain build.
 * 1. It has to have preamble that ends before Chapters and articles begin to appear.
 * 2. It has to be divided into Chapters, Chapters into Articles.
 * 3. Chapters has to start with word "Rozdział" and this word should be
 *      followed by a romanian number (after a space) and Articles should start with "Art." be followed by a number
 *      (after space) and a number be followed by a dot. (In default mode)
 * 4. You can use your own recognition words for Chapters and Articles. Use Constitution(Path, Set, String, String); constructor.
 * 5. File should not end with line containing any of the recognition words
 *
 * @param filepath - filepath to the constitution file
 *
 * @param preambleSet - set of integers describing lines of preamble
 *
 * @param chapterRecognitionWord - string which tells us that a new chapter begins
 * @param articleRecognitionWord - string which tells us that a new article begins
 *
 */
public class Constitution implements IConstitution {

    private List<Chapter> chapters = new LinkedList<>();
    private String preamble = "";

    public Constitution(Path filepath, Set preambleSet, String chapterRecognitionword, String articleRecognitionWord) throws ConstitutionException{   //filepath - path to a txt file with constitution, preambuleSet - Set with no. of lines of preambule (counting from 0)
        Parser parser = new Parser(filepath);
        try{
            parser.parse(preambleSet, chapterRecognitionword, articleRecognitionWord);
        }catch(ConstitutionParserException e){
            throw new ConstitutionException("Error while creating constitution from a file." + '\n' + e.getMessage());
        }
    }

    public Constitution(Path filepath, Set preambleSet) throws ConstitutionException {   //filepath - path to a txt file with constitution, preambuleSet - Set with no. of lines of preambule (counting from 0)
        Parser parser = new Parser(filepath);
        try {
            parser.parse(preambleSet);
        }catch(ConstitutionParserException e){
            throw new ConstitutionException("Error while creating constitution from a file." + '\n' + e.getMessage());
        }
    }



    private void addChapter(Chapter chapter) throws ChapterAlreadyExistsException{
        if(!chapters.contains(chapter)){
            chapters.add(chapter);
        }else{
            throw new ChapterAlreadyExistsException("Tried to add chapter with the same number.");
        }
    }

    private Chapter getChapter(int chapterNo) throws ChapterDoesNotExistException{
        try {
            return chapters.get(chapterNo - 1);
        }catch(IndexOutOfBoundsException e){
            throw new ChapterDoesNotExistException("Error while trying to get a chapter with a chapter no: " + chapterNo +'\n');
        }
    }

    private Chapter findChapter(int article) throws ArticlesNotInBoundException {
        for(Chapter ch : chapters){
            if(ch.containsArticle(article)){
                return ch;
            }
        }
        throw new ArticlesNotInBoundException("Error while looking for chapter for an article. Not in range.");
    }

    public String display(int startArticle, int endArticle) throws ConstitutionException{
        try {
            Chapter startingChap = findChapter(startArticle);
            Chapter endingChap = findChapter(endArticle);

            if (startingChap.getChapterNo() == endingChap.getChapterNo()) {
                return startingChap.toStringArticlesRange(startArticle, endArticle);
            }

            String toRet = startingChap.toStringArticlesRange(startArticle, startingChap.getLastArticle().getArticleNo());

            for (int i = startingChap.getChapterNo() + 1; i < endingChap.getChapterNo(); i++) {
                toRet = toRet.concat(getChapter(i).toString());
            }

            toRet = toRet.concat(endingChap.toStringArticlesRange(endingChap.getFirstArticle().getArticleNo(), endArticle));
            return toRet;
        }catch(ArticlesNotInBoundException | ChapterDoesNotExistException e){
            throw new ConstitutionException("Error while looking for articles to display them." + '\n' + e.getMessage());
        }
    }

    public String display(int articleNo) throws ConstitutionException {
        try {
            Chapter ch = findChapter(articleNo);
            return ch.getArticle(articleNo).toString();
        }catch(ArticlesNotInBoundException e){
            throw new ConstitutionException("Error while looking for an article to display." + '\n' + e.getMessage());
        }
    }

    public String displayPreamble(){
        return preamble;
    }

    public String displayChapter(int chapterNo) throws ConstitutionException {
        try{
            return getChapter(chapterNo).toString();
        }catch(ChapterDoesNotExistException e){
            throw new ConstitutionException("Error while trying to display a chapter no: " + chapterNo + '\n' + e.getMessage());
        }
    }








    private class Parser {
        private FileReader reader;
        private Path filepath;
        private List<String> lines = new LinkedList<>();
        private Set preambleSet;

        private String chapterRecognitionWord;
        private String articleRecognitionWord;



        public Parser(Path filepath) {
            this.filepath = filepath;
        }

        public void parse(Set preambleSet) throws ConstitutionParserException{
            chapterRecognitionWord = "Rozdział";
            articleRecognitionWord = "Art.";
            this.preambleSet = preambleSet;
            try {
                parsingSequenceOfActions();
            }catch(ConstitutionParserException e){
                throw new ConstitutionParserException(e.getMessage());
            }
        }

        public void parse(Set preambleSet, String chapterRecognitionword, String articleRecognitionWord) throws ConstitutionParserException {
            this.chapterRecognitionWord = chapterRecognitionword;
            this.articleRecognitionWord = articleRecognitionWord;
            this.preambleSet = preambleSet;
            try {
                parsingSequenceOfActions();
            }catch(ConstitutionParserException e){
                throw new ConstitutionParserException(e.getMessage());
            }
        }



        private void parsingSequenceOfActions() throws ConstitutionParserException{
            try {
                openConstitutionFile();
                clearNotNeededLines();
                concatenateWordsMovedToNextLine();
                validateEndingLine();
                divideConstitution();
            }catch(FileNotFoundException | DividingConstitutionException | RecognitionWordException e){
                throw new ConstitutionParserException("Error while parsing the file." + '\n' + e.getMessage());
            }
        }

        private void validateEndingLine() throws RecognitionWordException {
            String lastline = lines.get(lines.size() - 1);
            if(lastline.startsWith(articleRecognitionWord) || lastline.startsWith(chapterRecognitionWord))
                throw new RecognitionWordException("Error, last line cannot start with a recognition word.");
        }


        private void openConstitutionFile() throws FileNotFoundException{
            try(Stream<String> s = Files.lines(filepath)){
                lines = s.collect(Collectors.toList());
            } catch (IOException e) {
                throw new FileNotFoundException("File not found. Specify the path to the file correctly. This path is incorrect: " + filepath);
            }
        }

        private void clearNotNeededLines() {
            List<String> tmp = new LinkedList<>();

            lines = lines.stream().
                    filter(e -> !(e.equals("©Kancelaria Sejmu") || e.equals("2009-11-16"))).
                    collect(Collectors.toList());

//            for (int i = 0; i < lines.size(); i++) {
//                String l = lines.get(i);
//                if (!l.equals("©Kancelaria Sejmu") && !l.equals("2009-11-16")) {
//                    tmp.add(l);
//                }else{
//                    updatePreambleSet(i);
//                }
//            }
//            lines = tmp;
        }

        private void concatenateWordsMovedToNextLine() {
            List<String> tmp = new LinkedList<>();

            boolean toPass = false;             //czy omijamy wstawianie nastepnej linii
            int last = 0;

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
                        toPass = true;
                    }
                }else{
                    if(!toPass) tmp.add(currentLine);
                    toPass = false;
                }
                last = i+1;
            }
            tmp.add(lines.get(last));
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

        private boolean endsWithRomanianNumber(String test){
            return test.endsWith("I") || test.endsWith("X") || test.endsWith("V")
                    || test.endsWith("L") || test.endsWith("M") || test.endsWith("C") || test.endsWith("D");
        }

        private String getNameOfChapter(int lineOfChapterNo){
            //param - numer linii zawierajacej slowo "Rozdział" + numer rozdziału
            //zakładamy, że tytuły rozdziałów są wielkimi literami i rozdział zaczyna się od "Art."
            String toRet = "";
            int i = lineOfChapterNo;
            i++;
            while(!lines.get(i).startsWith(articleRecognitionWord)){
                toRet = toRet.concat(lines.get(i) + '\n');
                i++;
            }
            return toRet;
        }

        private LinkedList<Article> getArtListForChapter(int lineOfChapterNo){

            LinkedList<Article> toRet = new LinkedList<>();
            int i = lineOfChapterNo;
            while(!lines.get(i).startsWith(articleRecognitionWord))     //przechodzimy przez tytuł
                i++;

           while(true) {
               String currentLine = lines.get(i);

               String sub = currentLine.substring(5, currentLine.length() - 1);

               int artNo = Integer.parseInt(sub);
               String text = "";
               i++;
               while (i != lines.size() - 1 && !lines.get(i).startsWith(articleRecognitionWord) && !lines.get(i).startsWith(chapterRecognitionWord)) {  //dopóki nie dojdziemy do konca rtykulu
                   String actLine = lines.get(i);
                   text = text.concat(actLine + '\n');
                   i++;
               }
               if(i == lines.size() - 1){
                   text = text.concat(lines.get(i));
               }

               toRet.add(new Article(artNo, text));

               if (i == lines.size() - 1){  //sprawdzamy, czy to nie koniec artykulu
                   return toRet;
               }
               if (lines.get(i).startsWith(chapterRecognitionWord)) return toRet;
           }


        }

        private void divideConstitution() throws DividingConstitutionException {
            try {
                for (int i = preambleSet.getX(); i <= preambleSet.getY(); i++)
                    preamble = preamble.concat(lines.get(i) + '\n');
            }catch(IndexOutOfBoundsException e){
                throw new DividingConstitutionException("Error while creating preamble.");
            }
            // znajdowanie kolejnych chapterów
            int i = preambleSet.getY();
            int currentChapterNo = 1;
            while(i<lines.size()){
                String currentLine = lines.get(i);
                if(currentLine.startsWith(chapterRecognitionWord) && endsWithRomanianNumber(currentLine)){
                    String name = getNameOfChapter(i);
                    List<Article> toAddArt = getArtListForChapter(i);

                    try {
                        addChapter(new Chapter(currentChapterNo, name, toAddArt));
                    }catch(ChapterAlreadyExistsException e){
                        System.out.println("Error while dividing constitution into chapters. "+ e.getMessage());
                    }
                    currentChapterNo++;
                }
                i++;
            }

        }
    }
}


