package iguanaman.iguanatweakstconstruct.claybuckets;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import iguanaman.iguanatweakstconstruct.claybuckets.items.ClayBucket;
import iguanaman.iguanatweakstconstruct.claybuckets.items.ClayBucketMilk;
import iguanaman.iguanatweakstconstruct.claybuckets.items.ClayBucketTinkerLiquids;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import tconstruct.blocks.slime.SlimeFluid;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.world.TinkerWorld;

import static tconstruct.smeltery.TinkerSmeltery.*;

@Pulse(id = Reference.PULSE_ITEMS, description = "All the Items Iguana Tweaks for TConstruct adds (Clay Buckets,...)")
public class IguanaItems {
    public static Item clayBucketUnfired;
    public static Item clayBucketFired;
    public static Item clayBucketWater;
    public static Item clayBucketLava;
    public static Item clayBucketMilk;
    public static Item clayBucketsTinkers;

    @Handler
    public void preInit(FMLPreInitializationEvent event)
    {
        Log.info("Adding Items");
        // unfired clay bucket is a regular item
        clayBucketUnfired = new Item().setUnlocalizedName(Reference.MOD_ID + ".clayBucketUnfired").setTextureName(Reference.resource("clayBucketUnfired")).setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMisc);
        GameRegistry.registerItem(clayBucketUnfired, "clayBucketUnfired");

        clayBucketFired = new ClayBucket(Blocks.air, "clayBucketFired", "clayBucketFired").setMaxStackSize(16);
        clayBucketWater = new ClayBucket(Blocks.flowing_water, "clayBucketWater", "clayBucketWater");
        clayBucketLava = new ClayBucket(Blocks.flowing_lava, "clayBucketLava", "clayBucketLava");
        clayBucketMilk = new ClayBucketMilk();

        clayBucketsTinkers = new ClayBucketTinkerLiquids(null);

        GameRegistry.registerItem(clayBucketFired, "clayBucketFired");
        GameRegistry.registerItem(clayBucketWater, "clayBucketWater");
        GameRegistry.registerItem(clayBucketLava,  "clayBucketLava");
        GameRegistry.registerItem(clayBucketMilk,  "clayBucketMilk");
        GameRegistry.registerItem(clayBucketsTinkers, "clayBucketsTinkers");

        // register the buckets with the fluid container registry
        ItemStack emptyClayBucket = new ItemStack(clayBucketFired);
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(clayBucketWater), emptyClayBucket);
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, new ItemStack(clayBucketLava), emptyClayBucket);

        // tinker metals
        Fluid[] tinkerFluids = new Fluid[] {moltenIronFluid, moltenGoldFluid, moltenCopperFluid, moltenTinFluid, moltenAluminumFluid,
                                            moltenCobaltFluid, moltenArditeFluid, moltenBronzeFluid, moltenAlubrassFluid, moltenManyullynFluid,
                                            moltenAlumiteFluid, moltenObsidianFluid, moltenSteelFluid, moltenGlassFluid, moltenStoneFluid, moltenEmeraldFluid,
                                            bloodFluid, moltenNickelFluid, moltenLeadFluid, moltenSilverFluid, moltenShinyFluid, moltenInvarFluid,
                                            moltenElectrumFluid, moltenEnderFluid, TinkerWorld.blueSlimeFluid, glueFluid, pigIronFluid};

        for(int i = 0; i < tinkerFluids.length; i++)
            FluidContainerRegistry.registerFluidContainer(tinkerFluids[i], new ItemStack(clayBucketsTinkers, 1, i), emptyClayBucket);

        Log.info("Added Items");
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ClayBucketHandler());
    }
}
