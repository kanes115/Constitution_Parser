package zad.one;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kanes on 29.11.2016.
 * TODO:
 * -Exceptions
 * -Tests
 */
public class Chapter implements IChapter {
    private List<Article> articles = new LinkedList<>();
    private String chapterName;
    private int chapterNo;
    private final Set articlesRange;

    public Chapter(int chapterNo, String name, List<Article> articles){
        this.chapterName = name;
        this.articles = articles;
        this.chapterNo = chapterNo;
        articlesRange = setArtRange();
    }

    private Set setArtRange(){
        int artNoMin = Integer.MAX_VALUE, artNoMax = Integer.MIN_VALUE;

        for(Article art : articles){
            if(art.getArticleNo()<artNoMin) artNoMin = art.getArticleNo();
            if(art.getArticleNo()>artNoMax) artNoMax = art.getArticleNo();
        }
        return new Set(artNoMin, artNoMax);
    }

    public void addArticle(Article article){}

    public Article getArticle(int articleNo){
        return articles.get(articleNo - articlesRange.getX());
    }

    public Article getArticleRelevant(int relevantNo){
        return articles.get(relevantNo - 1);
    }

    public int getChapterNo(){return chapterNo;}

    public String getChapterName(){return chapterName;}

    public boolean containsArticle(int article){
        return articlesRange.isBetween(article);
    }

    public Article getFirstArticle(){
        return getArticle(articlesRange.getX());
    }

    public Article getLastArticle(){
        return getArticle(articlesRange.getY());
    }

    @Override
    public String toString(){
        String toRet = "--- Rozdział " + chapterNo + " ---" + '\n';
        toRet = toRet.concat("Tytuł: " + chapterName.toLowerCase() + '\n' + "Treść:" + '\n');
        for(Article art : articles)
            toRet = toRet.concat(art.toString());
        return toRet;
    }

    public String toStringArticlesRange(int art1, int art2){
        String toRet = "--- Rozdział " + chapterNo + " ---" + '\n';
        toRet = toRet.concat("Tytuł: " + chapterName.toLowerCase() + '\n' + "Treść:" + '\n');

        for(int i = art1; i <= art2; i++){
            toRet = toRet.concat(getArticle(i).toString());
        }
        return toRet;
    }
}
