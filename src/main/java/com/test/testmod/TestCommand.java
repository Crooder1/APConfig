package com.test.testmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.HashMap;
import java.util.List;

public class TestCommand extends CommandBase {

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "testcmd";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/testcmd";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (args[0].equalsIgnoreCase("put")) {

            if (args[1].equalsIgnoreCase("map")) {

                HashMap<String, Integer> map = TestMod.element.getValue();

                map.put(args[2], Integer.parseInt(args[3]));
                TestMod.element.setValue(map);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(TestMod.element.getAsString()));

            } else if (args[1].equalsIgnoreCase("list")) {

                List<Double> list = TestMod.element2.getValue();
                list.add(Double.parseDouble(args[2]));
                TestMod.element2.setValue(list);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(TestMod.element2.getAsString()));

            }

        } else if (args[0].equalsIgnoreCase("get")) {

            if (args[1].equalsIgnoreCase("map")) {

                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(TestMod.element.getAsString()));

            } else if (args[1].equalsIgnoreCase("list")) {

                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(TestMod.element2.getAsString()));

            }

        }  else if (args[0].equalsIgnoreCase("load")) {

            TestMod.config.loadConfig();

        } else if (args[0].equalsIgnoreCase("save")) {

            TestMod.config.saveConfig();

        } else if (args[0].equalsIgnoreCase("autosave")) {

            TestMod.config.autosave.setValue(!TestMod.config.autosave.getValue());
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(TestMod.config.autosave.getAsString()));

        }

    }
}
