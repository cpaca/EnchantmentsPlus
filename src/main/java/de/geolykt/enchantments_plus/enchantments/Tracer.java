package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.arrows.EnchantedArrow;
import de.geolykt.enchantments_plus.arrows.enchanted.TracerArrow;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.util.Tool;

import java.util.HashMap;
import java.util.Map;

public class Tracer extends CustomEnchantment {

    // Map of tracer arrows to their expected accuracy
    public static final Map<Arrow, Integer> tracer = new HashMap<>();
    public static final int                 ID     = 63;

    @Override
    public Builder<Tracer> defaults() {
        return new Builder<>(Tracer::new, ID)
            .all(BaseEnchantments.TRACER,
                    0,
                    "Guides the arrow to targets and then attacks",
                    new Tool[]{Tool.BOW},
                    "Tracer",
                    4, // MAX LVL
                    1.0,
                    Hand.RIGHT);
    }

    @Override
    public boolean onEntityShootBow(EntityShootBowEvent evt, int level, boolean usedHand) {
        TracerArrow arrow = new TracerArrow((Arrow) evt.getProjectile(), level, power);
        EnchantedArrow.putArrow((Arrow) evt.getProjectile(), arrow, (Player) evt.getEntity());
        return true;
    }

    // Moves Tracer arrows towards a target
    public static void tracer() {
        for (Arrow e : tracer.keySet()) {
            Entity close = null;
            double distance = 100;
            int level = tracer.get(e);
            level += 2;
            for (Entity e1 : e.getNearbyEntities(level, level, level)) {
                if (e1.getLocation().getWorld().equals(e.getLocation().getWorld())) {
                    double d = e1.getLocation().distance(e.getLocation());
                    if (e.getLocation().getWorld().equals(((Entity) e.getShooter()).getLocation().getWorld())) {
                        if (d < distance && e1 instanceof LivingEntity
                            && !e1.equals(e.getShooter())
                            && e.getLocation().distance(((Entity) e.getShooter()).getLocation()) > 15) {
                            distance = d;
                            close = e1;
                        }
                    }
                }
            }
            if (close != null) {
                Location location = close.getLocation();
                org.bukkit.util.Vector v = new org.bukkit.util.Vector(0D, 0D, 0D);
                Location pos = e.getLocation();
                double its = location.distance(pos);
                if (its == 0) {
                    its = 1;
                }
                v.setX((location.getX() - pos.getX()) / its);
                v.setY((location.getY() - pos.getY()) / its);
                v.setZ((location.getZ() - pos.getZ()) / its);
                v.add(e.getLocation().getDirection().multiply(.1));
                e.setVelocity(v.multiply(2));
            }
        }
    }
}
