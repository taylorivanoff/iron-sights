package org.taylorivanoff.ironsights.ecs;

import java.util.*;

public class EntityManager {
    private static EntityManager instance;
    private final List<Entity> entities = new ArrayList<>();
    private final List<GameSystem> systems = new ArrayList<>();

    public static EntityManager get() {
        if (instance == null)
            instance = new EntityManager();
        return instance;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Entity createEntity() {
        Entity e = new Entity();
        getEntities().add(e);
        return e;
    }

    public void addSystem(GameSystem system) {
        systems.add(system);
    }

    public void updateSystems(float deltaTime) {
        systems.forEach(system -> system.update(deltaTime));
    }
}