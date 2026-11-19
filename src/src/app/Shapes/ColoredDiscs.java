package app.Shapes;

import cgg_tools.Color;
import cgg_tools.Vec2;
import cgg_tools.Sampler;
import java.util.ArrayList;
import java.util.Random;

import app.DiscModel2D;

public record ColoredDiscs(Color color, ArrayList<DiscModel2D> coloredDiscsCollection) implements Sampler {

    static ArrayList<DiscModel2D> createColoredDiscsList(int discAmount, int radiusMin, int radiusMax) {
        ArrayList<DiscModel2D> coloredDiscsCollection = new ArrayList<DiscModel2D>();
        Random r = new Random();

        for (int i = 0; i < discAmount; i++) {
            Color c = Color.randomColor();
            double randomValueX = 0 + (1280 - 0) * r.nextDouble();
            double randomValueY = 0 + (720 - 0) * r.nextDouble();
            double radiusValue = radiusMin + (radiusMax - radiusMin) * r.nextDouble();
            Vec2 randomRadius = new Vec2(radiusValue, radiusValue);
            Vec2 randomMittelpunkt = new Vec2(randomValueX, randomValueY);

            DiscModel2D coloredDisc = new DiscModel2D(randomMittelpunkt, randomRadius, c);
            coloredDiscsCollection.add(coloredDisc);
        }
        return coloredDiscsCollection;

    }

    public static ColoredDiscs createColoredDiscs(int discAmount, int radiusMin, int radiusMax) {
        ArrayList<DiscModel2D> discs = createColoredDiscsList(discAmount, radiusMin, radiusMax);
        return new ColoredDiscs(null, discs);
    }

    @Override
    public Color getColor(Vec2 at) {
        for (int i = 0; i < coloredDiscsCollection.size(); i++) {
            if (coloredDiscsCollection.get(i).coversPoint(at)) {
                return coloredDiscsCollection.get(i).color();
            }
        }
        return Color.black;
    }
}
