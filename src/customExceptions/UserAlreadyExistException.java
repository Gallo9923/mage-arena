package customExceptions;

public class UserAlreadyExistException extends Exception{

	private static final long serialVersionUID = 2922581018898341797L;
	String username;
	
	public UserAlreadyExistException(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	
}
