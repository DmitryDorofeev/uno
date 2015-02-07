package main;

import MessageSystem.MessageSystem;
import MessageSystem.AddressService;
import admin.AdminPageServletImpl;
import base.*;
import db.DBService;
import db.DBServiceImpl;
import frontend.*;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.json.simple.JSONObject;
import resources.ResourceSystem;
import utils.LoggerHelper;
import utils.TimeService;

import javax.servlet.Servlet;
import java.sql.Time;
import java.util.Set;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws Exception {
        ResourceSystem resourceSystem = ResourceSystem.instance();
        DBService dbService = new DBServiceImpl();
        if (!dbService.getStatus()) {
            System.out.println("Error with db connection!");
            System.exit(1);
        }
        Server server = new Server(resourceSystem.getServerConfigResource().getPort());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        AddressService addressService = new AddressService();

        MessageSystem messageSystem = MessageSystem.instance();
        messageSystem.setAddressService(addressService);

        final Thread webSocketServiceThread = new Thread(new WebSocketServiceImpl());
        webSocketServiceThread.setName("webSocketServiceThread");
        webSocketServiceThread.setDaemon(true);
        final Thread gameMechanicsThread = new Thread(new GameMechanicsImpl(dbService));
        gameMechanicsThread.setName("gameMechanicsThread");
        gameMechanicsThread.setDaemon(true);

        webSocketServiceThread.start();
        gameMechanicsThread.start();

        AuthService authService = new AuthServiceImpl(dbService);

        Servlet signIn = new SignInServletImpl(authService);
        Servlet signUp = new SignUpServletImpl(authService);
        Servlet logOut = new LogOutServletImpl(authService);
        Servlet profile = new UserProfileServletImpl(authService);
        Servlet scoreboard = new ScoreboardServletImpl(dbService);
        Servlet admin = new AdminPageServletImpl(authService);

        context.addServlet(new ServletHolder(new WebSocketGameServlet(authService)),
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

        final int timeMs = 60000;
        TimeService.instance().start();
        TimeService.instance().schedulePeriodicTask(new TimerTask() {
            @Override
            public void run() {
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
                for (int i = 0; i < threadArray.length; ++i) {
                    JSONObject jsonBody = new JSONObject();
                    StringBuilder stackTraceStr = new StringBuilder();
                    StackTraceElement[] stack = threadArray[i].getStackTrace();
                    for (int j = 0; j < stack.length; ++j) {
                        stackTraceStr.append(stack[j].toString() + "<br>");
                    }
                    jsonBody.put("trace", stackTraceStr);
                    LoggerHelper.logJSON(threadArray[i].toString(), jsonBody);
                }
            }
        }, timeMs);

        server.start();
    }
}
