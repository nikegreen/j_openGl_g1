package ru.nikegreen.openGlGame1.renderer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30C.*;
//import static org.lwjgl.opengl.GL45C.glCreateVertexArrays;

public class VertexArrayObj {
    @Getter
    private int id;

    @Getter
    private List<VertexBufferObj> vbos = new ArrayList<>();

    @Getter
    private List<IndexBufferObj> ibos = new ArrayList<>();

    public VertexArrayObj() {
        this.create();
    }

    private void create() {
//        this.id = glCreateVertexArrays(); // openGL 4.5C
        this.id = glGenVertexArrays(); // openGL 3.1C
        this.bind();
    }

    public void destroy() {
        vbos.forEach(vbo->vbo.destroy());
        vbos.clear();
        glDeleteVertexArrays(this.id);
    }

    public void bind() {
        glBindVertexArray(this.id);
    }

    public void unBind() {
        glBindVertexArray(0);
    }

    public void putBuffer(VertexBufferObj vertexBufferObj) {
        int attributeId = vertexBufferObj.getLayout().prepareBuffer(0);
        vbos.add(vertexBufferObj);
    }

    public void putBuffer(IndexBufferObj indexBufferObj) {
        ibos.add(indexBufferObj);
    }

}
