package app.Shapes;

import app.Hit;
import app.Material;
import app.Ray;
import cgg_tools.*;

public class BackgroundShape implements Shape {
    private final Material material;

    public BackgroundShape(Material material) {
        this.material = material;
    }

    @Override
    public Hit intersect(Ray ray) {
        double t = Double.POSITIVE_INFINITY;
        Vec3 hitPoint = Vec3.add(ray.urspung, Vec3.multiply(t, ray.richtung));
        Vec3 normale = Vec3.normalize(Vec3.multiply(-1.0, ray.richtung));
        return new Hit(t, hitPoint, normale, material, null);

    }
}