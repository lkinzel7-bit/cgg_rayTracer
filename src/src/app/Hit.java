package app;

import cgg_tools.*;

public record Hit(double t, Vec3 hitPoint, Vec3 normale, Material material, Vec2 uv) {

    public String toString() {
        return "Hit t?" + t + " at " + hitPoint + " n=" + normale + " color=" + material;
    }

}
