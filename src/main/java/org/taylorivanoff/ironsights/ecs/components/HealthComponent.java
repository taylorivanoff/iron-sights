package org.taylorivanoff.ironsights.ecs.components;

public class HealthComponent extends GameComponent {
    public float maxHealth = 100f;
    public float currentHealth = maxHealth;
    public boolean isInvulnerable = false;

    public void takeDamage(float amount) {
        if (!isInvulnerable) {
            currentHealth = Math.max(0, currentHealth - amount);
            // EventBus.publish(new HealthChangedEvent(this));
        }
    }
}