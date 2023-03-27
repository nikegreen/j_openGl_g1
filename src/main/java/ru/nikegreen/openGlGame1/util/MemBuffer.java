package ru.nikegreen.openGlGame1.util;

import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static org.lwjgl.system.MemoryStack.stackGet;

public class MemBuffer {
//    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
//        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
//        buffer.put(data);
//        buffer.flip();
//        return buffer;
//    }
//
//    public static IntBuffer storeDataInIntBuffer(int[] data) {
//        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
//        buffer.put(data);
//        buffer.flip();
//        return buffer;
//    }

    /**
     * Складывем массив в память стека openGL
     * @param data массив long
     * @return буфер массива long
     */
    public static LongBuffer putData(long[] data) {
        return (LongBuffer) stackGet().mallocLong(8 * data.length)
                .put(data)
                .flip();
    }

    /**
     * Складывем массив в память стека openGL
     * @param data массив int
     * @return буфер массива int
     */
    public static IntBuffer putData(int[] data) {
        return (IntBuffer) stackGet().mallocInt(4 * data.length)
                .put(data)
                .flip();
    }


    /**
     * Складывем массив в память стека openGL
     * @param data массив float
     * @return буфер массива float
     */
    public static FloatBuffer putData(float[] data) {
        return (FloatBuffer) stackGet().mallocFloat(4 * data.length)
                .put(data)
                .flip();
    }

    /**
     * Складывем массив в память стека openGL
     * @param data массив double
     * @return буфер массива double
     */
    public static DoubleBuffer putData(double[] data) {
        return (DoubleBuffer) stackGet().mallocDouble(8 * data.length)
                .put(data)
                .flip();
    }


}
