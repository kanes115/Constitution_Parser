package zad.one;

import zad.one.Exceptions.ConstitutionException;

/**
 * Created by Kanes on 29.11.2016.
 */
public interface IConstitution {

    String display(int startArticle, int endArticle) throws ConstitutionException;

    String display(int articleNo) throws ConstitutionException;

    String displayChapter(int chapterNo) throws ConstitutionException;

    String displayPreamble();
}
