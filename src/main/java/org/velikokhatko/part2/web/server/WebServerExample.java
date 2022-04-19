package org.velikokhatko.part2.web.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class WebServerExample {
    private static final int PORT = 1488;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("Socket is ready to serve on port %d...\n", PORT);
            while (true) {
                new ExtSocketThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ExtSocketThread extends Thread {
        Socket clientSocket;
        InputStream inputStream;
        OutputStream outputStream;
        PrintWriter printWriter;

        public ExtSocketThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                clientSocket.setSoTimeout(3000);
                inputStream = clientSocket.getInputStream();
                while (true) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    Integer contentLength = 0;
                    boolean flagStopReadingHeaders = false;
                    for (byte[] b = new byte[1]; !flagStopReadingHeaders && inputStream.read(b) > -1; ) {
                        byteArrayOutputStream.write(b);
                        if (byteArrayOutputStream.toString(Charset.defaultCharset())
                                .endsWith(new String(new char[]{10, 13, 10}))) {
                            String[] corteges = byteArrayOutputStream.toString().split("\n");
                            contentLength = Arrays.stream(corteges)
                                    .filter(c -> c.startsWith("Content-Length: "))
                                    .map(c -> c.substring(c.indexOf(":") + 2, c.indexOf("\r")))
                                    .map(Integer::valueOf)
                                    .filter(i -> i > 0)
                                    .reduce(0, Integer::sum, Integer::sum);
                            flagStopReadingHeaders = true;
                        }
                    }
                    System.out.println("-------------------- HEADERS -------------------");
                    System.out.println(byteArrayOutputStream);
                    System.out.println("------------------- /HEADERS -------------------");

                    if (contentLength > 0) {
                        byte[] body = new byte[contentLength];
                        inputStream.read(body);
                        System.out.println("---------------------  BODY  -------------------");
                        System.out.println(new String(body, Charset.defaultCharset()));
                        System.out.println("--------------------- /BODY  -------------------");
                    } else {
                        System.out.println("Request has no body");
                    }
                    tryClose(byteArrayOutputStream);

                    outputStream = clientSocket.getOutputStream();
                    printWriter = new PrintWriter(outputStream);
                    try {
                        String body = "Нормально: " + Thread.currentThread().getId();
                        int length = body.length();

                        printWriter.println("HTTP/1.1 200 Ok");
                        printWriter.println("Content-Type: text/html; charset=utf-8");
                        printWriter.println("Content-Length: " + length);
                        printWriter.println();
                        printWriter.println(body);
                        printWriter.println();
                        printWriter.flush();
                    } finally {
//                    tryClose(printWriter);
//                    tryClose(outputStream);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                tryClose(printWriter);
                tryClose(outputStream);
                tryClose(inputStream);
                tryClose(clientSocket);
            } finally {
//                tryClose(bufferedReader);
//                tryClose(inputStream);
//                tryClose(clientSocket);
                System.out.println();
            }
        }

        private void tryClose(Closeable closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println(closeable.getClass().getSimpleName() + " closed");
            }
        }
    }
}
