package zad.one;

/**
 * Created by Kanes on 29.11.2016.
 */
public class Article implements IArticle {
    String articleText;
    private int articleNo;

    public Article(int articleNo, String text){
        this.articleText = text;
        this.articleNo = articleNo;
    }

    public int getArticleNo(){return articleNo;}


    @Override
    public String toString(){return new String("");}
}
