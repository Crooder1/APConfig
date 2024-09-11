package com.test.testmod;

import github.crooder1.apconfig.APConfig;
import github.crooder1.apconfig.ConfigElement;
import github.crooder1.apconfig.ElementMethod;
import github.crooder1.apconfig.HashMapElementMethod;
import github.crooder1.apconfig.ListElementMethod;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.util.Color;

import java.util.HashMap;
import java.util.List;

@Mod(modid = TestMod.MODID, version = TestMod.VERSION)
public class TestMod {
    public static final String MODID = "TestMod";
    public static final String VERSION = "1.0";

    public static APConfig config = new APConfig("apconfig");



    public static ConfigElement<HashMap<String, Integer>> element = new ConfigElement<>("Testing", "t3", new HashMapElementMethod<>(ElementMethod.stringMethod, ElementMethod.integerMethod));
    public static ConfigElement<List<Double>> element2 = new ConfigElement<>("Testing","t2", new ListElementMethod<>(ElementMethod.doubleMethod));

    @EventHandler
    public void init(FMLInitializationEvent event) {

        ClientCommandHandler.instance.registerCommand(new TestCommand());

    }
}
