package cn.bt.selftag;

/**
 * @Author: zhouqian
 * @Date: 2020/11/26 15:37
 */
public class User {
	private String username;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
