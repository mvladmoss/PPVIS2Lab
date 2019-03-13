package dao;

public class ParseException extends Exception {
    public ParseException(String message){
        super(message);
    }
    public ParseException(){}
    public ParseException(Throwable th){
        super(th);
    }
    public ParseException(String m, Throwable th){
        super(m,th);
    }
}
