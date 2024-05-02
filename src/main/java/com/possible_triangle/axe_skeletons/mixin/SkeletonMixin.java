package com.possible_triangle.axe_skeletons.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeleton.class)
public class SkeletonMixin {

    @Redirect(
            method = "populateDefaultEquipmentSlots",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/AbstractSkeleton;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V")
    )
    private void replaceEquipment(AbstractSkeleton instance, EquipmentSlot equipmentSlot, ItemStack itemStack) {
        if(instance.getRandom().nextInt(3) > 0) {
            instance.setItemSlot(equipmentSlot, new ItemStack(Items.WOODEN_AXE));
        } else {
            instance.setItemSlot(equipmentSlot, itemStack);
        }
    }

}
