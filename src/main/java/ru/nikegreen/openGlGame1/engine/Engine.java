package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.joml.Vector3f;
import ru.nikegreen.openGlGame1.object.GameObject;
import ru.nikegreen.openGlGame1.renderer.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
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
    private static final float Z_STEP = 0.02f;

    /**
     * работа с окном OpenGL
     */
    @Getter
    private EngineWindow engineWindow;

    /**
     * обработка событий клавиатуры
     */
    //@Getter
    //private Keyboard keyboard;

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
        //engineWindow.setFullscreen(true);
        engineWindow.create();
        RenderEngine.init(engineWindow);
        gameObjects = new ArrayList<>();
        //keyboard = new Keyboard(engineWindow);
        mouse = new Mouse(engineWindow);
        shader = new Shader(
                separatorNormalizer("shaders/rectangle.vert"),
                separatorNormalizer("shaders/rectangle.frag")
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
        //индексы рисования куба
        int[] iboQube = new int[] {
                //перед
                0, 1, 2,//треугольник 1
                2, 3, 0,//треугольник 2
                //зад
                4, 5, 6,//треугольник 3
                6, 7, 4,//треугольник 4
                //право
                8, 9, 10, //треугольник 5
                10, 11, 8,//треугольник 6
                //лево
                12, 15, 14,//треугольник 7
                14, 13, 12,//треугольник 8
                //верх
                16,17,18,//треугольник 9
                18,19,16,//треугольник 10
                //низ
                20,21,22,//треугольник 11
                22,23,20 //треугольник 12
        };

        //вершины (x,y,z) + цвета (rgba) + коорд.текстур(x,y) против часовой стрелки
        float[] veritces = {
                //вершины (x,   y,    z) + цвета (red  green blue  alpha) коорд.текстур
                ///////////////////////////////////////////////////////////////
                // куб вершины
                /////////плоскость 0 перед
                 0.5f,  0.5f, 0.5f,       1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                -0.5f,  0.5f, 0.5f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                -0.5f, -0.5f, 0.5f,       1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                 0.5f, -0.5f, 0.5f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f,
                /////////плоскость 1 сзади
                // VO - 3
                 0.5f,  0.5f, -0.5f,       1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,       1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                 0.5f, -0.5f, -0.5f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f,
                /////////плоскость 2 справа
                 0.5f,  0.5f, -0.5f,       1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                 0.5f, -0.5f, -0.5f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                 0.5f, -0.5f,  0.5f,       1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                 0.5f,  0.5f,  0.5f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f,
                /////////плоскость 3 слева
                 -0.5f,  0.5f, -0.5f,       1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                 -0.5f, -0.5f, -0.5f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                 -0.5f, -0.5f,  0.5f,       1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                 -0.5f,  0.5f,  0.5f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f,
                /////////плоскость 4 верх
                -0.5f,  0.5f, -0.5f,       1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                 0.5f,  0.5f,  0.5f,       1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                 0.5f,  0.5f, -0.5f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f,
                /////////плоскость 5 низ
                 0.5f, -0.5f,  0.5f,       1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
                 0.5f, -0.5f, -0.5f,       0.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,       1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,       0.0f, 1.0f, 1.0f, 1.0f,  1.0f, 1.0f
        };

        VertexArrayObj vertexArrayObj = new VertexArrayObj();
        VertexBufferObj vertexBufferObj = new VertexBufferObj(veritces);
        vertexBufferObj.setLayout(
                new BufferLayout(
                        new VertexAttribute("attrib_Position", VertexAttribute.ShaderDataType.t_float3),
                        new VertexAttribute("attrib_Colour", VertexAttribute.ShaderDataType.t_float4),
                        new VertexAttribute("attrib_TextureCoord", VertexAttribute.ShaderDataType.t_float2)
                )
        );

        vertexArrayObj.putBuffer(vertexBufferObj);
        IndexBufferObj indexBufferObj = new IndexBufferObj(iboQube);
        vertexArrayObj.putBuffer(indexBufferObj);

        //Vector4f colour = new Vector4f(0,0,0, 1);

        //первый игровой объект
        GameObject gameObject = new GameObject(
                new Vector3f(-1.5f,0,0), //координаты объекта x,y,z
                new Vector3f(0,0,0),
                new Vector3f(1,1,1)
        );
        gameObject.buildModel(vertexArrayObj) // vertexArrayObj буфер с вершинами и индексами
                .buildTexture(texture)
                .buildIndexesSize(iboQube.length);
        gameObjects.add(gameObject);
        //второй игровой объект
        GameObject gameObject2 = new GameObject(
                new Vector3f(-1.0f, 0,-1.0f),
                new Vector3f(0,0,0),
                new Vector3f(1,1,1)
        );
        gameObject2.buildModel(vertexArrayObj)
                .buildTexture(texture)
                .buildIndexesSize(iboQube.length);
        gameObjects.add(gameObject2);

        //третий игровой объект
        GameObject gameObject3 = new GameObject(
                new Vector3f(1.0f,0,-1.0f),
                new Vector3f(0,0,0),
                new Vector3f(1,1,1)
        );
        gameObject3.buildModel(vertexArrayObj)
                .buildTexture(texture)
                .buildIndexesSize(iboQube.length);
        gameObjects.add(gameObject3);

        // начальные установки камеры
        RenderEngine.getCamera().setPosition(0.0f, 0.0f, 3.5f);
        RenderEngine.getCamera().calculate();

        while (!engineWindow.isCloseRequest()) {
            if (Keyboard.keyPressed(GLFW_KEY_KP_8)) {
                RenderEngine.getCamera().getPosition().z += Z_STEP;
                RenderEngine.getCamera().calculate();
            }
            if (Keyboard.keyPressed(GLFW_KEY_KP_5)) {
                RenderEngine.getCamera().getPosition().z -= Z_STEP;
                RenderEngine.getCamera().calculate();
            }
            if (Keyboard.keyPressed(GLFW_KEY_KP_4)) {
                RenderEngine.getCamera().getPosition().x += Z_STEP;
                RenderEngine.getCamera().calculate();
            }
            if (Keyboard.keyPressed(GLFW_KEY_KP_6)) {
                RenderEngine.getCamera().getPosition().x -= Z_STEP;
                RenderEngine.getCamera().calculate();
            }
            if (Keyboard.keyPressed(GLFW_KEY_KP_ENTER)) {
                RenderEngine.getCamera().getPosition().y += Z_STEP;
                RenderEngine.getCamera().calculate();
            }
            if (Keyboard.keyPressed(GLFW_KEY_KP_ADD)) {
                RenderEngine.getCamera().getPosition().y -= Z_STEP;
                RenderEngine.getCamera().calculate();
            }
            //проверка кнопок
            Keyboard.handleKeyboardInput();
            //mouse
            mouse.handleMouseInput();
            //движение
            float speed = 0.01f;
            float rotSpeed = 180.0f/1.5f*0.01f;
            gameObject.getPosition().x += speed;
            gameObject.getRotation().x += rotSpeed;
            gameObject2.getRotation().y += rotSpeed;
            gameObject3.getRotation().z += rotSpeed;
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
        //цикл рисования окончен
        RenderEngine.destroy();
        //удаляем игровые объекты
        for (GameObject obj: gameObjects) {
            obj.destroy();
        }
        gameObjects.clear();
    }
}
