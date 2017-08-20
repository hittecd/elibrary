import java.util.List;

public class MoveResult {

    public static final String ERROR_INVALID_TIME_MSG = "Cannot build road at this time.";
    public static final String ERROR_FAILED_TO_BUILD_MSG_2 = "Failed to build Road. Check your placement and try again.";
    public static final String ERROR_SELECT_EDGE_TO_BUILD_MSG = "Select Edge to build Road.";

    public static final String ERROR_FAILED_TO_BUILD_MSG = "Failed to build Settlement. Check your placement and try again.";
    public static final String ERROR_SELECT_CORNER_TO_BUILD_MSG = "Select Corner to build Settlement.";

    private boolean success;
    private String message;

    public MoveResult() {
        success = false;
        message = "";
    }

    public MoveResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
