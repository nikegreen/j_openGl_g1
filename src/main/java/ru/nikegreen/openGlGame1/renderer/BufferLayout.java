package ru.nikegreen.openGlGame1.renderer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static ru.nikegreen.openGlGame1.renderer.VertexAttribute.convertShaderTypeToOpenGL;

/**
 * слой для шейдера с несколькими атрибутами
 */
public class BufferLayout {
    @Getter
    private List<VertexAttribute> attributes;

    @Getter
    private int stride = 0;

    public BufferLayout(VertexAttribute... attributes) {
        this.attributes = new ArrayList<>();
        for (VertexAttribute va: attributes) {
            this.attributes.add(va);
        }
        //this.attributes.addAll(Arrays.stream(attributes).toList()); // new version java
        calcOffsetAndStride();
    }

    /**
     * вычисляем смещение для атрибутов и общий размер всех атрибутов
     */
    private void calcOffsetAndStride() {
        int offset = 0;
        stride = 0;
        for (VertexAttribute vertexAttribute: attributes) {
            vertexAttribute.setOffset(offset);
            offset += vertexAttribute.getSizeInBytes();
            stride += vertexAttribute.getSizeInBytes();
        }
    }

    /**
     * устанавливает атрибут шейдера
     * (шейдер может состоять из нескольких атрибутов)
     * @param attributeId номер атрибута шейдера
     */
    public void addAttribute(
            int attributeId
    ) {
        glEnableVertexAttribArray(attributeId);
        VertexAttribute attribute = attributes.get(attributeId);
        glVertexAttribPointer(
                attributeId,
                attribute.getSize(), //                attribute.getElementAttribSize(attribute.type),
                convertShaderTypeToOpenGL(attribute.getType()), // convertShaderTypeToOpenGL(attribute.type), //тип openGL
                attribute.getNormalized(), //normalize
                stride, //
                attribute.getOffset() //смещение
        );
    }

    /**
     * Приготавливает буфера для отрисовки шейдеров
     * @param attributeId номер атрибута шейдера
     * @return следущий свободный номер атрибута шейдера.
     */
    public int prepareBuffer(int attributeId) {
        for (VertexAttribute attribute : getAttributes()) {
            addAttribute(attributeId++);
        }
        return attributeId;
    }
}
