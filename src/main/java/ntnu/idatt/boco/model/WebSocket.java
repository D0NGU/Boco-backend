package ntnu.idatt.boco.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocket {
    Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private Session session;
    private static CopyOnWriteArrayList<WebSocket> webSockets = new CopyOnWriteArrayList<>();
    private static Map<String,Session> sessionPool = new HashMap<String,Session>();

    /**
     * Method that runs when a Websocket is opened
     * @param session session
     * @param id the id of the target user
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") String id){
        this.session = session;
        webSockets.add(this);
        sessionPool.put(id,session);
        logger.info("[websocket message] has new connections, total" + webSockets.size());

    }

    /**
     * Method that runs when the WebSocket is closed
     */
    @OnClose
    public void onClose(){
        webSockets.remove(this);
        logger.info("[websocket message] has new connections, total" + webSockets.size());
    }

    /**
     * Method that runs when a message is received
     * @param message the message that is received
     */
    @OnMessage
    public void onMessage(String message){
        logger.info("[websocket message] receives client message:"+message);
    }

    /**
     * Method for sending a message to a single WebSocket
     * @param id the id of the target user
     * @param message the message to send
     */
    public static void sendMessage(String id, String message) {
        Session session = sessionPool.get(id);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
