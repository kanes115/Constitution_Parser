package zad.one.Exceptions;

/**
 * Created by Kanes on 08.12.2016.
 */
public class ConstitutionParserException extends Exception {

    public ConstitutionParserException(String message){
        super(message);
    }

    public ConstitutionParserException(Throwable cause){
        super(cause);
    }
}
