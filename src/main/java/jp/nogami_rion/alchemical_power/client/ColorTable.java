package jp.nogami_rion.alchemical_power.client;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ColorTable {
    private ColorTable(){}

    public static final Map<ResourceLocation,Integer> COLORS = Map.ofEntries(
            entry("molten_t1_panakeia_ingot", 0xFFCCFFFF),
            entry("molten_t2_panakeia_ingot", 0xFFFFFF66),
            entry("molten_t3_panakeia_ingot", 0xFFFF6666),
            entry("molten_t4_panakeia_gem", 0xFFFF33FF),
            entry("molten_t5_panakeia_gem", 0xFF9999FF),
            entry("molten_t6_panakeia_ingot", 0xFFFFFFFF),
            entry("molten_unite_alloy",       0xFF000000),
            entry("molten_singularity", 0xFFFFF9DC)
    );

    private static Map.Entry<ResourceLocation,Integer> entry(String path, int argb) {
        return Map.entry(new ResourceLocation("alchemical_power", path), argb);
    }

    public static int colorOf(ResourceLocation id,int fallback){
        return COLORS.getOrDefault(id,fallback);
    }

}
