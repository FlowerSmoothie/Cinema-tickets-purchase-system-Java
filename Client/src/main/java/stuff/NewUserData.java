package stuff;

import java.io.Serializable;

public class NewUserData implements Serializable
{

    String login;
    String password;
    String newData;

    public NewUserData()
    {

    }

    public NewUserData(String login, String password, String newData)
    {
        this.login = login;
        this.password = password;
        this.newData = newData;
    }

    public void setLogin(String login) { this.login = login; }
    public String getLogin() { return login; }

    public void setPassword(String password) { this.login = login; }
    public String getPassword() { return password; }

    public void setNewDate(String newData) { this.newData = newData; }
    public String getNewData() { return newData; }

}
