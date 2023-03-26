package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

/**
 * Логика работы
 */
public class Engine {
    /**
     * константы
     */
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static final String TITLE = "Open GL window";

    /**
     * работа с окном OpenGL
     */
    @Getter
    private EngineWindow engineWindow;

    /**
     * обработка событий клавиатуры
     */
    @Getter
    private Keyboard keyboard;

    /**
     * Обработка событий мыши
     */
    @Getter
    private Mouse mouse;

    /**
     * запуск OpenGL приложения
     */
    public void run() {
        init();
        engineWindow.destroy();
    }

    /**
     * Подготовка данных для запуска окна OpenGL.
     * 1. создание окна OpenGL
     * 2. создание обработчиков клавиатуры
     * 3. создание обработчиков мыши
     * 4. запуск основного цикла рисования в OpenGL
     */
    public void init() {
        engineWindow = new EngineWindow(WIDTH, HEIGHT, TITLE);
        engineWindow.create();
        keyboard = new Keyboard(engineWindow);
        mouse = new Mouse(engineWindow);
        update();
    }

    public FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * основной цикл рисования в окне OpenGL
     */
    public void update() {
        float[] v_position = {
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        int vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        int vboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(v_position);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        MemoryUtil.memFree(buffer);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);

        GL30.glBindVertexArray(vaoId);

        while (!engineWindow.isCloseRequest()) {

            //проверка кнопок
//            if (keyboard.keyDown(GLFW_KEY_A)) {
//                System.out.println("down A");
//            }
//            if (keyboard.keyUp(GLFW_KEY_A)) {
//                System.out.println("up   A");
//            }
            keyboard.handleKeyboardInput();
//            //mouse
//            if (mouse.buttonDown(GLFW_MOUSE_BUTTON_1)) {
//                System.out.println("down left");
//            }
//            if (mouse.buttonUp(GLFW_MOUSE_BUTTON_1)) {
//                System.out.println("up   left");
//            }
//            System.out.println("x=" + Mouse.getX() + " y=" + Mouse.getY());
            mouse.handleMouseInput();
            //рисование в окне

            //стираем буффер
            GL11.glClearColor(0, 1, 0, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            //рисуем треугольник в буфере
            GL30.glBindVertexArray(vaoId);
            GL30.glEnableVertexAttribArray(0);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_position.length / 3);
            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(vaoId);

            //обновляем окно
            engineWindow.update();
        }
    }
}
