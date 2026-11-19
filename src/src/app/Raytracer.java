package app;

import java.util.ArrayList;

import app.Shapes.GroupShape;
import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class Raytracer implements Sampler {
    private GroupShape szene;
    private Camera kamera;
    private Color bg_color;
    private Color ambientColor;
    private ArrayList<Light> lichtquellen;

    public Raytracer(GroupShape szene, Camera kamera, Color bg_color, Color ambientColor,
            ArrayList<Light> lichtquellen) {
        this.szene = szene;
        this.kamera = kamera;
        this.bg_color = bg_color;
        this.ambientColor = ambientColor;
        this.lichtquellen = lichtquellen;
    }

    @Override
    public Color getColor(Vec2 p) {
        final double shadowEpsilon = 1e-6;

        Ray ray = kamera.generate_ray(p);
        Hit hit = szene.intersect(ray);

        if (hit == null) {
            return bg_color;
        } else {
            Vec3 to_viewer = Vec3.multiply(-1, ray.richtung);
            if (Vec3.dot(hit.normale(), to_viewer) < 0) {
                return Color.magenta;
            }

            Material m = hit.material();

            Color result = m.ambient(hit, this.ambientColor);

            for (Light light : lichtquellen) {

                Vec3 hit_position = ray.point_at(hit.t());
                Vec3 to_light = light.toLight(hit_position);
                Color incoming_light = light.color_At(hit_position);

                // Gemini hilfe
                Vec3 shadowRayUrsprung = Vec3.add(hit_position, Vec3.multiply(shadowEpsilon, hit.normale()));
                Ray shadowRay = new Ray(shadowRayUrsprung, to_light);
                Hit shadowHit = szene.intersect(shadowRay);
                double lightDistance = light.distance(hit_position);
                boolean imSchatten = false;

                if (shadowHit != null &&
                        shadowHit.t() < lightDistance) {

                    imSchatten = true;
                }

                if (!imSchatten) {
                    Color lightContribution = m.shade(hit, to_viewer, to_light, incoming_light);
                    result = Color.add(result, lightContribution);
                }

            }

            return result;
        }
    }
}