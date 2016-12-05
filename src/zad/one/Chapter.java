package zad.one;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kanes on 29.11.2016.
 */
public class Chapter implements IChapter {
    private List<Article> articles = new LinkedList<>();
    private String chapterName;
    private int chapterNo;
    private int amountOfArticles;

    public Chapter(int chapterNo, String name, List<Article> articles){
        this.chapterName = name;
        this.articles = articles;
        this.chapterNo = chapterNo;
    }

    public void addArticle(Article article){}

    public Article getArticle(int articleNo){return new Article(2, "11");}

    public int getChapterNo(){return chapterNo;}

    @Override
    public String toString(){return new String("");}
}
