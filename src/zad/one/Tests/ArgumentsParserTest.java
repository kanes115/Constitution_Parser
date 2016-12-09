package zad.one.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import zad.one.ArgumentsParser;
import zad.one.Constitution;
import zad.one.Exceptions.ArgumentParserException;
import zad.one.Exceptions.ConstitutionException;
import zad.one.Set;

import java.nio.file.Paths;

/**
 * Created by Kanes on 09.12.2016.
 */
public class ArgumentsParserTest {
    @Test
    public void parse() throws Exception {
        ArgumentsParser parser = new ArgumentsParser();
        String []args = {"-ch", "constitution.txt", "1", "31", "5"};
        parser.parse(args);
        Assert.assertNotNull(parser.getResult());

        ArgumentsParser parser2 = new ArgumentsParser();
        String []args2 = {"-ch", "-rw", "constitution.txt", "1", "31", "5", "Rozdział", "Art."};
        parser2.parse(args2);
        Assert.assertNotNull(parser.getResult());
    }

    @Test
    public void testExceptions() throws Exception {
        ArgumentsParser parser = new ArgumentsParser();
        String []args = {"-ch", "constitution.txt", "1", "31", "5"};
        boolean thrown = false;
        try {
            parser.parse(args);
        }catch(ArgumentParserException e){
            thrown = true;
        }
        Assert.assertEquals(thrown, false);
        thrown = false;
        String []args2 = {"-chh","constitution.txt", "1", "31", "5" };
        try {
            parser.parse(args2);
        }catch(ArgumentParserException e){
            thrown = true;
        }
        Assert.assertEquals(thrown, true);

    }

}