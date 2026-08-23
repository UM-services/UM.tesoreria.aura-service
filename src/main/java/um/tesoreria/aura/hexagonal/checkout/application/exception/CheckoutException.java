package um.tesoreria.aura.hexagonal.checkout.application.exception;

import lombok.Getter;

@Getter
public class CheckoutException extends RuntimeException {

    private final String errorCode;
    private final int httpStatus;

    public CheckoutException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public CheckoutException(String message) {
        super(message);
        this.errorCode = "CHECKOUT_ERROR";
        this.httpStatus = 500;
    }

}
