package zad.one.Exceptions;

/**
 * Created by Kanes on 08.12.2016.
 */
public class ChapterAlreadyExistsException extends Exception {

        public ChapterAlreadyExistsException(String message){
            super(message);
        }

        public ChapterAlreadyExistsException(Throwable cause){
            super(cause);
        }

}
