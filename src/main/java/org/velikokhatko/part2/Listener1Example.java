package org.velikokhatko.part2;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

public class Listener1Example implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Слушаю внимательно");
    }
}
