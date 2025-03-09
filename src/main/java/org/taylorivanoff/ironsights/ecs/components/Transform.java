package org.taylorivanoff.ironsights.ecs.components;

import java.lang.Math;

import org.joml.*;

public class Transform extends GameComponent {
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f(1, 1, 1);

    public Matrix4f getModelMatrix() {
        return new Matrix4f()
                .translate(position)
                .rotateXYZ(
                        (float) Math.toRadians(rotation.x),
                        (float) Math.toRadians(rotation.y),
                        (float) Math.toRadians(rotation.z))
                .scale(scale);
    }
}