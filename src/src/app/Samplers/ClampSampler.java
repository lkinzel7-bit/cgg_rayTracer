package app.Samplers;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record ClampSampler(Sampler sampler, Color color) implements Sampler {

    @Override
    public cgg_tools.Color getColor(Vec2 at) {
        if (at.u() < 0 || at.u() > 1 || at.v() < 0 || at.v() > 1) {

            return color;
        }
        return sampler.getColor(at);
    }

}
