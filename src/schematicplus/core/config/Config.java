package schematicplus.core.config;

import java.io.File;
import java.io.IOException;

public abstract class Config implements IConfig {
    private String configname;
    public Config (String name) {
        this.configname = name;
    }

    public String getConfig() {
        return this.configname;
    }
    public File getFile() {
        return new File("plugins//SchematicsPlus//"+configname+".yml");
    }
    public boolean setup() {
    if (!getFile().exists()) {
        try {
            getFile().createNewFile();
            setupKeys();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    return true;
}
public void reload() {
    getFile().delete();
    setup();
    setupKeys();
}
}