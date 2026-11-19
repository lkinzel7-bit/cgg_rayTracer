package app.Samplers;

import cgg_tools.Color;
import cgg_tools.Mat4x4;

import cgg_tools.Sampler;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public record TransformationSampler(Sampler sampler, Mat4x4 transformation) implements Sampler {

    @Override
    public Color getColor(Vec2 p) {
        if (p == null) {
            return Color.black;
        }
        Vec3 transformedPoint = Mat4x4.multiplyPoint(transformation, new Vec3(p.u(), p.v(), 0));
        return sampler.getColor(new Vec2(transformedPoint.x(), transformedPoint.y()));
    }

}
