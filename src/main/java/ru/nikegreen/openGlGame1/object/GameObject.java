package ru.nikegreen.openGlGame1.object;

import lombok.Getter;
import ru.nikegreen.openGlGame1.renderer.Texture;
import ru.nikegreen.openGlGame1.renderer.VertexArrayObj;
import ru.nikegreen.openGlGame1.vector.Vector3f;

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

    public GameObject setModel(VertexArrayObj vertexArray) {
        this.vertexArray = vertexArray;
        return this;
    }

    public GameObject setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }
}
