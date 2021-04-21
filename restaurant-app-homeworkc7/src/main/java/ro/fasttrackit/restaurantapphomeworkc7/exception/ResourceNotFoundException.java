package ro.fasttrackit.restaurantapphomeworkc7.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
