package app.Samplers;

import cgg_tools.Color;
import cgg_tools.RandomUtil;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record StratifiedGridSampler(Sampler sampler, int n, int m) implements Sampler {

    @Override
    public Color getColor(Vec2 p) {
        Color sum = Color.black;
        for (int jj = 0; jj < m; jj++) {
            double randomValue = RandomUtil.random();
            for (int ij = 0; ij < n; ij++) {
                Vec2 xy = new Vec2(
                        p.x() + (ij + randomValue) / n,
                        p.y() + (jj + randomValue) / m);
                sum = Color.add(sum, sampler.getColor(xy));
            }
        }
        return Color.divide(sum, n * m);
    }
}
