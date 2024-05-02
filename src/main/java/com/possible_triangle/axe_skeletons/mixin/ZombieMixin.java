package com.possible_triangle.axe_skeletons.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Zombie.class)
public class ZombieMixin {

    @Unique
    private static final AttributeModifier HEALTH_MODIFIER_BABY = new AttributeModifier(UUID.fromString("75537bd6-4648-484f-9d42-3297d3024e1a"), "Baby health nerf", -0.75, AttributeModifier.Operation.MULTIPLY_BASE);

    @Inject(
            method = "setBaby",
            at = @At(value = "HEAD")
    )
    private void modifyBabyHealth(boolean value, CallbackInfo ci) {
        var self = (Zombie) (Object) this;

        if (self.level() != null && !self.level().isClientSide) {
            AttributeInstance attributeInstance = self.getAttribute(Attributes.MAX_HEALTH);
            attributeInstance.removeModifier(HEALTH_MODIFIER_BABY);
            if (value) {
                attributeInstance.addTransientModifier(HEALTH_MODIFIER_BABY);
                self.setHealth(Math.min(self.getHealth(), self.getMaxHealth()));
            }
        }
    }

}
