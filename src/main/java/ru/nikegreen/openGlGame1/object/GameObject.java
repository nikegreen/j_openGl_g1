package ru.nikegreen.openGlGame1.object;

import lombok.Getter;
import org.lwjgl.ovr.OVRMatrix4f;
import ru.nikegreen.openGlGame1.engine.Engine;
import ru.nikegreen.openGlGame1.renderer.Texture;
import ru.nikegreen.openGlGame1.renderer.VertexArrayObj;
//import ru.nikegreen.openGlGame1.vector.Vector3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameObject {
    @Getter
    private VertexArrayObj vertexArray;
    @Getter
    private Texture texture;
    @Getter
    protected Vector3f position;
    @Getter
    protected Vector3f rotation;
    @Getter
    protected Vector3f scale;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = new Vector3f(position);
        this.rotation = new Vector3f(rotation);
        this.scale = new Vector3f(scale);
    }

    public void destroy() {
        if (vertexArray != null) {
            vertexArray.destroy();
        }
    }

    public GameObject setModel(VertexArrayObj vertexArray) {
        this.vertexArray = vertexArray;
        return this;
    }

    public GameObject setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public Matrix4f getModelMatrix() {
        return new Matrix4f()
                .translate(position)
                .rotate(rotation.x, new Vector3f(1, 0, 0))
                .rotate(rotation.y, new Vector3f(0, 1, 0))
                .rotate(rotation.z, new Vector3f(0, 0, 1))
                .scale(scale);
    }
}
