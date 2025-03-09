package org.taylorivanoff.ironsights.ecs;

import java.util.*;

import org.taylorivanoff.ironsights.ecs.components.*;

public class Entity {
    private static int NEXT_ID = 0;
    public final int id = NEXT_ID++;
    private final Map<Class<? extends GameComponent>, GameComponent> components = new HashMap<>();

    public <T extends GameComponent> T addComponent(T component) {
        components.put(component.getClass(), component);
        return component;
    }

    public <T extends GameComponent> T getComponent(Class<T> type) {
        return type.cast(components.get(type));
    }

    public boolean hasComponent(Class<? extends GameComponent> type) {
        return components.containsKey(type);
    }
}