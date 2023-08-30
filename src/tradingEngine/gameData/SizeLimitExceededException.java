package tradingEngine.gameData;

public class SizeLimitExceededException extends IllegalArgumentException{
    public SizeLimitExceededException() {
        super("in-game strings cannot exceed 8 characters");
    }

    public SizeLimitExceededException(String s) {
        super(s);
    }

    public SizeLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeLimitExceededException(Throwable cause) {
        super(cause);
    }
}
