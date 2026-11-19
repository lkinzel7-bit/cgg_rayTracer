package app;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public class ConstantColor implements Sampler {

    Color color;

    public ConstantColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(Vec2 p) {
        return color;
    }

}
