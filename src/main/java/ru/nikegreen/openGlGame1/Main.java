package ru.nikegreen.openGlGame1;

import ru.nikegreen.openGlGame1.engine.Engine;

public class Main {
    public static Engine engine = null;
    public static void main(String[] args) {
        engine = new Engine();
        engine.run();
    }
}
