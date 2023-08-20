package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDao;

    //default constructor
    public AccountService(){
        accountDao = new AccountDAO();
    }

    //constructor given an accountDao
    public AccountService( AccountDAO accountDao )
    {
        this.accountDao = accountDao;
    }

    //method to register a new user to database
    public Account addUser( Account user )
    {
        //checks if username is empty/null
        if(user.getUsername() == null || user.getUsername().isEmpty())
        {
            return null;
        }
        //checks to see if username exist
        if(accountDao.checkUsername(user) != null)
        {
            return null;
        }
        //checks if password is at least 4 character long
        if(user.getPassword().length() <= 3)
        {
            return null;
        }
        return accountDao.insertUser(user);
    }

    //method to log a user if the credentials of password and user match one in the database
    public Account checkAccountCred( Account user )
    {
        if(accountDao.loginUser(user) != null)
        {
            return accountDao.loginUser(user);
        }
        else
            return null;
    }
}
