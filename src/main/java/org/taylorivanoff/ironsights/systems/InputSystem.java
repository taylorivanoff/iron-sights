package org.taylorivanoff.ironsights.systems;

import java.lang.Math;

import org.joml.*;
import static org.lwjgl.glfw.GLFW.*;
import org.taylorivanoff.ironsights.ecs.*;
import org.taylorivanoff.ironsights.ecs.components.*;

public class InputSystem extends GameSystem {
    private long window;
    private final Vector2f mouseDelta = new Vector2f();
    private final Vector2f lastMousePos = new Vector2f();

    public InputSystem(long window) {
        this.window = window;
        glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
            mouseDelta.set((float) xpos - lastMousePos.x, (float) ypos - lastMousePos.y);
            lastMousePos.set((float) xpos, (float) ypos);
        });
    }

    public void update(float deltaTime) {
        // Process player input
        Entity player = EntityManager.get().getEntities().stream()
                .filter(e -> e.hasComponent(Player.class))
                .findFirst()
                .orElse(null);

        if (player != null) {
            Transform t = player.getComponent(Transform.class);
            Camera cam = player.getComponent(Camera.class);

            // Mouse look
            Vector2f delta = new Vector2f(mouseDelta).mul(0.1f);
            t.rotation.y += delta.x;
            t.rotation.x = Math.max(-89, Math.min(89, t.rotation.x - delta.y));

            // Keyboard movement
            Vector3f movement = new Vector3f();
            if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
                movement.z -= 1;
            if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
                movement.z += 1;
            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
                movement.x -= 1;
            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
                movement.x += 1;

            movement.normalize().mul(5 * deltaTime);
            t.position.add(movement.rotateY((float) Math.toRadians(t.rotation.y)));

            // Update camera view matrix
            cam.view.identity()
                    .rotateXYZ(
                            (float) Math.toRadians(-t.rotation.x),
                            (float) Math.toRadians(-t.rotation.y),
                            0)
                    .translate(-t.position.x, -t.position.y, -t.position.z);
        }

        mouseDelta.set(0, 0);
    }
}