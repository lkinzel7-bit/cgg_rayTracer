package app;

import cgg_tools.*;

public record DirectionalLight(Color color, Vec3 lichtRichtung) implements Light {

    // Richtungsverktor von Oberfläche bis Lichtquelle
    public Vec3 toLight(Vec3 receiver_position) {
        return Vec3.multiply(-1, Vec3.normalize(this.lichtRichtung));
    }

    // Farbe der Lichtqulle
    public Color color_At(Vec3 receiver_position) {
        return this.color;
    }

    // Entfernung oberfläche bis Lichtquelle(Für schattenberechnug)
    public double distance(Vec3 receiver_position) {
        return Double.MAX_VALUE;
    }
}
