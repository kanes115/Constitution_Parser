package zad.one.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import zad.one.Constitution;
import zad.one.Exceptions.ConstitutionException;
import zad.one.Set;

import java.nio.file.Paths;


/**
 * Created by Kanes on 08.12.2016.
 */
public class ConstitutionTest {
    @Test
    public void testExceptionWhenFileNotFound() throws Exception {
        boolean thrown = false;
        try{
            Constitution myCons = new Constitution(Paths.get("tasciezkanapewnonieistnieje"), new Set(1, 2));
        }catch(ConstitutionException e){
            thrown = true;
        }
        Assert.assertEquals(thrown, true);
    }

    @Test
    public void testNoException() throws Exception {
        boolean thrown = false;
        try{
            Constitution myCons = new Constitution(Paths.get("constitution.txt"), new Set(1, 2));
        }catch(ConstitutionException e){
            thrown = true;
        }
        Assert.assertEquals(thrown, false);
    }

    @Test
    public void display1() throws Exception {
        Constitution myCons = new Constitution(Paths.get("constitution.txt"), new Set(1, 31));  //constitution.txt to plik z konstyucją Polski

        Assert.assertNotNull(myCons.display(9));
        Assert.assertNotNull(myCons.display(19));
        Assert.assertNotNull(myCons.display(1));
        Assert.assertNotNull(myCons.display(2));
        Assert.assertNotNull(myCons.display(50));

        boolean thrown = false;

        try {
            myCons.display(Integer.MAX_VALUE);
            myCons.display(-1);
        }catch(ConstitutionException e){
            thrown = true;
        }

        try {
            myCons.display(-1029);
            myCons.display(-20);
        }catch(ConstitutionException e){
            thrown = true;
        }

        Assert.assertEquals(thrown, true);

    }

    @Test
    public void display2() throws Exception {
        Constitution myCons = new Constitution(Paths.get("constitution.txt"), new Set(1, 31));  //constitution.txt to plik z konstyucją Polski

        Assert.assertNotNull(myCons.display(1, 9));
        Assert.assertNotNull(myCons.display(5, 19));
        Assert.assertNotNull(myCons.display(1, 50));
        Assert.assertNotNull(myCons.display(2, 2));
        Assert.assertNotNull(myCons.display(5, 5));

        boolean thrown = false;

        try {
            myCons.display(Integer.MIN_VALUE, Integer.MAX_VALUE);
            myCons.display(-1, 1);
        }catch(ConstitutionException e){
            thrown = true;
        }

        try {
            myCons.display(-1029, 1029);
            myCons.display(-20, -20);
        }catch(ConstitutionException e){
            thrown = true;
        }

        Assert.assertEquals(thrown, true);

    }

    @Test
    public void displayPreamble() throws Exception {
        Constitution myCons = new Constitution(Paths.get("constitution.txt"), new Set(1, 31));  //constitution.txt to plik z konstyucją Polski

        Assert.assertNotNull(myCons.displayPreamble());
    }

    @Test
    public void displayChapter() throws Exception {

    }

}