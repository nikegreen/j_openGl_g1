package ru.nikegreen.openGlGame1.vector;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Vector4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f(Vector4f vector4f) {
        x = vector4f.x;
        y = vector4f.y;
        z = vector4f.z;
        w = vector4f.w;
    }
}
