package org.taylorivanoff.ironsights.core;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.taylorivanoff.ironsights.ecs.*;
import org.taylorivanoff.ironsights.ecs.components.*;
import org.taylorivanoff.ironsights.systems.*;

public class Game {
    private long window;
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create window
        window = glfwCreateWindow(WIDTH, HEIGHT, "FPS Game", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        // Setup OpenGL context
        glfwMakeContextCurrent(window);

        // Enable VSync
        glfwSwapInterval(1);

        // Show window
        glfwShowWindow(window);

        GL.createCapabilities();

        // Set clear color
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glEnable(GL_DEPTH_TEST);
    }

    private void loop() {

        RenderSystem renderSystem = new RenderSystem();
        renderSystem.init(); // Initialize the shader

        // Initialize ECS
        EntityManager em = EntityManager.get();
        em.addSystem(new InputSystem(window));
        em.addSystem(new RenderSystem());

        // Create player
        Entity player = em.createEntity();
        player.addComponent(new Transform());
        player.addComponent(new Camera());

        // Initialize camera projection
        player.getComponent(Camera.class).updateProjection(WIDTH, HEIGHT);

        // Main loop
        while (!glfwWindowShouldClose(window)) {
            Time.update();

            // Update systems
            em.updateSystems(Time.getDeltaTime());

            // Swap buffers
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void cleanup() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void main(String[] args) {
        new Game().run();
    }
}