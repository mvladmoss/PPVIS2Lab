package repository;

public class RepositoryException extends Exception {
    public RepositoryException(String message){
        super(message);
    }
    public RepositoryException(){}
    public RepositoryException(Throwable th){
        super(th);
    }
    public RepositoryException(String m, Throwable th){
        super(m,th);
    }
}
