package main;

import admin.AdminPageServletImpl;
import base.*;
import frontend.*;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import sax.ReadXMLFileSAX;
import sax.SerializationObject;

import javax.servlet.Servlet;

public class Main {
    public static void main(String[] args) throws Exception {
        SerializationObject object = (SerializationObject) ReadXMLFileSAX.readXML("resources/port.xml");
        if (object == null) {
            System.out.println("Error with resource file!");
            System.exit(1);
        }
        Server server = new Server(object.getPort());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        AuthService authService = new AuthServiceImpl();

        Servlet signIn = new SignInServletImpl(authService);
        Servlet signUp = new SignUpServletImpl(authService);
        Servlet profile = new UserProfileServletImpl(authService);
        Servlet admin = new AdminPageServletImpl(authService);

        context.addServlet(new ServletHolder(new WebSocketGameServlet(authService, gameMechanics, webSocketService)), "/gameplay");
        context.addServlet(new ServletHolder(new FrontendServlet(gameMechanics, authService)), "/game.html");
        context.addServlet(new ServletHolder(signIn), SignInServlet.signInPageURL);
        context.addServlet(new ServletHolder(signUp), SignUpServlet.signUpPageURL);
        context.addServlet(new ServletHolder(profile), UserProfileServlet.userProfilePageURL);
        context.addServlet(new ServletHolder(admin), AdminPageServlet.adminPageURL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
    }
}
