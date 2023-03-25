package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import lombok.Setter;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class Engine {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static final String TITLE = "Open GL window";
    @Getter
    @Setter
    private EngineWindow engineWindow;

    @Getter
    private Keyboard keyboard;

    @Getter
    private Mouse mouse;

    public void run() {
        init();
        engineWindow.destroy();
    }

    public void init() {
        engineWindow = new EngineWindow(WIDTH, HEIGHT, TITLE);
        engineWindow.create();
        keyboard = new Keyboard(engineWindow);
        mouse = new Mouse(engineWindow);
        update();
    }

    public void update() {
        while (!engineWindow.isCloseRequest()) {
            //проверка кнопок
            if (keyboard.keyDown(GLFW_KEY_A)) {
                System.out.println("down A");
            }
            if (keyboard.keyUp(GLFW_KEY_A)) {
                System.out.println("up   A");
            }
            keyboard.handleKeyboardInput();
            //mouse
            if (mouse.buttonDown(GLFW_MOUSE_BUTTON_1)) {
                System.out.println("down left");
            }
            if (mouse.buttonUp(GLFW_MOUSE_BUTTON_1)) {
                System.out.println("up   left");
            }
            System.out.println("x=" + Mouse.getX() + " y=" + Mouse.getY());
            mouse.handleMouseInput();
            //рисование в окне
            engineWindow.update();
        }
    }
}
