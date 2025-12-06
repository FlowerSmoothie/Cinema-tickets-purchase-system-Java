package services;

import dao.UserDAO;
import entities.User;
import enums.UserStatuses;
import stuff.NewUserData;

import java.util.List;

public class UserService extends Service
{

    public UserService() { dao = new UserDAO(); }

    @Override
    public User findEntity(int id) {
        return (User)dao.findById(id);
    }

    @Override
    public List<User> findAllEntities() {
        return dao.findAll();
    }

    public Boolean ifTheresUserWithSameLogin(String login)
    {
        List<User> users = findAllEntities();
        Boolean flag = false;
        for (User u: users)
        {
            if(login.equals(u.getLogin()))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public User isLoginAndPasswordEqual(String login, String password)
    {
        List<User> users = findAllEntities();
        User toReturn = null;
        for(User u: users)
        {
            if(login.equals(u.getLogin()) && password.equals(u.getPassword()))
            {
                toReturn = u;
                break;
            }
        }
        return toReturn;
    }

    public int changeLogin(NewUserData userData)
    {
        List<User> users = findAllEntities();
        String newLogin = userData.getNewData();
        String oldLogin = userData.getLogin();
        User user = null;
        for(User u: users)
        {
            if(newLogin.equals(u.getLogin())) return 1;
            if(oldLogin.equals(u.getLogin())) user = u;
        }
        user.setLogin(newLogin);
        dao.update(user);
        return 0;
    }

    public int changePassword(NewUserData userData)
    {
        List<User> users = findAllEntities();
        for(User u: users)
        {
            if(userData.getLogin().equals(u.getLogin()))
            {
                u.setPassword(userData.getNewData());
                dao.update(u);
                return 0; // success
            }
        }
        return 1; // incorrect password
    }

    public int deactivateAccount(int id)
    {

        List<User> users = findAllEntities();
        for(User u: users)
        {
            if(u.getID() == id)
            {
                u.setStatus(UserStatuses.DEACTIVATED);
                dao.update(u);
                return 0; // success
            }
        }
        return 1;
    }

    public List<User> findAllUserEntities() { return UserDAO.findAllUserEntities(); }

}
