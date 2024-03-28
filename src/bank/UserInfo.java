package bank;

import java.io.Serializable;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = -4650894901546275024L;
	
	private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private String aadhaarNumber;

    public UserInfo(String name, int age, String phoneNumber, String email, String aadhaarNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.aadhaarNumber = aadhaarNumber;
    }

    // Getters and setters 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
}
