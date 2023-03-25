package ru.nikegreen.openGlGame1.engine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * Класс для обработки событий от клавиатуры
 */
public class Keyboard {
    private final boolean[] keys = new boolean[GLFW_KEY_LAST];
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
}
