package ru.nikegreen.openGlGame1.engine;

import ru.nikegreen.openGlGame1.object.GameObject;
import ru.nikegreen.openGlGame1.renderer.*;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.*;

public class RenderEngine {
    public static void init() {
        ///////////////////////////////////////////////////////
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void begin(Shader shader) {
        //стираем буффер
        glClearColor(0, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        shader.bind();
    }

    public static void end(Shader shader) {
        shader.unBind();
    }

    public static void renderGameObj(Shader shader, GameObject gameObject) {
        if (gameObject != null) {
            if (gameObject.getVertexArray() != null) {
                if (gameObject.getTexture() != null) {
                    gameObject.getTexture().bind();
                }
                gameObject.getVertexArray().bind();
                shader.bind();
                //рисуем в буфере
                //glActiveTexture(GL_TEXTURE0); // Активируем текстурный блок перед привязкой текстуры
                glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);// to do 6 заменить на ???
                //glDrawElements(GL_TRIANGLES, ibo_quad.length, GL_UNSIGNED_INT, 0);
                shader.unBind();
                gameObject.getVertexArray().unBind();
                if (gameObject.getTexture() != null) {
                    gameObject.getTexture().unBind();
                }
            }
        }
    }

    public static void destroy() {

    }
}
