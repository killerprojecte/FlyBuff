package flyproject.flybuff.utils;

public class BuffParticle {
    private String particle;
    private String x;
    private String y;
    private String z;
    private int count;
    private String world;

    public BuffParticle(String particle, String x, String y, String z, String world, int count) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
        this.world = world;
    }

    public String getParticle() {
        return particle;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }

    public int getCount() {
        return count;
    }
}
