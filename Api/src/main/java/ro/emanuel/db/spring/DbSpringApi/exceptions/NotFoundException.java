package ro.emanuel.db.spring.DbSpringApi.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
