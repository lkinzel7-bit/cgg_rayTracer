package app;

import cgg_tools.*;

public interface Light {
    public Vec3 toLight(Vec3 receiver_position);

    public double distance(Vec3 receiver_position);

    public Color color_At(Vec3 receiver_position);
}
