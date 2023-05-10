package starter.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserObject {
    String ID;
    String fullName;
    String email;
    String password;

    public String getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }
    @JsonProperty("Fullname")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    @JsonProperty("Email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    @JsonProperty("Password")
    public void setPassword(String password) {
        this.password = password;
    }
}
