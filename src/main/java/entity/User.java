package entity;

import javax.persistence.*;

@Entity
@Table(name = "user_entity")
public class User {

	@Id
	private String id_user;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String email;

	@Column
	private String type;


	public User() {
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id) {
		this.id_user = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
