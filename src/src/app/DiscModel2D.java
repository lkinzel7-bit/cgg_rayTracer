package app;

import cgg_tools.Color;
import cgg_tools.Vec2;

public record DiscModel2D(Vec2 mittelpunkt, Vec2 radius, Color color) {

    public boolean coversPoint(Vec2 pos) {
        Vec2 dist = Vec2.subtract(pos, mittelpunkt);
        double length = Vec2.length(dist);
        double r = Vec2.length(radius); // falls radius als Vec2 gespeichert ist
        return length <= r;

    }

}
