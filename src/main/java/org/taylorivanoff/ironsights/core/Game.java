package org.taylorivanoff.ironsights.core;

import org.taylorivanoff.ironsights.ecs.*;
import org.taylorivanoff.ironsights.ecs.components.*;
import org.taylorivanoff.ironsights.systems.*;

public class Game {
    private Window window;

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        window = new Window(1280, 720, "Iron Sights");
    }

    /**
     * ░█▀▀░█▀█░█▄█░█▀▀░░░█░░░█▀█░█▀█░█▀█
     * ░█░█░█▀█░█░█░█▀▀░░░█░░░█░█░█░█░█▀▀
     * ░▀▀▀░▀░▀░▀░▀░▀▀▀░░░▀▀▀░▀▀▀░▀▀▀░▀░░
     */
    private void loop() {
        EntityManager em = EntityManager.get();
        em.addSystem(new InputSystem(window.getWindowHandle()));
        em.addSystem(new RenderSystem());

        // Create player entity
        Entity player = em.createEntity();

        // Add components to the player
        player.addComponent(new Transform()); // Position, rotation, scale
        player.addComponent(new Camera()); // View and projection matrices
        player.addComponent(new Player()); // Player-specific data (optional)

        // Initialize camera projection
        Camera cam = player.getComponent(Camera.class);
        cam.updateProjection(window.getWidth(), window.getHeight());

        // Main loop
        while (!window.shouldClose()) {
            Time.update();

            em.updateSystems(Time.getDeltaTime());

            window.update();
        }
    }

    private void cleanup() {
        window.cleanup();
    }

    public static void main(String[] args) {
        new Game().run();
    }
}