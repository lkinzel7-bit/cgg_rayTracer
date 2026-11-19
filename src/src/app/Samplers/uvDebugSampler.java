package app.Samplers;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record uvDebugSampler() implements Sampler {

    @Override
    public cgg_tools.Color getColor(Vec2 at) {
        if (at == null) {
            return Color.black;
        }
        return new cgg_tools.Color(at.u(), at.v(), 0);
    }

}
