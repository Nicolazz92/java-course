package org.velikokhatko.part2.web.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServerExample {

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(1488);
            while (true) {
                Socket socket = serverSocket.accept();
                try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    while (bufferedReader.ready()) {
                        final String line = bufferedReader.readLine();
                        System.out.println(line);
                    }

                    final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("HTTP/1.1 200 Ok");
                    printWriter.println("Content-Type: text/html; charset=utf-8");
                    printWriter.println();
                    printWriter.println("Нормально");
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    t.printStackTrace();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
