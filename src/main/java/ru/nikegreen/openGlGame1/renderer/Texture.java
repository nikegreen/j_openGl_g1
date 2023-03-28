package ru.nikegreen.openGlGame1.renderer;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33C;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33C.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static ru.nikegreen.openGlGame1.util.FileUtil.separatorNormalizer;
import java.awt.image.BufferedImage;

/**
 * класс для использования текстур
 */
public class Texture {
    /**
     * ссылка на текстуру
     */
    @Getter
    private int id;
    /**
     * Ширина текстуры в пикселях
     */
    @Getter
    private int width;
    /**
     * высота текстуры в пикселях
     */
    @Getter
    private int height;
    /**
     * количество байт в пикселе
     */
    @Getter
    private int channels;

    /**
     * буфер с текстурой
     */
    @Getter
    private ByteBuffer data;

    /**
     * Путь и имя файла с текстурой
     */
    private String resource;

    /**
     * формат пикселя
     */
    private int format;

    /**
     * внутренний формат пикселя
     */
    private int internalFormat;

    /**
     * конструктор
     * @param resource путь и имя файла с текстурой (.png, .jpg)
     */
    public Texture(String resource) {
        this.resource = resource;
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer chan = BufferUtils.createIntBuffer(1);
        try {
            BufferedImage image = loadImage(resource);//The path is inside the jar file
            id = TextureLoader.loadTexture(image);
            width = image.getWidth();
            height = image.getHeight();
            channels = image.getColorModel().getPixelSize() / 8;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (channels == 4) {
            internalFormat = GL_RGBA8;
            format = GL_RGBA;
        } else if (channels == 3) {
            internalFormat = GL_RGB8;
            format = GL_RGB;
        } else {
            throw new IllegalArgumentException("Ошибка! Формат цветовых каналов не 3 и не 4! channels=" + channels);
        }
//        if (data != null) {
//            stbi_image_free(data);
//        }
    }

    /**
     * присоеденить
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * отсоеденить
     */
    public void unBind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     *  освободить ресурсы
     */
    public void destroy() {
        GL11.glDeleteTextures(id);
        //glDestroyTextures(this.id);
    }

    /**
     * Считать ресурс в буфер
     * @param resource путь к папке и файлу
     * @return буфер байтов
     * @throws IOException в случае ошибки ввода вывода из файла.
     */
    private ByteBuffer resourceToByteBuffer(final String resource) throws IOException
    {
        ByteBuffer buffer = null;
        String resource1 = separatorNormalizer(resource);
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(resource1);
            byte[] bytes = in.readAllBytes();
            byte[] bytes1 = new byte[bytes.length + 1];
            bytes1[bytes.length] = 0;
            buffer = ByteBuffer.wrap(bytes1);
            buffer.flip();
        } catch (IOException e) {
            System.out.println("Файл " + resource + " не может прочитаться!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        return buffer;
    }

    /**
     * Загружаем картинку как буфер-имедж
     * @param loc путь и имя файла с картинкой
     * @return тип {@link }
     */
    public BufferedImage loadImage(String loc) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResource(loc));
        } catch (IOException e) {
            //Error Handling Here
        }
        return null;
    }
}

/*
// без использования AWT
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.demo.util.IOUtils.ioResourceToByteBuffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

public class Texture{
    private int width;
    private int height;
    private int id;

    public Texture(String imagePath) {
        ByteBuffer imageData = ioResourceToByteBuffer(imagePath, 1024);

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer components = stack.mallocInt(1);

            // Decode texture image into a byte buffer
            ByteBuffer decodedImage = stbi_load_from_memory(imageData, w, h, components, 4);

            this.width = w.get();
            this.height = h.get();

            // Create a new OpenGL texture
            this.id = glGenTextures();

            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, this.id);

            // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            // Upload the texture data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, decodedImage);

            // Generate Mip Map
            glGenerateMipmap(GL_TEXTURE_2D);
        }
    }
}
*/