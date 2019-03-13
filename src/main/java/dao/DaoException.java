package dao;

public class DaoException extends Exception {
    public DaoException(String message){
        super(message);
    }
    public DaoException(){}
    public DaoException(Throwable th){
        super(th);
    }
    public DaoException(String m, Throwable th){
        super(m,th);
    }
}
