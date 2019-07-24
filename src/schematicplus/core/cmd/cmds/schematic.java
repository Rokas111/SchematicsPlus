package schematicplus.core.cmd.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import schematicplus.core.cmd.Command;
import schematicplus.core.logic.schematics.Schematic;
import schematicplus.core.main;

import java.io.File;

public class schematic extends Command {
    public schematic() {
        super("schematic", "schematic");

    }

    public void run(Player p, String[] args) {
        if (args == null || args.length == 0) {
            if (main.cm.getSchematicDirectory().listFiles().length == 0 || main.sm.getSchematics().isEmpty()) {
                p.sendMessage(main.mm.getSchematicCmdKey("schematic","noschematics"));
                return;
            }
            main.gm.addSchematicGUI(p, main.sm.getSchematics());
            return;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 1) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","give","wrongformat"));
                return;
            }
            Player target = null;
            if (args.length >= 3) {
                target = Bukkit.getPlayer(args[1]);
            } else {
                target = p;
            }
            if (target == null) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","give","playernotonline"));
                return;
            }
            String sc = args.length == 2 ? args[1] : args[2];
            if (!main.sm.isRegistered(sc)) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","give","nosuchschematic"));
                return;
            }
            p.getInventory().addItem(main.cm.getSchematicYaml().getSchematic(sc).build());
            p.updateInventory();
            return;
        }
        if (args[0].equalsIgnoreCase("load")) {
            if (args.length == 1) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","load","wrongformat"));
                return;
            }
            String sc = args[1];
            if (!main.sm.isRegistered(sc)) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","load","nosuchschematic"));
                return;
            }
            File file = new File("plugins//SchematicsPlus//Schematics//" + main.cm.getSchematicYaml().getYaml().getString(sc + ".schematic") + ".schematic");
            new Schematic(file).load(p.getLocation());
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (args.length == 1) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","reload","wrongformat"));
                return;
            }
            if (main.cm.getYaml(args[1]) == null) {
                p.sendMessage(main.mm.getSchematicCmdArgKey("schematic","reload","nosuchconfig"));
                return;
            }
            main.cm.getYaml(args[1]).reload();
        }
    }
}
