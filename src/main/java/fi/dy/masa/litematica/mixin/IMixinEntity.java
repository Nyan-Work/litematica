package fi.dy.masa.litematica.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface IMixinEntity {
    @Accessor("world")
    void litematica_setWorld(World world);
}
