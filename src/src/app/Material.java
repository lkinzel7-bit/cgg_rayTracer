package app;

import cgg_tools.*;

public interface Material {
    public Color ambient(Hit hit, Color ambient_light);

    public Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light);
}
