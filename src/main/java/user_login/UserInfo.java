package user_login;

public class UserInfo {
	private String name;
	private String password;
	private boolean oi = true;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getOi() {
		return oi;
	}
	public void setOi(boolean oi) {
		this.oi = oi;
	}

}
