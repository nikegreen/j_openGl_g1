package ru.nikegreen.openGlGame1.engine;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 * Класс для обработки событий от клавиатуры
 */
public class Keyboard {
    private static final boolean[] keys = new boolean[GLFW_KEY_LAST];
    private final EngineWindow engineWindow;

    /**
     * Конструктор
     * @param engineWindow дескриптор окна OpenGL
     */
    public Keyboard(EngineWindow engineWindow) {
        this.engineWindow = engineWindow;
    }

    /**
     * Проверка клавиши в нажатом состоянии
     * @param keyId код клавиши тип int
     * @return тип boolean
     * true  - нажата
     * false - не нажата
     */
    public boolean keyPressed(int keyId) {
        return engineWindow.keyPressed(keyId);
    }

    /**
     * событие нажатие на кнопку
     * @param keyId код кнопки
     * @return тип boolean
     * true  - нажата
     * false - не нажата
     */
    public boolean keyDown(int keyId) {
        return  keyPressed(keyId) && !keys[keyId];
    }

    /**
     * событие кнопку отпустили
     * @param keyId код кнопки
     * @return тип boolean
     * true  - не нажата
     * false - нажата
     */
    public boolean keyUp(int keyId) {
        return !keyPressed(keyId) && keys[keyId];
    }

    /**
     * Проверка и формирование событий кнопок
     * нужно циклически вызывать
     */
    public void handleKeyboardInput() {
        for (int i = 0; i < GLFW_KEY_LAST; i++) {
            keys[i] = engineWindow.keyPressed(i);
        }
    }

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.

    /**
     * Установить свой обработчик клавиатуры
     * @param windowHandle дескриптор окна openGL
     * @param keyCallbackFunction функция принимает:
     *                           long windowHandle,
     *                            int key,
     *                            int scancode,
     *                            int action,
     *                            int mods
     */
    public static void setCallBack(long windowHandle, GLFWKeyCallbackI keyCallbackFunction) {
        glfwSetKeyCallback(windowHandle, keyCallbackFunction);

    }

    /**
     * устанавливает для окна обработчик нажатий кнопок по умолчанию
     * @param windowHandle дескриптор окна openGL
     */
    public static void setCallBackDefault(long windowHandle) {
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                keys[key] = true;
            }
            if (key == GLFW_KEY_A && action == GLFW_RELEASE) {
                keys[key] = false;
            }
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });
    }
}
