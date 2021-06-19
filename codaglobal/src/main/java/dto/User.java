package dto;

public class User {

	private Long uId;
	private String uName;
	private String uBio;
	private String uProfilePic;
	private String password;
	
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuBio() {
		return uBio;
	}
	public void setuBio(String uBio) {
		this.uBio = uBio;
	}
	public String getuProfilePic() {
		return uProfilePic;
	}
	public void setuProfilePic(String uProfilePic) {
		this.uProfilePic = uProfilePic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uBio=" + uBio + ", uProfilePic=" + uProfilePic
				+ ", password=" + password + "]";
	}
	
}
