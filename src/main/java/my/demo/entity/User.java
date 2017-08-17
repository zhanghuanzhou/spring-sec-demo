package my.demo.entity;

import javax.persistence.*;

/**
 * Created by Ash on 2015/5/22.
 */
@Entity
@Table(name = "user")
public class User extends StatefulEntity {
	
    /**
	 * SID
	 */
	private static final long serialVersionUID = 5958153018639238837L;

	/**
     * 用户主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", length = 100)
    private String userName;

    /**
     * 登录密码
     */
    @Column(name = "password", length = 100)
    private String password;

    /**
     * 加密密钥
     */
    @Column(name = "password_salt", length = 100)
    private String passwordSalt;

	/**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
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
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", passwordSalt='" + passwordSalt + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
