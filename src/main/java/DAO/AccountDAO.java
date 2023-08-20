package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    /*
    * User Registeration
    */
    public Account insertUser( Account user )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT into account(username, password) values(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int accountID = rs.getInt(1);
                return new Account(accountID, user.getUsername(), user.getPassword());
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    * User Login
    */
    public Account loginUser ( Account user )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * from account where username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    * Check to see if username exist
    */
    public Account checkUsername ( Account user )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * from account where username = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    * Check to see if account_id exist
    */
    public Account checkAccountId ( int id )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * from account where account_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
