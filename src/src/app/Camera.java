package app;

import cgg_tools.*;
import static cgg_tools.Mat4x4.*;

public class Camera {
    double breite;
    double höhe;
    double winkel;
    Vec3 ursprung;
    Mat4x4 transformationUrsprung;
    Mat4x4 transformationRichtung;
    Vec3 richtung;

    public Camera(double breite, double höhe, double winkel, Vec3 ursprung, Vec3 richtung,
            Mat4x4 transformationUrsprung, Mat4x4 transformationRichtung) {
        this.breite = breite;
        this.höhe = höhe;
        this.winkel = Math.toRadians(winkel);
        this.ursprung = ursprung;
        this.richtung = richtung;
        this.transformationUrsprung = transformationUrsprung;
        this.transformationRichtung = transformationRichtung;

    }

    Ray generate_ray(Vec2 pos) {
        double x = -(breite / 2.0) + pos.x();
        double y = (höhe / 2.0) - pos.y();
        double z = -(breite / 2.0) / (Math.tan(winkel / 2.0));

        Vec3 transformedUrsprung = multiplyPoint(transformationUrsprung, ursprung);
        Vec3 transformedRichtung = multiplyDirection(transformationRichtung, new Vec3(x, y, z));
        Vec3 richtungUnnormalisiert = Vec3.normalize(transformedRichtung);
        return new Ray(transformedUrsprung, richtungUnnormalisiert, 0, Double.POSITIVE_INFINITY);
    }
}
