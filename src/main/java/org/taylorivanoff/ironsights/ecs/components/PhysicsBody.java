package org.taylorivanoff.ironsights.ecs.components;

import javax.vecmath.*;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;

public class PhysicsBody extends GameComponent {
    public RigidBody rigidBody;
    public CollisionShape collisionShape;
    public boolean isStatic = false;

    public void init() {
        // Mass calculation
        float mass = isStatic ? 0f : 1f;
        Vector3f inertia = new Vector3f();
        collisionShape.calculateLocalInertia(mass, inertia);

        // Create rigid body
        rigidBody = new RigidBody(
                new RigidBodyConstructionInfo(
                        mass,
                        new DefaultMotionState(),
                        collisionShape,
                        inertia));
    }
}
