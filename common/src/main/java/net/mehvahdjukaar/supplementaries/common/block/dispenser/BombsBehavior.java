package net.mehvahdjukaar.supplementaries.common.block.dispenser;

import net.mehvahdjukaar.supplementaries.common.block.fire_behaviors.ProjectileStats;
import net.mehvahdjukaar.supplementaries.common.entities.BombEntity;
import net.mehvahdjukaar.supplementaries.common.items.BombItem;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

class BombsBehavior extends ProjectileDispenseBehavior {

    public BombsBehavior(Item projectile) {
        super(projectile);
    }

}

