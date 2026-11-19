package app.Samplers;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record CheckerboardSampler(Color color1, Color color2, double squareSize) implements Sampler {

    @Override
    public Color getColor(Vec2 at) {
        if (at == null) {
            return color1;
        }

        // Berechne die Feldposition basierend auf UV-Koordinaten (0-1)
        // Skaliere mit squareSize um Feldgröße zu kontrollieren
        int xField = (int) Math.floor(at.x() * squareSize);
        int yField = (int) Math.floor(at.y() * squareSize);

        // Schachbrett-Muster: Farbe wechselt wenn (xField + yField) ungerade ist
        if ((xField + yField) % 2 == 0) {
            return color1;
        } else {
            return color2;
        }
    }
}
