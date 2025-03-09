package org.taylorivanoff.ironsights.ecs.components;

import java.lang.Math;

import org.joml.*;

public class Camera extends GameComponent {
        public Matrix4f projection = new Matrix4f();
        public Matrix4f view = new Matrix4f();

        public void updateProjection(int width, int height) {
                projection.setPerspective(
                                (float) Math.toRadians(70.0f),
                                (float) width / height,
                                0.1f,
                                1000.0f);
        }
}