package org.velikokhatko.part2.web.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class WebServerExample {
    private static final int PORT = 1488;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("Socket is ready to serve on port %d...", PORT);
            while (true) {
                new ExtSocketThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ExtSocketThread extends Thread {
        Socket clientSocket;

        public ExtSocketThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                while (bufferedReader.ready()) {
                    final String line = bufferedReader.readLine();
                    System.out.println(line);
                }

                try (OutputStream outputStream = clientSocket.getOutputStream();
                     PrintWriter printWriter = new PrintWriter(outputStream)) {
                    printWriter.println("HTTP/1.1 200 Ok");
                    printWriter.println("Content-Type: text/html; charset=utf-8");
                    printWriter.println();
                    printWriter.println("Нормально: " + Thread.currentThread().getId());
                    printWriter.flush();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
