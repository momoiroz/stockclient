
package control;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegRespond {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("token")
    @Expose
    private String token;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
