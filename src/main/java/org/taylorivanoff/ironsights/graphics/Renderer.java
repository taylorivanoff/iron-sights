// package org.taylorivanoff.ironsights.graphics;

// import org.joml.*;
// import static org.lwjgl.opengl.GL11.*;

// public class Renderer {
// private ShaderManager shaderManager;

// public Renderer() {
// shaderManager = ShaderManager.getInstance();
// }

// public void clear() {
// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
// }

// public void renderModel(Model model, Matrix4f modelMatrix, Camera camera) {
// Shader defaultShader = shaderManager.getShader("default");
// defaultShader.bind();

// // Set camera matrices
// defaultShader.setUniform4f("projection", camera.getProjectionMatrix());
// defaultShader.setUniform4f("view", camera.getViewMatrix());
// defaultShader.setUniform3f("viewPos", camera.getPosition());

// // Set lighting parameters
// defaultShader.setUniform3f("lightPos", new Vector3f(10.0f, 10.0f, 10.0f));
// defaultShader.setUniform3f("lightColor", new Vector3f(1.0f, 1.0f, 1.0f));
// defaultShader.setUniform1f("ambientStrength", 0.2f);

// model.render(modelMatrix, defaultShader);
// defaultShader.unbind();
// }

// public void cleanup() {
// shaderManager.cleanup();
// }
// }
