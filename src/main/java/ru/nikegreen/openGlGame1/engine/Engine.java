package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.nikegreen.openGlGame1.object.GameObject;
import ru.nikegreen.openGlGame1.renderer.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45C.glCreateBuffers;
import static org.lwjgl.opengl.GL45C.glCreateVertexArrays;
import static ru.nikegreen.openGlGame1.util.FileUtil.separatorNormalizer;

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
     * Создание шейдеров
     */
    @Getter
    private Shader shader;

    /**
     * texture
     */
    @Getter
    private Texture texture;

    @Getter
    private List<GameObject> gameObjects;

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
        RenderEngine.init();
        gameObjects = new ArrayList<>();
        keyboard = new Keyboard(engineWindow);
        mouse = new Mouse(engineWindow);
        shader = new Shader(
                separatorNormalizer("shaders/Rectangle.vert"),
                separatorNormalizer("shaders/Rectangle.frag")
        );
        //texture = new Texture("textures/texture1.png");
        texture = new Texture("textures/texture2.jpg");
        shader.bind();
        shader.setUniformFromInt("u_TextureSampler", 0);

        System.out.println("renderer: " + glGetString(GL_RENDERER));
        System.out.println("vendor: " + glGetString(GL_VENDOR));
        System.out.println("version: " + glGetString(GL_VERSION));
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        update();
    }

    /**
     * основной цикл рисования в окне OpenGL
     */
    public void update() {
        //индексы рисования квадрата из вершин в vbo_quad
        int[] ibo_quad = {
                0, 1, 2,
                0, 2, 3
        };

        //вершины (x,y,z) + цвета (rgba) + коорд.текстур(x,y) против часовой стрелки
        float[] veritces = {
                //вершины (x,   y,    z) + цвета (red  green blue  alpha) коорд.текстур
                0.5f, 0.5f, 0.0f,        1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                -0.5f, 0.5f, 0.0f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f,      1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                0.5f, -0.5f, 0.0f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f
        };

        VertexArrayObj vertexArrayObj = new VertexArrayObj();
        VertexBufferObject vertexBufferObj = new VertexBufferObject(veritces);
        vertexBufferObj.setLayout(
                new BufferLayout(
                        new VertexAttribute("attrib_Position", VertexAttribute.ShaderDataType.t_float3),
                        new VertexAttribute("attrib_Colour", VertexAttribute.ShaderDataType.t_float4),
                        new VertexAttribute("attrib_TextureCoord", VertexAttribute.ShaderDataType.t_float2)
                )
        );
        vertexArrayObj.putBuffer(vertexBufferObj);
        IndexBufferObj indexBufferObj = new IndexBufferObj(ibo_quad);
        vertexArrayObj.putBuffer(indexBufferObj);

        Vector4f colour = new Vector4f(0,0,0, 1);

        GameObject gameObject = new GameObject(
                new Vector3f(-1.5f,0,0),
                new Vector3f(0,0,0),
                new Vector3f(1,1,1)
        );
        gameObject.setModel(vertexArrayObj);
        gameObject.setTexture(texture);
        gameObjects.add(gameObject);

        GameObject gameObject2 = new GameObject(
                new Vector3f(0,0,0),
                new Vector3f(0,0,0),
                new Vector3f(1,1,1)
        );
        gameObject2.setModel(vertexArrayObj);
        gameObject2.setTexture(texture);
        gameObjects.add(gameObject2);

        while (!engineWindow.isCloseRequest()) {

            //проверка кнопок
            keyboard.handleKeyboardInput();
            //mouse
            mouse.handleMouseInput();
            //движение
            float speed = 0.01f;
            gameObject.getPosition().x += speed;
            gameObject.getRotation().x += speed;
            if (gameObject.getPosition().x >= 1.5f) {
                gameObject.getPosition().x = -1.5f;
            }
            //рисование в окне
            RenderEngine.begin(shader);
            for (GameObject go: gameObjects) {
                RenderEngine.renderGameObj(shader, go);
            }
            RenderEngine.end(shader);
            //обновляем окно
            engineWindow.swapBuffers();
        }
        RenderEngine.destroy();
        //удаляем игровые объекты
        for (GameObject obj: gameObjects) {
            obj.destroy();
        }
        gameObjects.clear();
    }
}
