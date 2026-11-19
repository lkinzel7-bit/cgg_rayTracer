package app;

import app.Shapes.SphereShape;
import cgg_tools.Color;
import cgg_tools.Vec3;

public class IntersectTest {

    public static void startTest() {
        testSphereIntersect(new Vec3(0, 0, 0), new Vec3(0, 0, -1), 0, Double.POSITIVE_INFINITY,
                new Vec3(0, 0, -2), 1, 1, new Vec3(0, 0, -1), new Vec3(0, 0, 1));

        testSphereIntersect(new Vec3(0, 0, 0), new Vec3(0, 1, -1), 0, Double.POSITIVE_INFINITY,
                new Vec3(0, 0, -2), 1, -1, null, null);

        testSphereIntersect(new Vec3(0, 0, 0), new Vec3(0, 0, -1), 0, Double.POSITIVE_INFINITY,
                new Vec3(0, -1, -2), 1, 2, new Vec3(0, 0, -2), new Vec3(0, 1, 0));

        testSphereIntersect(new Vec3(0, 0, -4), new Vec3(0, 0, -1), 0, Double.POSITIVE_INFINITY,
                new Vec3(0, 0, -2), 1, -1, null, null);

        testSphereIntersect(new Vec3(0, 0, 0), new Vec3(0, 0, -1), 0, 2,
                new Vec3(0, 0, -4), 1, -1, null, null);
    }

    public static void testSphereIntersect(Vec3 rayOrigin, Vec3 rayDir,
            double tMin, double tMax,
            Vec3 center, double radius,
            double expectedT, Vec3 expectedPoint, Vec3 expectedNormal) {

        Ray ray = new Ray(rayOrigin, rayDir, tMin, tMax);
        SphereShape sphere = new SphereShape(center, radius, new PhongMaterial());

        Hit hit = sphere.intersect(ray);

        if (expectedT < 0) { // Kein Schnitt erwartet
            if (hit == null) {
                System.out.println("✅ Test bestanden: Kein Schnitt wie erwartet");
            } else {
                System.out.println("❌ Test fehlgeschlagen: Schnitt gefunden, t=" + hit.t());
            }
            return;
        }

        if (hit == null) {
            System.out.println("❌ Test fehlgeschlagen: Kein Schnitt, erwartet t=" + expectedT);
            return;
        }

        boolean tMatch = Math.abs(hit.t() - expectedT) < 1e-6;
        boolean pointMatch = hit.hitPoint().equals(expectedPoint);
        boolean normalMatch = hit.normale().equals(expectedNormal);

        if (tMatch && pointMatch && normalMatch) {
            System.out.println("✅ Test bestanden: t=" + hit.t());
        } else {
            System.out.println("❌ Test fehlgeschlagen:");
            System.out
                    .println("   Erwartet t=" + expectedT + ", Punkt=" + expectedPoint + ", Normale=" + expectedNormal);
            System.out.println("   Gefunden t=" + hit.t() + ", Punkt=" + hit.hitPoint() + ", Normale=" + hit.normale());
        }
    }

}
