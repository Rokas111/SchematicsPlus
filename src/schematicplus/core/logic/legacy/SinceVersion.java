package schematicplus.core.logic.legacy;

public class SinceVersion {
    private Version version;
    private String output;
    public SinceVersion(Version v, String Output) {
        this.version = v;
        this.output = Output;
    }
    public Version getVersion() {
        return this.version;
    }
    public String getOutput() {
        return this.output;
    }
}
