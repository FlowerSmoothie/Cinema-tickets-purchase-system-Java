package entities;

import stuff.Hash;
import enums.Roles;
import enums.UserStatuses;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

import static enums.UserStatuses.BANNED;

@Entity
@Table(name = "users")
public class User implements EntityDB, Serializable
{

    @Id
    @Column(name = "ID")
    private int ID;

    @Column(name = "Login")
    private String login;

    @Column(name = "Password")
    private String password;

    @Column(name = "Role")
    private int role;

    @Column(name = "Status")
    private int status;

    public User()
    {

    }

    public User(int ID, String login, String password, int role, int status)
    {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }


    public User(int ID, String login, String password, Roles role, UserStatuses status)
    {
        this.ID = ID;
        this.login = login;
        this.password = password;
        setRole(role);
        setStatus(status);
    }

    public void setID(int ID) { this.ID = ID; }
    public int getID() { return ID; }

    public void setLogin(String login) { this.login = login; }
    public String getLogin() { return login; }

    public void setPasswordHash(String password) { this.password = Hash.getHash(password); }
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public Boolean comparePassword(String toCompare) { return (this.password.equals(toCompare)); }
    public Boolean compareLogin(String toCompare) { return (this.login.equals(toCompare)); }

    public void setRole(int role) { this.role = role; }
    public void setRole(Roles role)
    {
        switch(role)
        {
            case USER:
                this.role = 0;
                break;
            case ADMIN:
                this.role = 1;
                break;
            case SEANCE_MANAGER:
                this.role = 2;
                break;
            case CONTENT_MANAGER:
                this.role = 3;
                break;
        }
    }
    public int getRole() { return role; }

    public void setStatus(int status) { this.status = status; }
    public void setStatus(UserStatuses status)
    {
        switch(status)
        {
            case BANNED: this.status = 1; break;
            case UNBANNED: this.status = 0; break;
            case DEACTIVATED: this.status = -1; break;
        }
    }
    public int getStatus() { return status; }

}
