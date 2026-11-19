package app.Shapes;

import java.util.ArrayList;

import org.w3c.dom.css.Rect;

import app.Hit;
import app.OpenCylinderShape;
import app.Ray;
import cgg_tools.Color;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;

public class GroupShape
        implements Shape {
    BackgroundShape backgroundShape;
    DiscShape discShape;
    ArrayList<Shape> szene;
    Color ambientLight;
    Mat4x4 transformation;
    Mat4x4 inverseTransformation;
    Mat4x4 inverseTranspose;
    RectShape rectShape;
    Shape shape;
    GroupShape groupShape;

    public GroupShape(BackgroundShape backgroundShape, DiscShape discShape, ArrayList<Shape> szene, Color ambientLight,
            Mat4x4 transformation) {
        if (transformation == null)
            transformation = Mat4x4.identity();
        this.backgroundShape = backgroundShape;
        this.discShape = discShape;
        this.szene = szene;
        this.ambientLight = ambientLight;
        this.transformation = transformation;
        this.inverseTransformation = Mat4x4.invert(transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
    }

    public GroupShape(BackgroundShape backgroundShape, RectShape rectShape, ArrayList<Shape> szene, Color ambientLight,
            Mat4x4 transformation) {
        if (transformation == null)
            transformation = Mat4x4.identity();
        this.backgroundShape = backgroundShape;
        this.rectShape = rectShape;
        this.szene = szene;
        this.ambientLight = ambientLight;
        this.transformation = transformation;
        this.inverseTransformation = Mat4x4.invert(transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
    }

    public GroupShape(ArrayList<Shape> szene, Color ambientLight, Mat4x4 transformation) {
        if (transformation == null)
            transformation = Mat4x4.identity();
        this.szene = szene;
        this.ambientLight = ambientLight;
        this.transformation = transformation;
        this.inverseTransformation = Mat4x4.invert(transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
    }

    public GroupShape(Shape shape, Mat4x4 transformation, Color ambientLight) {
        if (transformation == null)
            transformation = Mat4x4.identity();
        this(shape, ambientLight, transformation);
    }

    public GroupShape(Shape shape, Color ambientLight, Mat4x4 transformation) {
        if (transformation == null)
            transformation = Mat4x4.identity();
        this.shape = shape;
        this.ambientLight = ambientLight;
        this.transformation = transformation;
        this.inverseTransformation = Mat4x4.invert(transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
        this.szene = new ArrayList<>();
    }

    public GroupShape(Mat4x4 rotate, DiscShape bottom_disc) {
        if (rotate == null) {
            rotate = Mat4x4.identity();

        }
        this.transformation = rotate;
        this.inverseTransformation = Mat4x4.invert(rotate);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
        this.discShape = bottom_disc;
    }

    public GroupShape(DiscShape top_disc, OpenCylinderShape cylinder, GroupShape bottom_group) {
        this.discShape = top_disc;
        this.shape = cylinder;
        this.transformation = Mat4x4.identity();
        this.inverseTransformation = Mat4x4.invert(this.transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
        this.groupShape = bottom_group;
    }

    public GroupShape(Shape openCylinder, Mat4x4 transformation) {
        this.shape = openCylinder;
        this.transformation = transformation;
        this.inverseTransformation = Mat4x4.invert(this.transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
    }

    public GroupShape(Shape... shapes) {
        this.szene = new ArrayList<>();
        for (Shape shape : shapes) {
            this.szene.add(shape);
        }
        this.transformation = Mat4x4.identity();
        this.inverseTransformation = Mat4x4.invert(this.transformation);
    }

    public GroupShape(BackgroundShape backgroundShape2, ArrayList<Shape> groupShape2, Color color,
            Mat4x4 transformation) {
        if (transformation == null)
            transformation = Mat4x4.identity();
        this.backgroundShape = backgroundShape2;
        this.szene = groupShape2;
        this.ambientLight = color;
        this.transformation = transformation;
        this.inverseTransformation = Mat4x4.invert(transformation);
        this.inverseTranspose = Mat4x4.transpose(inverseTransformation);
    }

    // Gemini
    public Color ambient_light() {
        return this.ambientLight;
    }

    @Override
    public Hit intersect(Ray ray) {

        Vec3 transformedOrigin = Mat4x4.multiplyPoint(inverseTransformation, ray.urspung);
        Vec3 transformedDirection = Mat4x4.multiplyDirection(inverseTransformation, ray.richtung);
        Ray transformedRay = new Ray(transformedOrigin, transformedDirection, ray.tMin, ray.tMax);

        // gemini
        final double hitEpsilon = 1e-9;

        Hit closestHit = null;

        if (backgroundShape != null) {
            closestHit = backgroundShape.intersect(transformedRay);
        }

        if (discShape != null) {
            Hit hit = discShape.intersect(transformedRay);

            if (hit != null && hit.t() > hitEpsilon) {
                if (closestHit == null || hit.t() < closestHit.t()) {
                    closestHit = hit;
                }
            }
        }

        if (rectShape != null) {
            Hit hit = rectShape.intersect(transformedRay);

            if (hit != null && hit.t() > hitEpsilon) {
                if (closestHit == null || hit.t() < closestHit.t()) {
                    closestHit = hit;
                }
            }
        }

        if (szene != null) {
            for (Shape shape : szene) {
                Hit hit = shape.intersect(transformedRay);

                if (hit != null && hit.t() > hitEpsilon) {
                    if (closestHit == null || hit.t() < closestHit.t()) {
                        closestHit = hit;
                    }
                }
            }
        }

        if (shape != null) {
            Hit hit = shape.intersect(transformedRay);

            if (hit != null && hit.t() > hitEpsilon) {
                if (closestHit == null || hit.t() < closestHit.t()) {
                    closestHit = hit;
                }
            }
        }

        if (closestHit != null) {
            // Normale transformieren mit der Transpose der inversen Matrix
            Vec3 transformedNormal = Mat4x4.multiplyDirection(inverseTranspose, closestHit.normale());
            transformedNormal = Vec3.normalize(transformedNormal);

            // Erstelle einen neuen Hit mit transformierter Normaler
            closestHit = new Hit(closestHit.t(), closestHit.hitPoint(), transformedNormal, closestHit.material(),
                    closestHit.uv());
        }

        return closestHit;
    }

    public ArrayList<Shape> getSzene() {
        return this.szene;
    }

    public void add(Shape shape) {
        szene.add(shape);
    }

}