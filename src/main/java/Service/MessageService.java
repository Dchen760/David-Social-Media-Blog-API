package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    //default constructor
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    //constructor given an messageDao
    public MessageService( MessageDAO messageDAO )
    {
        this.messageDAO = messageDAO;
    }

    //method to create new message and add it to database
    public Message createMessage( Message text )
    {
        if(!text.getMessage_text().isEmpty() && text.getMessage_text().length() < 255 && text.getMessage_text() != null && accountDAO.checkAccountId(text.getPosted_by()) != null)
        {
            return messageDAO.insertMessage(text);
        }
        return null;
    }

    //method to get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    //method to get a message given message id
    public Message getMessageById( int id )
    {
        return messageDAO.getMessageById(id);
    }

    //method to delete a message given message id
    public Message deleteMessageById( int id )
    {
        //checks if a message by the id exist
        if( messageDAO.getMessageById(id) != null)
        {
            Message text = messageDAO.getMessageById(id);
            messageDAO.deleteMessageById(id);
            return text;
        }
        return null;
    }

    //method to update a message given message id
    public Message updateMessageById( int id, Message text )
    {
        if(messageDAO.getMessageById(id) != null && text.getMessage_text().length() < 255 && text.getMessage_text() != null && !text.getMessage_text().isEmpty())
        {
            messageDAO.updateMessageById(id, text);
            return messageDAO.getMessageById(id);
        }
        return null;
    }

    //method to get all message from a given user id
    public List<Message> getAllMessageByUserId( int id )
    {
        return messageDAO.getAllMessagesByAccId(id);
    }

}
