package game.scene.pdk.exception;

public class ChuPaiException extends Exception{
	public static final String  MSG= "pai.erroType";
	public ChuPaiException() {
		super();
	}
	public ChuPaiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public ChuPaiException(String message, Throwable cause) {
		super(message, cause);
	}
	public ChuPaiException(String message) {
		super(message);
	}
	public ChuPaiException(Throwable cause) {
		super(cause);
	}

}
