package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {
    /*
     * Create new message
     */
    public Message insertMessage( Message text )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT into message(posted_by, message_text, time_posted_epoch) values(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, text.getPosted_by());
            ps.setString(2, text.getMessage_text());
            ps.setLong(3, text.getTime_posted_epoch());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int messageID = rs.getInt(1);
                return new Message(messageID, text.getPosted_by(), text.getMessage_text(), text.getTime_posted_epoch());
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /*
     * Get all message
     */
    public List<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> li = new ArrayList<>();
        try{
            String sql = "SELECT * from message";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Message text = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                li.add(text);
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return li;
    }

    /*
     * Get One Message Given Message Id
     */
    public Message getMessageById( int id )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                Message text = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return text;
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
     * Delete a Message Given Message Id
     */
    public void deleteMessageById( int id )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    

    /*
     * Update Message Given Message Id
     */
    public void updateMessageById( int id, Message text )
    {
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql1 = "UPDATE message set message_text = ? where message_id = ?";
            PreparedStatement ps1 = con.prepareStatement(sql1);

            ps1.setString(1, text.getMessage_text());
            ps1.setInt(2, id);
            ps1.executeUpdate();

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /*
     * Get All Messages From User Given Account Id
     */
    public List<Message> getAllMessagesByAccId( int id ){
        Connection con = ConnectionUtil.getConnection();
        List<Message> li = new ArrayList<>();
        try{
            String sql = "SELECT * from message where posted_by = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Message text = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                li.add(text);
            }

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return li;
    }
}
