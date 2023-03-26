package ru.nikegreen.openGlGame1.renderer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BufferLayout {
    @Getter
    private List<VertexAttribute> attributes;

    @Getter
    private int stride = 0;

    public BufferLayout(VertexAttribute... attributes) {
        this.attributes = new ArrayList<>();
        this.attributes.addAll(Arrays.stream(attributes).toList());
    }

    public void calcOffsetAndStride() {
        int offset = 0;
        stride = 0;
        for (VertexAttribute vertexAttribute: attributes) {
            vertexAttribute.offset = offset;
            offset += vertexAttribute.size;
            stride += vertexAttribute.size;
        }
    }
}
