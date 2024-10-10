package org.tak.techstoreecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, String resourceName, Long id) {
        super(resource + " with " + resourceName + ": " + id + " not found");
    }

}
