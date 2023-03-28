package ru.nikegreen.openGlGame1.vector;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Vector3f {
    public float x;
    public float y;
    public float z;

    public Vector3f(Vector3f vector3f) {
        x = vector3f.x;
        y = vector3f.y;
        z = vector3f.z;
    }
}
