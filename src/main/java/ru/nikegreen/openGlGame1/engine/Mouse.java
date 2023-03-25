package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private final boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private final EngineWindow engineWindow;

    @Getter
    private static double x;
    @Getter
    private static double y;

    /**
     * Конструктор
     * @param engineWindow дескриптор окна OpenGL
     */
    public Mouse(EngineWindow engineWindow) {
        this.engineWindow = engineWindow;
    }

    /**
     * Проверка клавиши в нажатом состоянии
     * @param keyId код клавиши тип int
     * @return тип boolean
     * true  - нажата
     * false - не нажата
     */
    public boolean buttonPressed(int keyId) {
        return glfwGetMouseButton(engineWindow.getWindow(), keyId) == 1;
    }

    /**
     * событие нажатие на кнопку
     * @param keyId код кнопки
     * @return тип boolean
     * true  - нажата
     * false - не нажата
     */
    public boolean buttonDown(int keyId) {
        return  buttonPressed(keyId) && !buttons[keyId];
    }

    /**
     * событие кнопку отпустили
     * @param keyId код кнопки
     * @return тип boolean
     * true  - не нажата
     * false - нажата
     */
    public boolean buttonUp(int keyId) {
        return !buttonPressed(keyId) && buttons[keyId];
    }

    /**
     * Проверка и формирование событий кнопок
     * нужно циклически вызывать
     */
    public void handleMouseInput() {
        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            buttons[i] = engineWindow.keyPressed(i);
        }
    }

    public static void setMouseCallbacks(long windowHandle) {
        setCursorPositionCallback(windowHandle);
    }

    public static void setCursorPositionCallback(long windowHandle) {
        glfwSetCursorPosCallback(windowHandle, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                x = v;
                y = v1;
            }
        });
    }
}
