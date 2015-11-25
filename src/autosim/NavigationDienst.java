package autosim;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

/**
 * Created by alican on 23.10.2015.
 */
public class NavigationDienst {

    public Integer add(int x, int y) {
        return x + y;
    }


    private static final int port = 8080;

    public static void main(String[] args) {

        try {
            WebServer webServer = new WebServer(port);
            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
            PropertyHandlerMapping phm = new PropertyHandlerMapping();

           // phm.addHandler( "Calculator", autosim.NavigationDienst.class);
            phm.addHandler( "RoadState", NavigationDienst.class);

            xmlRpcServer.setHandlerMapping(phm);
            XmlRpcServerConfigImpl serverConfig =
                    (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();

            webServer.start();

        } catch (IOException | XmlRpcException e) {
            e.printStackTrace();
        }

    }
}
