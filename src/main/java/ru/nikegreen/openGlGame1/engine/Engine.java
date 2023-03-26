package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static ru.nikegreen.openGlGame1.util.MemBuffer.storeDataInFloatBuffer;
import static ru.nikegreen.openGlGame1.util.MemBuffer.storeDataInIntBuffer;

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

    /**
     * основной цикл рисования в окне OpenGL
     */
    public void update() {
        //позиции вершин (vbo)
        //фигура квадрат только вершины квадрата
        //треугольники через индексы вершин
        float[] vbo_quad = {
                0.5f, 0.5f, 0.0f,
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
        };

        //позиции вершин (vbo)
        //фигура треугольник
        float[] v_triangle = {
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        //позиции вершин (vbo)
        //фигура квадрат
        float[] v_quad = {
                0.5f, 0.5f, 0.0f,
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        //позиции вершин (vbo)
        //фигура ромб
        float[] v_romb = {
                0.0f, 0.5f, 0.0f,
                -0.5f, 0.0f, 0.0f,
                0.5f, -0.0f, 0.0f,
                0.5f, 0.0f, 0.0f,
                -0.5f, -0.0f, 0.0f,
                0.0f, -0.5f, 0.0f
        };

        int[] indexes = {
                0, 1, 2,
                0, 2, 3
        };

        int vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        //связываем индексы
        int iboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboId);
        IntBuffer intBuffer = storeDataInIntBuffer(indexes);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL30.GL_STATIC_DRAW);
        MemoryUtil.memFree(intBuffer); //освобождаем память занятую intBuffer


        int vboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(vbo_quad);
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
//            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_romb.length / 3);
            GL11.glDrawElements(GL11.GL_TRIANGLES, indexes.length, GL11.GL_UNSIGNED_INT, 0);
            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(vaoId);

            //обновляем окно
            engineWindow.update();
        }
    }
}
