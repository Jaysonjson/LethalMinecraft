package json.jayson.mixin;

import json.jayson.LM;
import json.jayson.LMClient;
import json.jayson.client.model.ItemModelRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
       //this.addModel(new ModelIdentifier(LM.ID, "3d/golden_yield_sign", "inventory"));
        for (Item item : LMClient.ITEM_MODELS.getModels().keySet()) {
            Identifier itemId = Registries.ITEM.getId(item);
            ItemModelRegistry.ModelData data = LMClient.ITEM_MODELS.getModel(item);
            this.addModel(new ModelIdentifier(itemId.getNamespace(), data.getPath().isEmpty() ? "3d/" + itemId.getPath() : data.getPath(), data.getVariant()));
        }
    }

}