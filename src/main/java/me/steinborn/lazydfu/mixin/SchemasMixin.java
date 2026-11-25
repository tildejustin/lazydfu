package me.steinborn.lazydfu.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.mojang.datafixers.DataFixerBuilder;
import me.steinborn.lazydfu.mod.LazyDataFixerBuilder;
import net.minecraft.util.datafix.DataFixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DataFixes.class)
public class SchemasMixin {
    @WrapOperation(method = "buildDataFixer", at = @At(value = "NEW", target = "com/mojang/datafixers/DataFixerBuilder"))
    private static DataFixerBuilder create$replaceBuilder(int dataVersion, Operation<DataFixerBuilder> original) {
        return new LazyDataFixerBuilder(dataVersion);
    }
}
