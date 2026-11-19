package app;

import cgg_tools.*;

public record PhongMaterial(Sampler k_ambient, Sampler k_diffuse, Sampler k_specular, double k_shininess)
        implements Material {
    public Color ambient(Hit hit, Color ambient_light) {
        return Color.multiply(k_ambient.getColor(null), ambient_light);

    }

    public Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light) {
        Vec2 uv = hit.uv();

        double cos_angle_light = Math.max(0, Vec3.dot(Vec3.normalize(to_light), hit.normale()));
        // Gemini
        double cos_angle_view = Math.max(0,
                Vec3.dot(
                        Vec3.normalize(Vec3.reflect(
                                Vec3.normalize(to_light),
                                hit.normale())),
                        Vec3.normalize(to_viewer)));
        Color diffuse = Color.multiply(this.k_diffuse.getColor(uv), incoming_light);
        diffuse = Color.multiply(cos_angle_light, diffuse);

        if (cos_angle_light <= 0) {
            cos_angle_view = 0;
        }
        // Gemini Ende

        double cos_angle_expo = Math.pow(cos_angle_view, k_shininess);
        Color spekular = Color.multiply(k_specular.getColor(uv), incoming_light);
        spekular = Color.multiply(cos_angle_expo, spekular);

        return Color.add(diffuse, spekular);

    }
}
