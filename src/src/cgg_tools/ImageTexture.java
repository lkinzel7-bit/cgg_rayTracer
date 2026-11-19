// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package cgg_tools;

import static cgg_tools.Color.black;
import static cgg_tools.Sampler.CC;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static cgg_tools.Sampler.*;

public class ImageTexture implements Sampler {

    private BufferedImage image;
    public final int width;
    public final int height;
    private final double componentScale;
    private final int components;

    /**
     * Constructs an ImageTexture from an image file.
     *
     * @param filename The path to the image file to load
     * @throws RuntimeException if the image cannot be read or is invalid
     */
    public ImageTexture(String filename) {
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("Cannot read image from: " + filename);
            System.exit(1);
        }

        if (image == null) {
            System.err.println("Error reading image from: " + filename);
            System.exit(1);
        }

        width = image.getWidth();
        height = image.getHeight();
        components = image.getRaster().getNumBands();

        System.out.format(
                "texture: %s: %dx%d, components: %d\n",
                filename,
                width,
                height,
                components);

        switch (image.getSampleModel().getDataType()) {
            case DataBuffer.TYPE_BYTE:
                componentScale = 255;
                break;
            case DataBuffer.TYPE_USHORT:
                componentScale = 65535;
                break;
            default:
                componentScale = 1;
                break;
        }
    }

    public Color getColor(Vec2 at) {
        // if (at.u() < 0 || at.u() > 1 || at.v() < 0 || at.v() > 1) {

        // return Color.black;
        // }
        if (at == null) {
            return new Color(0, 0, 0);
        }

        double u = at.u();
        double v = 1.0 - at.v();
        int x = (int) ((u - Math.floor(u)) * width);
        int y = (int) ((v - Math.floor(v)) * height);

        // Gemini
        double[] pixelBuffer = new double[4];
        image.getRaster().getPixel(x, y, pixelBuffer);
        double r = gamma(pixelBuffer[0] / componentScale);
        double g = gamma(pixelBuffer[1] / componentScale);
        double b = gamma(pixelBuffer[2] / componentScale);
        return new Color(r, g, b);
    }

    private static double gamma(double v) {
        return (double) Math.pow(v, 1.0 / 2.2);
    }
}
