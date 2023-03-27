package ru.nikegreen.openGlGame1.renderer;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33C;
import ru.nikegreen.openGlGame1.Main;
import ru.nikegreen.openGlGame1.util.FileUtil;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

//import static org.lwjgl.opengl.GL11C.*;
//import static org.lwjgl.opengl.GL45C.glCreateTextures;
import static org.lwjgl.opengl.GL33C.*;
//import static org.lwjgl.opengles.GLES32.glTexStorage2D;
import static org.lwjgl.opengl.GL33C.glTexSubImage2D;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static ru.nikegreen.openGlGame1.util.FileUtil.separatorNormalizer;
import java.awt.image.BufferedImage;

public class Texture {
    @Getter
    private int id;
    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    private int channels;
    @Getter
    private ByteBuffer data;
    private String resource;
    private int format;
    private int internalFormat;

    public Texture(String resource) {
        this.resource = resource;
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer chan = BufferUtils.createIntBuffer(1);
        try {
            BufferedImage image = loadImage(resource);//The path is inside the jar file
            id = TextureLoader.loadTexture(image);
            //data = SOIL_load_image();
            //data = stbi_load_from_memory(resourceToByteBuffer(resource), w, h, chan, 0);
//            width = w.get();
//            height = h.get();
//            channels = chan.get(0);
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
//для openGL 4.5C
//        this.id = glCreateTextures(GL_TEXTURE_2D);
//        glTextureStorage2D(this.id, 1, this.internalFormat, this.width, this.height);
//
//        glTextureParameteri(this.id, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTextureParameteri(this.id, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//        glTextureParameteri(this.id, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
//        glTextureParameteri(this.id, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
//
//        glTextureSubImage2D(this.id, 0, 0, 0, this.width, this.height, this.format, GL_UNSIGNED_BYTE, data);

//        //id = glCreateTextures(GL_TEXTURE_2D); // openGL 4.5
//        id = glGenTextures(); // openGL 3.3
//        //glBindTexture(GL_TEXTURE_2D, id); //openGL 3.3
//        GL33C.glBindTexture(GL_TEXTURE_2D, id); //openGL 3.3
//        //glTextureStorage2D(id, 1, internalFormat, width, height);//openGL 4.5
//        //glTexStorage2D(GL_TEXTURE_2D, 1, internalFormat, width, height); // openGL 3.3
//
//        //glTextureParameteri(); //openGL 4.5C
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//        //glTexImage2D(GL_TEXTURE_2D, 0, this.internalFormat, this.width, this.height, 0, this.format, GL_UNSIGNED_BYTE, data);
//        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
//        glGenerateMipmap(GL_TEXTURE_2D);
////        glTexSubImage2D(GL_TEXTURE_2D,
////                0,
////                0,
////                0,
////                width,
////                height,
////                format,
////                GL_UNSIGNED_INT,
////                data);
//        glBindTexture(GL_TEXTURE_2D, 0);
        //unBind();

//        this.id = glGenTextures();
//        glBindTexture(GL_TEXTURE_2D, id);
//        //
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
//        //
//        //единственная наверно заметная разница, тут мы используем glTexImage2D вместо glTextureStorage2D и glTextureSubImage2D.
//        glTexImage2D(GL_TEXTURE_2D, 0, this.internalFormat, this.width, this.height, 0, this.format, GL_UNSIGNED_BYTE, data);
//
//        glBindTexture(GL_TEXTURE_2D,0);
//
//        if (data != null) {
//            stbi_image_free(data);
//        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unBind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void destroy() {
        GL11.glDeleteTextures(id);
        //glDestroyTextures(this.id);
    }

    private ByteBuffer resourceToByteBuffer(final String resource) throws IOException
    {
        ByteBuffer buffer = null;
        String resource1 = separatorNormalizer(resource);
//        File file = new File(resource);
//
//        FileInputStream fileInputStream = new FileInputStream(file);
//        FileChannel fileChannel = fileInputStream.getChannel();
//
//        ByteBuffer buffer = BufferUtils.createByteBuffer((int) fileChannel.size() + 1);
//
//        while (fileChannel.read(buffer) != -1) {
//            ;
//        }
//
//        fileInputStream.close();
//        fileChannel.close();
//        buffer.flip();
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(resource1);
            byte[] bytes = in.readAllBytes();
            byte[] bytes1 = new byte[bytes.length + 1];
            bytes1[bytes.length] = 0;
            System.out.println("bytes len=" + bytes.length);
            //buffer = BufferUtils.createByteBuffer((int) bytes.length + 1);
            //buffer.put(bytes);
            //buffer.flip();
            buffer = ByteBuffer.wrap(bytes1);
            buffer.flip();
            System.out.println("resourceToByteBuffer " + resource1);
        } catch (IOException e) {
            System.out.println("Файл " + resource + " не может прочитаться!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        return buffer;
    }

    public BufferedImage loadImage(String loc) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResource(loc));
        } catch (IOException e) {
            //Error Handling Here
        }
        return null;
    }
}
