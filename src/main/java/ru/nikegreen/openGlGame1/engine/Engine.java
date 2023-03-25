package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import lombok.Setter;

public class Engine {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static final String TITLE = "Open GL window";
    @Getter
    @Setter
    private EngineWindow engineWindow;

    public void run() {
        init();
    }

    public void init() {
        engineWindow = new EngineWindow(WIDTH, HEIGHT, TITLE);
        engineWindow.create();
        update();
    }

    public  void update() {
        while (!engineWindow.isCloseRequest()) {
            //рисование в окне
            engineWindow.update();
        }
    }
}
