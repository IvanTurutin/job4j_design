package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    String str = in.readLine();
                    System.out.println(str);
                    if (str != null && !str.isEmpty()) {
                        String request = getRequest(str);
                        if ("Exit".equals(request)) {
                            out.write("Bye".getBytes());
                            server.close();
                            continue;
                        } else if ("Hello".equals(request)) {
                            out.write("Hello, dear friend.".getBytes());
                        } else {
                            out.write(request.getBytes());
                        }
                    }

                    out.flush();
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in EchoServer", e);
        }
    }

    private static String getRequest(String request) {
        String[] str = request.split(" ");
        String rsl;
        if (str[1].startsWith("/?msg=")) {
            rsl = str[1].split("=")[1];
        } else {
            throw new IllegalArgumentException("Incorrect request.");
        }
        return rsl;
    }
}