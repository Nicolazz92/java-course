package org.velikokhatko.part2.web.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class WebServerExample {
    private static final int PORT = 1488;

    public static void main(String[] args) {

        System.out.println((char) 10);

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
        BufferedReader bufferedReader;
        InputStream inputStream;
        OutputStream outputStream;
        PrintWriter printWriter;

        public ExtSocketThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                inputStream = clientSocket.getInputStream();
                int buffSize = 1;
                byte[] buffer = new byte[buffSize];
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                int realBufferLength;
                while ((realBufferLength = inputStream.read(buffer)) > -1) {
                    bytes.write(buffer, 0, realBufferLength);
                    System.out.println(realBufferLength);
                    System.out.println(Arrays.toString(buffer));
                    if (bytes.toString(Charset.defaultCharset()).endsWith(new String(new char[]{10, 13, 10}))) {
                        System.out.println("ПОЙМАЛ");
                    }
                    if (realBufferLength < buffSize || inputStream.available() == -1) {
                        break;
                    }
                }
                System.out.println(bytes.toString(Charset.defaultCharset()));
                //TODO https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
                // считать header, вывести их, найти конец хедеров, найти контент-ленгт, считать боди.

//                while (inputStream.available() >= 0)
//                inputStream.available()
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                while (bufferedReader.ready()) {
//                    final String line = bufferedReader.readLine();
//                    System.out.println(line);
//                }

                outputStream = clientSocket.getOutputStream();
                printWriter = new PrintWriter(outputStream);
                try {
                    printWriter.println("HTTP/1.1 200 Ok");
                    printWriter.println("Content-Type: text/html; charset=utf-8");
                    printWriter.println();
                    printWriter.println("Нормально: " + Thread.currentThread().getId());
                    printWriter.flush();
                } finally {
//                    tryClose(printWriter);
//                    tryClose(outputStream);
                }
            } catch (Throwable e) {
                e.printStackTrace();
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
