package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45C.glCreateBuffers;
import static org.lwjgl.opengl.GL45C.glCreateVertexArrays;
import static ru.nikegreen.openGlGame1.util.MemBuffer.*;

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

        //создадим массив вершин
        //int vaoId = glCreateVertexArrays(); //openGL 4.5
        int vaoId = glGenVertexArrays(); //openGL 3.3
        glBindVertexArray(vaoId);

        //связываем индексы
        //int iboId = glCreateBuffers(); //openGL 4.5
        int iboId = glGenBuffers(); //openGL 3.3
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
        //IntBuffer intBuffer = storeDataInIntBuffer(indexes);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, putData(indexes), GL_STATIC_DRAW);
        //MemoryUtil.memFree(intBuffer); //освобождаем память занятую intBuffer

        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        //FloatBuffer buffer = storeDataInFloatBuffer(vbo_quad);
        glBufferData(GL_ARRAY_BUFFER, putData(vbo_quad), GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        //MemoryUtil.memFree(buffer);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glBindVertexArray(vaoId);

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
            glClearColor(0, 1, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            //рисуем треугольник в буфере
            glBindVertexArray(vaoId);
//            glEnableVertexAttribArray(0);
//            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_romb.length / 3);
            glDrawElements(GL_TRIANGLES, indexes.length, GL_UNSIGNED_INT, 0);
            glDisableVertexAttribArray(0);
            glBindVertexArray(vaoId);

            //обновляем окно
            engineWindow.update();
        }
    }
}
