// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package cgg_tools;

import static cgg_tools.Color.*;

public interface Sampler {
    public Color getColor(Vec2 p);

    public static Sampler CC(Color c) {
        return new ConstantColorSampler(c);
    }

    public static Sampler CC(Color c, double w) {
        return new ConstantColorSampler(multiply(c, w));
    }
}
