package customExceptions;

public class UserAlreadyExistException extends Exception{

	String username;
	
	public UserAlreadyExistException(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	
}
