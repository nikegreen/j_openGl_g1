package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.joml.Vector2i;
import ru.nikegreen.openGlGame1.object.GameObject;
import ru.nikegreen.openGlGame1.renderer.*;

import static org.lwjgl.glfw.GLFW.glfwSetWindowAspectRatio;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.*;

public class RenderEngine {
    @Getter
    private static Camera camera;

    @Getter
    private static EngineWindow engineWindow;

    private static Vector2i oldWindowSize = new Vector2i(0, 0);

    /**
     * Подготавливаем окно к отрисовке шейдера
     * @param window класс окна openGL
     */
    public static void init(EngineWindow window) {
        camera = new Camera();
        engineWindow = window;
        ///////////////////////////////////////////////////////
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Подготовка к отрисовке внутри цикла рендера
     * проверяем изменение размера окна
     * @param shader шейдер
     */
    public static void begin(Shader shader) {
        Vector2i wSize = engineWindow.getWindowSize();
        if (!oldWindowSize.equals(wSize)) {
            oldWindowSize = wSize;
            //engineWindow.setAspectRatio(wSize.x, wSize.y);
            glViewport(0,0, wSize.x, wSize.y);
        }
        //стираем буффер
        glClearColor(0.01f, 0.01f, 0.01f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shader.bind();
    }

    /**
     * заканчиваем отрисовку шейдера в цикле рендера
     * @param shader класс шейдера
     */
    public static void end(Shader shader) {
        shader.unBind();
    }

    /**
     * Отрисовка одного объекта
     * @param shader класс шейдера
     * @param gameObject класс объекта
     */
    public static void renderGameObj(Shader shader, GameObject gameObject) {
        if (gameObject != null) {
            if (gameObject.getVertexArray() != null) {
                if (gameObject.getTexture() != null) {
                    gameObject.getTexture().bind();
                }
                gameObject.getVertexArray().bind();
                shader.bind();
                shader.setUniformFromMat4f("u_ModelMatrix", gameObject.getModelMatrix());
                shader.setUniformFromMat4f("u_ViewMatrix", camera.getViewMatrix());
                shader.setUniformFromMat4f("u_ProjectionMatrix", engineWindow.getProjectionMatrix());
                //рисуем в буфере
                //glActiveTexture(GL_TEXTURE0); // Активируем текстурный блок перед привязкой текстуры
                glDrawElements(GL_TRIANGLES, gameObject.getIndexesSize(), GL_UNSIGNED_INT, 0);// to do 6 заменить на ???
                //glDrawElements(GL_TRIANGLES, ibo_quad.length, GL_UNSIGNED_INT, 0);
                shader.unBind();
                gameObject.getVertexArray().unBind();
                if (gameObject.getTexture() != null) {
                    gameObject.getTexture().unBind();
                }
            }
        }
    }

    /**
     * освобождаем ресурсы
     */
    public static void destroy() {
        glDisable(GL_BLEND);
    }
}
