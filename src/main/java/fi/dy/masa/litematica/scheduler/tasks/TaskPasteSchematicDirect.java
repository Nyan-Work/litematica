package fi.dy.masa.litematica.scheduler.tasks;

import net.minecraft.world.WorldServer;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.litematica.schematic.util.SchematicPlacingUtils;
import fi.dy.masa.malilib.render.message.MessageType;
import fi.dy.masa.malilib.render.message.MessageUtils;
import fi.dy.masa.malilib.util.position.LayerRange;
import fi.dy.masa.malilib.util.WorldUtils;

public class TaskPasteSchematicDirect extends TaskBase
{
    private final SchematicPlacement placement;
    private final LayerRange range;

    public TaskPasteSchematicDirect(SchematicPlacement placement, LayerRange range)
    {
        this.placement = placement;
        this.range = range;
    }

    @Override
    public boolean canExecute()
    {
        return this.placement.isInvalidated() == false &&
               this.mc.world != null &&
               this.mc.player != null &&
               this.mc.isSingleplayer();
    }

    @Override
    public boolean execute()
    {
        WorldServer world = this.mc.getIntegratedServer().getWorld(WorldUtils.getDimensionId(this.mc.world));

        if (world != null && SchematicPlacingUtils.placeToWorld(this.placement, world, this.range, false))
        {
            this.finished = true;
        }

        return true;
    }

    @Override
    public void stop()
    {
        if (this.finished)
        {
            if (this.printCompletionMessage)
            {
                MessageUtils.showGuiOrActionBarMessage(MessageType.SUCCESS, "litematica.message.schematic_pasted");
            }
        }
        else
        {
            MessageUtils.showGuiOrInGameMessage(MessageType.ERROR, "litematica.message.error.schematic_paste_failed");
        }

        super.stop();
    }
}
