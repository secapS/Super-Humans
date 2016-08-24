package xyz.whynospaces.superhumans.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.whynospaces.superhumans.SuperHumans;
import xyz.whynospaces.superhumans.users.User;

public class SetHeroCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("hero")) {

            if(args.length == 2) {

                if(Bukkit.getPlayer(args[0]) != null) {
                    SuperHumans.instance.userManager.setUserHero(new User(Bukkit.getPlayer(args[0])), SuperHumans.instance.superHumanFactory.getSuperHumanByName(args[1]));
                }
            }
            else if(args.length == 1) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    SuperHumans.instance.userManager.setUserHero(new User(Bukkit.getPlayer(args[0])), null);
                }
            }
            return true;
        }

        return false;
    }
}
