package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createNewMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account newAcc = accountService.addUser(account);
        if(newAcc != null)
        {
            ctx.json(om.writeValueAsString(newAcc));
            ctx.status(200);
        }
        else
        {
            ctx.status(400);
        }
    }

    private void loginUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account newAcc = accountService.checkAccountCred(account);
        if(newAcc != null)
        {
            ctx.json(om.writeValueAsString(newAcc));
            ctx.status(200);
        }
        else
        {
            ctx.status(401);
        }
    }

    private void createNewMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message newMess = messageService.createMessage(message);
        if(newMess != null)
        {
            ctx.json(om.writeValueAsString(newMess));
            ctx.status(200);
        }
        else
        {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx)
    {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx)
    {
        String idString = ctx.pathParam("message_id");
        int id = Integer.parseInt(idString);
        Message message = messageService.getMessageById(id);
        if(message == null)
        {
            ctx.status(200).result("");
        }
        else
        {
            ctx.json(message);
        }
    }

    private void deleteMessageHandler(Context ctx)
    {
        String idString = ctx.pathParam("message_id");
        int id = Integer.parseInt(idString);
        Message message = messageService.deleteMessageById(id);
        if(message != null)
        {
            ctx.json(message);
        }
        else
        {
            ctx.status(200).result("");
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        String idString = ctx.pathParam("message_id");
        int id = Integer.parseInt(idString);
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message update = messageService.updateMessageById(id, message);
        if(update != null)
        {
            ctx.json(om.writeValueAsString(update));
            ctx.status(200);
        }
        else
        {
            ctx.status(400);
        }
    }

    private void getMessageByAccIdHandler(Context ctx){
        String idString = ctx.pathParam("account_id");
        int accId = Integer.parseInt(idString);
        List<Message> messages = messageService.getAllMessageByUserId(accId);
        ctx.json(messages);
    }
}