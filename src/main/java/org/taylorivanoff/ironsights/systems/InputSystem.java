package org.taylorivanoff.ironsights.systems;

import static org.lwjgl.glfw.GLFW.*;
import org.taylorivanoff.ironsights.ecs.*;

public class InputSystem extends GameSystem {
    private static boolean[] keys = new boolean[GLFW_KEY_LAST + 1];
    private static double mouseX, mouseY;
    private static double mouseDX, mouseDY;
    private static double lastMouseX, lastMouseY;
    private static boolean firstMouse = true;
    private static long windowHandle;
    private static boolean cursorLocked = false;
    public static boolean ignoreInput = true;
    private static boolean ignoreNextMouseInput = false;
    private boolean focused = false;

    public InputSystem(long window) {
        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            keys[key] = action != GLFW_RELEASE;

            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                setCursorLocked(false);
            }
        });

        glfwSetCursorPosCallback(window, (w, xPos, yPos) -> {
            mouseX = xPos;
            mouseY = yPos;

            if (firstMouse || !cursorLocked) {
                lastMouseX = mouseX;
                lastMouseY = mouseY;
                firstMouse = false;
            }
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                if (!isCursorLocked() && focused) {
                    setCursorLocked(true);
                }
            }
        });

        glfwSetWindowFocusCallback(window, (w, focused) -> {
            if (!focused) {
                setCursorLocked(false);
            }
        });

        setCursorLocked(false);
        ignoreInput = true;
    }

    @Override
    public void update(float deltaTime) {
        if (ignoreInput)
            return;

        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return;
        }

        mouseDX = mouseX - lastMouseX;
        mouseDY = mouseY - lastMouseY;

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    public static boolean isInputIgnored() {
        return ignoreInput;
    }

    public static void setCursorLocked(boolean locked) {
        cursorLocked = locked;
        glfwSetInputMode(windowHandle, GLFW_CURSOR,
                locked ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);

        if (locked) {
            // Center cursor without creating delta
            int[] width = new int[1], height = new int[1];
            glfwGetWindowSize(windowHandle, width, height);
            double centerX = width[0] / 2.0;
            double centerY = height[0] / 2.0;

            glfwSetCursorPos(windowHandle, centerX, centerY);

            lastMouseX = centerX;
            lastMouseY = centerY;
            mouseX = centerX;
            mouseY = centerY;

            ignoreNextMouseInput = true;
        }

        ignoreInput = !locked;
    }

    public static boolean isCursorLocked() {
        return cursorLocked;
    }

    public static boolean isKeyDown(int key) {
        return !ignoreInput && keys[key];
    }

    public static double getMouseDX() {
        return mouseDX;
    }

    public static double getMouseDY() {
        return mouseDY;
    }

}
