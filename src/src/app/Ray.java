package app;

import cgg_tools.Vec3;

public class Ray {
    public Vec3 urspung;
    public Vec3 richtung;
    public double tMin;
    public double tMax;

    public Ray(Vec3 urspung, Vec3 richtung, double tMin, double tMax) {
        this.urspung = urspung;
        this.richtung = richtung;
        this.tMin = tMin;
        this.tMax = tMax;
    }

    public Ray(Vec3 ursprung, Vec3 richtung) {
        this.urspung = ursprung;
        this.richtung = richtung;
        this.tMin = 0;
        this.tMax = Double.MAX_VALUE;
    }

    Vec3 point_at(double t) {
        Vec3 skalierterVector = new Vec3(richtung.x() * t, richtung.y() * t, richtung.z() * t);
        return Vec3.add(urspung, skalierterVector);

    }

    public boolean is_valid(double t) {
        return t >= tMin && t <= tMax;

    }

}
