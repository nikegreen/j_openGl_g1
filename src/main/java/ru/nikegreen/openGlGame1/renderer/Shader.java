package ru.nikegreen.openGlGame1.renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nikegreen.openGlGame1.vector.*;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL30C.*;

public class Shader {
    public int programId;
    public Shader(String vsSrc, String fsSrc) {
        Map<Integer, String> shaderSources = new HashMap<>(2);
        shaderSources.put(1, this.readFile(vsSrc));
        shaderSources.put(2, this.readFile(fsSrc));
        compile(shaderSources);
    }

    public void compile(Map<Integer, String> shaderSources) {
        int program = glCreateProgram();
        List<Integer> shaderIds = new ArrayList<>();
        int shaderIdIdxs = 1;

        for (int i = 0; i < shaderSources.size(); i++) {
            int type = i == 0 ? GL_VERTEX_SHADER : i == 1 ? GL_FRAGMENT_SHADER : -1;
            String source = shaderSources.get(shaderIdIdxs);
            //создание shader
            int shader = glCreateShader(type);
            glShaderSource(shader, source);
            glCompileShader(shader);
            //проверяем компиляцию шэйдера
            int isCompile = 0;
            isCompile = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (isCompile == GL_FALSE) { //ошибка
                //получаем длину строки с ошибкой
                int maxLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
                String infoLog = "";
                //получаем строку с ошибкой
                infoLog = glGetShaderInfoLog(shader, maxLength);
                glDeleteShader(shader);
                //лог ошибки
                String sh = type == 0 ? "вершинный шейдер" : "фрагментный шейдер";
                System.out.println("Не компилируется " + sh + ":" + infoLog);
                System.exit(-2);
            }
            glAttachShader(program, shader);
            shaderIdIdxs++;
        }
        //объединяем
        glLinkProgram(program);
        int isLinked = 0;
        //проверка объединения
        isLinked = glGetProgrami(program, GL_LINK_STATUS);
        if (isLinked == GL_FALSE) { //ошибка объединения
            //получаем длину строки с ошибкой
            int maxLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
            String infoLog = "";
            //получаем строку с ошибкой
            infoLog = glGetProgramInfoLog(program, maxLength);
            for (int shaderId : shaderIds) {
                glDeleteShader(shaderId);
            }
            //лог ошибки
            System.out.println("Не линкуется программа:" + infoLog);
            System.exit(-3);
        }
        for (int shaderId : shaderIds) {
            glDetachShader(program, shaderId);
        }
        programId = program;
    }

    public String readFile(String fileName) {
        boolean appendSlashes = false;
        boolean returnInOneLine = false;
        StringBuilder shaderSource = new StringBuilder();
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line);
                if (appendSlashes) {
                    shaderSource.append("//");
                }
                if (!returnInOneLine) {
                    shaderSource.append(System.lineSeparator());
                }
            }
            reader.close();
            return  shaderSource.toString();
        } catch (IOException e) {
            System.out.println("Файл " + fileName + " не может прочитаться!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        return "Ошибка чтения файла: Файл " + fileName + " не может прочитаться!";
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unBind() {
        glUseProgram(0);
    }

    public void setUniformFromInt(String name, int value) {
        glUniform1i(glGetUniformLocation(programId, name), value);
    }

    public void setUniformFromInt2(String name, int value, int value2) {
        glUniform2i(glGetUniformLocation(programId, name), value, value2);
    }

    public void setUniformFromInt3(String name, int value, int value2, int value3) {
        glUniform3i(glGetUniformLocation(programId, name), value, value2, value3);
    }

    public void setUniformFromInt4(String name, int value, int value2, int value3, int value4) {
        glUniform4i(glGetUniformLocation(programId, name), value, value2, value3, value4);
    }

    public void setUniformFromFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(programId, name), value);
    }

    public void setUniformFromFloat2(String name, float value, float value2) {
        glUniform2f(glGetUniformLocation(programId, name), value, value2);
    }

    public void setUniformFromFloat3(String name, float value, float value2, float value3) {
        glUniform3f(glGetUniformLocation(programId, name), value, value2, value3);
    }

    public void setUniformFromFloat4(String name, float value, float value2, float value3, float value4) {
        glUniform4f(glGetUniformLocation(programId, name), value, value2, value3, value4);
    }

    public void setUniformFromBoolean(String name, boolean value) {
        glUniform1i(glGetUniformLocation(programId, name),
                value == true ? 1 : 0);
    }

    public void setUniformFromVec2f(String name, Vector2f value) {
        glUniform2f(glGetUniformLocation(programId, name), value.x, value.y);
    }

    public void setUniformFromVec3f(String name, Vector3f value) {
        glUniform3f(glGetUniformLocation(programId, name), value.x, value.y, value.z);
    }

    public void setUniformFromVec2f(String name, Vector4f value) {
        glUniform4f(glGetUniformLocation(programId, name), value.x, value.y, value.z, value.w);
    }
}
