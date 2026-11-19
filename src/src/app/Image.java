
package app;

import cgg_tools.Color;
import cgg_tools.ImageWriter;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public class Image implements cgg_tools.Image {
    int width;
    int height;
    double[] doubleArray;

    // TODO create a double array for storage of the RGB Pixels
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        doubleArray = new double[width * height * 3];

    }

    // TODO store the RGB values of the pixel (i,j) in the image
    @Override
    public void setPixel(int i, int j, Color color) {
        doubleArray[(j * width + i) * 3 + 0] = color.r();
        doubleArray[(j * width + i) * 3 + 1] = color.g();
        doubleArray[(j * width + i) * 3 + 2] = color.b();

    }

    // TODO use ImageWriter.writePNG to implement this
    public void writePNG(String name) {
        ImageWriter.writePNG(name, doubleArray, width, height);
    }

    // TODO return the RGB color of a given pixel (i,j)
    @Override
    public Color getPixel(int i, int j) {
        return Color.black;
    }

    // TODO return the image width
    @Override
    public int width() {
        return -1;
    }

    // TODO return the image height
    @Override
    public int height() {
        return -1;
    }

    public void sample(Sampler rt) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vec2 sample = new Vec2(i + 0.5, j + 0.5);
                Color color = rt.getColor(sample);
                setPixel(i, j, color);
            }
        }
    }

}
