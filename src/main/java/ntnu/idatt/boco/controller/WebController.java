package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class WebController {
    @Autowired
    private WebSocket webSocket;

    /*
    @RequestMapping("/sendAllWebSocket")
    public String test() {
        WebSocket.sendAllMessage("Get up in the morning and open the window, feel beautiful ~");
        return "websocket group send! ";
    }*/

    @RequestMapping("/sendOneWebSocket")
    public String sendOneWebSocket(String id) {
        WebSocket.sendMessage(id, "Test message");
        return "websocket single-person sending";
    }
}
