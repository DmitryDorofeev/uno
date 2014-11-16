package main;

import admin.AdminPageServletImpl;
import base.*;
import db.DBService;
import frontend.*;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.ResourceSystem;

import javax.servlet.Servlet;

public class Main {
    public static void main(String[] args) throws Exception {
        ResourceSystem resourceSystem = ResourceSystem.instance();
        DBService dbService = DBService.instance();
        if (!dbService.getStatus()) {
            System.out.println("Error with db connection!");
            System.exit(1);
        }
        Server server = new Server(resourceSystem.getPortResource().getPort());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        AuthService authService = new AuthServiceImpl();

        Servlet signIn = new SignInServletImpl(authService);
        Servlet signUp = new SignUpServletImpl(authService);
        Servlet logOut = new LogOutServletImpl(authService);
        Servlet profile = new UserProfileServletImpl(authService);
        Servlet scoreboard = new ScoreboardServletImpl(authService);
        Servlet admin = new AdminPageServletImpl(authService);

        context.addServlet(new ServletHolder(new WebSocketGameServlet(authService, gameMechanics, webSocketService)),
                                                WebSocketGameServlet.WebSocketGameServletURL);
        context.addServlet(new ServletHolder(signIn), SignInServlet.signInPageURL);
        context.addServlet(new ServletHolder(signUp), SignUpServlet.signUpPageURL);
        context.addServlet(new ServletHolder(logOut), LogOutServlet.logOutURL);
        context.addServlet(new ServletHolder(profile), UserProfileServlet.userProfilePageURL);
        context.addServlet(new ServletHolder(scoreboard), ScoreboardServlet.scoreboardPageURL);
        context.addServlet(new ServletHolder(admin), AdminPageServlet.adminPageURL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.setHandler(handlers);

        server.start();
    }
}
