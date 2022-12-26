package me.nokko.cpr.mixin;

import me.nokko.cpr.init.ModRegistries;
import me.nokko.cpr.item.ClockworkComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ClockworkComponentHoverText {
    @Inject(at = @At(value = "HEAD"),
            method = "appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V")
    public void addTextIfRegistered(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced, CallbackInfo ci) {
        var itemName = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (ModRegistries.EXTRA_COMPONENTS.containsKey(itemName)) {
            ClockworkComponent.addBasicTooltip(ModRegistries.EXTRA_COMPONENTS.get(itemName).makeComponent(stack), tooltipComponents, isAdvanced);
        }
    }

}
