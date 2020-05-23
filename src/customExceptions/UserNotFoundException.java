package customExceptions;

public class UserNotFoundException extends Exception{

	String username;
	
	public UserNotFoundException(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
