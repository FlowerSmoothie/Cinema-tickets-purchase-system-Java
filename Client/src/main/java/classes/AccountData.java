package classes;

import stuff.Constants;
import stuff.Hash;

public class AccountData implements Constants
{

    private String login;
    private String password;

    public void setLogin(String login) { this.login = login; }
    public String getLogin() { return login; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    public void setHashPassword(String password) { this.password = Hash.getHash(password); }

    public AccountData() { }
    public AccountData(String login, String password, Boolean hash)
    {
        this.login = login;
        this.password = hash ? Hash.getHash(password) : password;
    }

    private boolean isLoginLengthOkay()
    {
        return (login.length() >= Constants.minLoginLength && login.length() <= Constants.maxLoginLength);
    }

    private boolean isPasswordLengthOkay()
    {
        return (password.length() >= Constants.minPasswordLength && password.length() <= Constants.maxPasswordLength);
    }

    public boolean isFieldLengthsOkay()
    {
        return (isLoginLengthOkay() && isPasswordLengthOkay());
    }

}
