package ru.nikegreen.openGlGame1.engine;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;

/**
 * Класс для задания параметров камеры
 * положения
 */
public class Camera {
    /**
     * матрица для преобразования мировых координат в координаты относительно камеры
     */
    private Matrix4f viewMatrix;

    /**
     * координаты камеры
     */
    @Getter
    private Vector3f position;

    /**
     * Угол поворота камеры
     */
    @Getter
    private Vector3f rotation;

    /**
     * актуальность матрицы viewMatrix
     */
    @Getter
    private boolean actual;

    public Camera() {
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        calculate();
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
        calculate();
    }

    public void setPosition(float x, float y, float z) {
        actual = false;
        position = new Vector3f(-x, -y, -z);
    }
    public void setPosition(Vector3f v) {
        actual = false;
        position = new Vector3f().sub(v);
    }

    public void setRotation(float x, float y, float z) {
        actual = false;
        rotation = new Vector3f(-x, -y, -z);
    }
    public void setRotation(Vector3f v) {
        actual = false;
        rotation = new Vector3f().sub(v);
    }

    public Camera buildPosition(float x, float y, float z) {
        setPosition(x, y, z);
        return this;
    }
    public Camera buildPosition(Vector3f v) {
        setPosition(v);
        return this;
    }

    public Camera buildRotation(float x, float y, float z) {
        setRotation(x, y, z);
        return this;
    }
    public Camera buildRotation(Vector3f v) {
        setRotation(v);
        return this;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
        actual = true;
    }
    public Camera buildViewMatrix(Matrix4f viewMatrix) {
        setViewMatrix(viewMatrix);
        return this;
    }

    public void calculate() {
        Matrix4f m = new Matrix4f().identity()
                .translate(position)
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z));
        setViewMatrix(m);
    }

    /**
     * Вернуть рассчитанную матрицу
     * (коррекция координат мира с позиции камеры)
     * @return матрица
     */
    public Matrix4f getViewMatrix() {
        if (!actual) {
            calculate();
        }
        return viewMatrix;
    }
}
