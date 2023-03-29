package ru.nikegreen.openGlGame1.renderer;

import lombok.Getter;
import lombok.Setter;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL45C.glCreateBuffers;
import static org.lwjgl.opengles.GLES20.GL_STATIC_DRAW;

public class VertexBufferObj {
    @Getter
    private int id;
    @Getter
    private float[] data;
    @Getter
    private int usage;
    @Setter
    @Getter
    private BufferLayout layout;

    public VertexBufferObj(float[] data) {
        this.data = data;
        this.usage = GL_STATIC_DRAW;
        this.create();
        this.putData();
    }

    private void putData() {
        glBufferData(GL_ARRAY_BUFFER, this.data, this.usage);
    }

    private void create() {
//        this.id = glCreateBuffers(); //openGL 4.5C
        this.id = glGenBuffers(); //openGL 3.3
        bind();
    }

    public void destroy() {
        glDeleteBuffers(this.id);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, this.id);
    }

    public void unBind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
