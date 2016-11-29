package zad.one;

/**
 * Created by Kanes on 29.11.2016.
 */
public interface IChapter {

   void addArticle(Article article);

    Article getArticle(int articleNo);

    int getChapterNo();

    @Override
    String toString();

}
