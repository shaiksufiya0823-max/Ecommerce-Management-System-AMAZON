package ecommerce;

public class User {

	private String name;
	private String email;
	private String password;
	private String phone;
	private String address;

	public User(String name, String email, String password, String phone , String address) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
	}
	//seller constructor
	public User(String name, String email, String password, String phone) {
		this(name, email, password, phone, "" );
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
}