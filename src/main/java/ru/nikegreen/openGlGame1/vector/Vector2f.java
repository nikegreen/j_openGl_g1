package ru.nikegreen.openGlGame1.vector;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Vector2f {
    public float x;
    public float y;

    public Vector2f(Vector2f vector2f) {
        x = vector2f.x;
        y = vector2f.y;
    }
}
