package ru.nikegreen.openGlGame1.object;

import lombok.Getter;
import lombok.Setter;
import ru.nikegreen.openGlGame1.renderer.Texture;
import ru.nikegreen.openGlGame1.renderer.VertexArrayObj;
//import ru.nikegreen.openGlGame1.vector.Vector3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameObject {
    /**
     * количество вершин в треугольнике (размер массива индексов вершин)
     */
    @Getter
    @Setter
    private int indexesSize;
    /**
     * Буфер с матрицей вершин и матрицей индексов вершин
     */
    @Getter
    @Setter
    private VertexArrayObj vertexArray;
    /**
     * класс текстур
     */
    @Getter
    @Setter
    private Texture texture;
    /**
     * координаты объекта
     */
    @Getter
    @Setter
    protected Vector3f position;
    /**
     * вращение объекта
     */
    @Getter
    @Setter
    protected Vector3f rotation;
    /**
     * маштаб объекта
     */
    @Getter
    @Setter
    protected Vector3f scale;

    public Matrix4f modelMatrix;

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

    public GameObject buildModel(VertexArrayObj vertexArray) {
        this.vertexArray = vertexArray;
        return this;
    }

    public GameObject buildTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public GameObject buildIndexesSize(int length) {
        indexesSize = length;
        return this;
    }

    public Matrix4f getModelMatrix() {
        return new Matrix4f().identity()
                .translate(position)
                .rotateX( (float) Math.toRadians(rotation.x))
                .rotateY( (float) Math.toRadians(rotation.y))
                .rotateZ( (float) Math.toRadians(rotation.z))
                .scale(scale);
    }
}
