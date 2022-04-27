package ntnu.idatt.boco.model;

import java.io.Serializable;

public class JWT_Response implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private int id;
    private final String jwttoken;

    public JWT_Response(int id, String jwttoken) {
        this.id = id;
        this.jwttoken = jwttoken;
    }

    public JWT_Response(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
    public int getId() {
        return id;
    }
}
