package app.Shapes;

import app.Hit;
import app.Material;
import app.Ray;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class RectShape implements Shape {
    private final Vec3 center;
    private final double höhe;
    private final double breite;
    private final Material material;
    private final Vec3 normal = new Vec3(0, 1, 0);
    private final double epsilon = 1e-9;

    public RectShape(Vec3 center, double höhe, double breite, Material material) {
        this.center = center;
        this.höhe = höhe;
        this.breite = breite;
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

        double halfWidth = breite / 2.0;
        double halfHeight = höhe / 2.0;
        if (hitPoint.x() < center.x() - halfWidth || hitPoint.x() > center.x() + halfWidth ||
                hitPoint.z() < center.z() - halfHeight || hitPoint.z() > center.z() + halfHeight) {
            return null;
        }
        double rx = hitPoint.x() - center.x();
        double rz = hitPoint.z() - center.z();
        Vec2 uv = new Vec2((rx + halfWidth) / breite, 1 - (rz + halfHeight) / höhe);
        return new Hit(t, hitPoint, normal, material, uv);
    }
}