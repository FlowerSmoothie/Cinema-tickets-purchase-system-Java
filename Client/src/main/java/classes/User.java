package classes;

import enums.Roles;
import enums.UserStatuses;

import static enums.UserStatuses.*;

public class User
{

    protected AccountData data;
    protected UserStatuses status;
    protected Roles role;

    protected int id;

    public User() { }
    public User(AccountData data, UserStatuses status, Roles role)
    {
        this.data = data;
        this.status = status;
        this.role = role;
    }

    public void setData(AccountData data) { this.data = data; }
    public void setData(String login, String password)
    {
        this.data.setLogin(login);
        this.data.setPassword(password);
    }
    public AccountData getData() { return data; }

    public void setStatus(UserStatuses status) { this.status = status; }
    public UserStatuses getStatus() { return status; }
    public void setStatus(int status)
    {
        switch(status)
        {
            case 1: this.status = BANNED; break;
            case 0: this.status = UNBANNED; break;
            case -1: this.status = DEACTIVATED; break;
        }
    }

    public void setRole(Roles role) { this.role = role; }

    public void setLogin(String login) { this.data.setLogin(login); }
    public void setPassword(String password) { this.data.setPassword(password); }
    public void setRole(int role)
    {
        switch (role)
        {
            case 0:
                this.role = Roles.USER;
                break;
            case 1:
                this.role = Roles.ADMIN;
                break;
            case 2:
                this.role = Roles.SEANCE_MANAGER;
                break;
            case 3:
                this.role = Roles.CONTENT_MANAGER;
                break;
        }
    }
    public Roles getRole() { return role; }

    public void setID(int id) { this.id = id; }
    public int getID() { return id; }

    public void logIn()
    {

    }

    public void logOut()
    {

    }

    public String goToMenu() { return "MenuUser.fxml"; }

    public static User createUser(entities.User u)
    {
        User user = new User();
        switch (u.getRole())
        {
            case 0 -> user = new User();
            case 1 -> user = new Administrator();
            case 2 -> user = new SeanceManager();
            case 3 -> user = new ContentManager();
        }
        user.id = u.getID();
        user.data = new AccountData();
        user.setData(u.getLogin(), u.getPassword());
        user.setRole(u.getRole());
        user.setStatus(u.getStatus());
        System.out.println(u.getRole());
        return user;
    }

}
