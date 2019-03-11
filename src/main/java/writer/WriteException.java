package writer;

public class WriteException extends Exception {
    public WriteException(String message){
        super(message);
    }
    public WriteException(){}
    public WriteException(Throwable th){
        super(th);
    }
    public WriteException(String m, Throwable th){
        super(m,th);
    }
}
