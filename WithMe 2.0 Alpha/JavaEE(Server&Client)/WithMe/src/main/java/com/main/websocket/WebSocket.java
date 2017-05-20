package com.main.websocket;

import javax.servlet.*;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by 桐小目 on 2017/2/15.
 */
public class WebSocket implements Servlet {
    int port = 20818;
    Server server = null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            server = new Server(port);
            server.start();
            System.out.println("Server启动成功，端口为：20818");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        try {
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
