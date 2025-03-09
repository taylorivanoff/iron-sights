package org.taylorivanoff.ironsights.graphics;

import java.io.*;
import java.nio.*;
import java.nio.file.*;

import org.joml.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.*;

public class Shader {
    private final int programId;

    public Shader(String vertexPath, String fragmentPath) {
        // Load and compile shaders
        int vertexShader = loadShader(vertexPath, GL_VERTEX_SHADER);
        int fragmentShader = loadShader(fragmentPath, GL_FRAGMENT_SHADER);

        // Link shaders into a program
        programId = glCreateProgram();
        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);
        glLinkProgram(programId);

        // Check for linking errors
        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader program linking failed: " + glGetProgramInfoLog(programId));
        }

        // Clean up shaders
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    private int loadShader(String path, int type) {
        String source = loadFile(path);
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader compilation failed: " + glGetShaderInfoLog(shader));
        }

        return shader;
    }

    private String loadFile(String path) {
        // Load file content as a string
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader file: " + path, e);
        }
    }

    public void setUniform4f(String name, Matrix4f value) {
        int location = GL20.glGetUniformLocation(programId, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            GL20.glUniformMatrix4fv(location, false, buffer);
        }
    }

    public void setUniform3f(String name, Vector3f value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    public void setUniform1f(String name, float value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform1f(location, value);
    }

    public void setUniform1i(String name, int value) {
        int location = GL20.glGetUniformLocation(programId, name);
        GL20.glUniform1i(location, value);
    }

}
