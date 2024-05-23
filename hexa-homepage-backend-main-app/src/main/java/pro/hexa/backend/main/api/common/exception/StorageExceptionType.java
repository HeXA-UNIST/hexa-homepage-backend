package pro.hexa.backend.main.api.common.exception;

public enum StorageExceptionType {
    ILLIGAL_PATH("해당 경로엔 파일을 저장할 수 없습니다.");

    private final String message;

    StorageExceptionType(String message) {
        this.message=message;
    }

    public String getMessage() {
        return this.message;
    }

}
