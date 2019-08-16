import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionChecker {

    private String host;

    ConnectionChecker(String host){
        this.host = host;
    }



    public boolean connect() {
        try {
            boolean a =  InetAddress.getByName(host).isReachable(5000);
            return a;
        } catch (Exception e) {
            return false;

        }
    }
}
