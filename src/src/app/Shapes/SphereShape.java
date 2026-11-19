package app.Shapes;

import app.Hit;
import app.Material;
import app.Ray;
import cgg_tools.*;

public record SphereShape(Vec3 center, double radius, Material material) implements Shape {

    public Hit intersect(Ray ray) {

        Vec3 oc = Vec3.subtract(ray.urspung, center);

        double a = Vec3.dot(ray.richtung, ray.richtung);
        double b = 2.0 * Vec3.dot(ray.richtung, oc);
        double c = Vec3.dot(oc, oc) - radius * radius;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null;
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = (-b - sqrtDiscriminant) / (2 * a);
        double t2 = (-b + sqrtDiscriminant) / (2 * a);

        double t = t1;
        if (!ray.is_valid(t)) {
            t = t2;
        }
        if (!ray.is_valid(t)) {
            return null;
        }
        if (t < 0)
            return null;

        Vec3 hitPoint = Vec3.add(ray.urspung, Vec3.multiply(t, ray.richtung));
        Vec3 normal = Vec3.normalize(Vec3.subtract(hitPoint, center));

        // uv
        double u = 0.5 + (Math.atan2(normal.z(), normal.x()) / (2 * Math.PI));
        double v = 1.0 - (Math.acos(normal.y()) / Math.PI);

        Vec2 uv = new Vec2(u, v);

        return new Hit(t, hitPoint, normal, material, uv);
    }
}
