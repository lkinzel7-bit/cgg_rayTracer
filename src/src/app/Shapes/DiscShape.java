package app.Shapes;

import static cgg_tools.Color.magenta;

import app.Hit;
import app.Material;
import app.Ray;
import cgg_tools.Color;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class DiscShape implements Shape {
    private final Vec3 center;
    private final double radius;
    private final Material material;
    private final Vec3 normal = new Vec3(0, 1, 0);
    private final double epsilon = 1e-9;

    public DiscShape(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public Hit intersect(Ray ray) {

        double denominator = Vec3.dot(ray.richtung, normal);

        if (Math.abs(denominator) < 1e-6) {
            return null;
        }

        double numerator = Vec3.dot(Vec3.subtract(center, ray.urspung), normal);
        double t = numerator / denominator;
        if (t < epsilon) {
            return null;
        }

        if (!ray.is_valid(t)) {
            return null;
        }

        Vec3 hitPoint = Vec3.add(ray.urspung, Vec3.multiply(t, ray.richtung));

        double distance = Vec3.length(Vec3.subtract(hitPoint, center));

        if (distance > radius) {
            return null;
        }

        double rx = hitPoint.x() - center.x();
        double rz = hitPoint.z() - center.z();
        Vec2 uv = new Vec2((rx + radius) / (2 * radius), 1 - (rz + radius) / (2 * radius));
        return new Hit(t, hitPoint, normal, material, uv);
    }
}