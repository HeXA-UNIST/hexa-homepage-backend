package pro.hexa.backend.main.api.common.exception;

public class StorageException extends RuntimeException{

    public StorageException(StorageExceptionType storageExceptionType){
        super(storageExceptionType.getMessage());
    }

}
