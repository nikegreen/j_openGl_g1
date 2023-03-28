package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Класс окна канвас OpenGL
 */
public class EngineWindow {
    //размеры OpenGL окна
    @Getter
    private int width; //ширина окна
    @Getter
    private int height; //высота окна
    @Getter
    private String title; //оглавление окна
    @Getter
    private long window; //дескриптор окна
    private IntBuffer pWidth;
    private IntBuffer pHeight;
    private GLFWVidMode vidmode;
//    @Getter
//    private Keyboard keyboard;
    private final boolean[] keys = new boolean[GLFW_KEY_LAST];

    /**
     * конструктор
     * @param width ширина окна тип int
     * @param height высота окна тип int
     * @param title оглавление окна  тип {@link java.lang.String}
     */
    public EngineWindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /**
     * создание канвас OpenGL
     * вызывается до отрисовки
     */
    public void create() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() ) {
            //если не проинициализировали GLFW
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Настройка GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window OpenGL
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL ) {
            //окно не создано
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            //резервируем память для указателей:
            pWidth = stack.mallocInt(1); // int*
            pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //установим оглавление окна
            glfwSetWindowTitle(window, title);
            //установка размера окна
            glfwSetWindowSize(window, width, height);
            //установка соотношения сторон
            glfwSetWindowAspectRatio(window, width, height);

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
            glfwSetWindowSizeLimits(window, width, height, 1980, 1200);
        } // the stack frame is popped automatically

        //Keyboard.setCallback(Keyboard.defCallback);
        Keyboard.setCallBackDefault(window);


        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        createCapabilities();
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        Mouse.setMouseCallbacks(window);
    }

    /**
     * Обновление окна
     */
    public void swapBuffers() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    /**
     * освободить все ресурсы и уничтожить OpenGL окно
     */
    public void destroy() {
        glfwDestroyWindow(window);
    }

    /**
     * Проверить событие для закрытия окна с OpenGL
     * @return тип boolean
     * true - закрыть окно
     * false - продолжать работу с OpenGL
     */
    public boolean isCloseRequest() {
        return  glfwWindowShouldClose(window);
    }

    public boolean keyPressed(int key) {
        return (key < GLFW_KEY_LAST) && keys[key];
    }
}
