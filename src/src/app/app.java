// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package app;

import java.util.ArrayList;

import app.Samplers.CheckerboardSampler;
import app.Samplers.ClampSampler;
import app.Samplers.RegularGridSampler;
import app.Samplers.StratifiedGridSampler;
import app.Samplers.TransformationSampler;
import app.Samplers.uvDebugSampler;
import app.Shapes.BackgroundShape;
import app.Shapes.DiscShape;
import app.Shapes.GroupShape;
import app.Shapes.RectShape;
import app.Shapes.Shape;
import app.Shapes.SphereShape;
import cgg_tools.*;

import static cgg_tools.Color.*;
import static cgg_tools.Mat4x4.*;
import static cgg_tools.Mat4x4.multiply;
import static cgg_tools.Sampler.*;
import static cgg_tools.Vec2.nyAxis;
import static cgg_tools.Vec2.yAxis;

public class app {

        public static void main(String[] args) {
                int width = 640;
                int height = 360;
                ArrayList<Shape> groupShape = new ArrayList<>();
                Image image = new Image(width, height);

                // Kamera
                Mat4x4 kameraUrsprungsTransormation = move(new Vec3(0, 5, 15));
                Mat4x4 kameraRichtungsTransormation = rotate(1, 0, 0, -20);
                Camera kamera = new Camera(width, height, 70, new Vec3(0, 0, 0), new Vec3(0, 0, -1),
                                kameraUrsprungsTransormation,
                                kameraRichtungsTransormation);

                // Material
                ImageTexture lvSphere = new ImageTexture("src\\src\\BilderImports\\bauwesen_1.jpg");
                ImageTexture goldMaterial = new ImageTexture(
                                "src\\src\\BilderImports\\golden_texture_wave_hd_abstract.jpg");
                ImageTexture lvSphereTexture = new ImageTexture(
                                "src\\src\\BilderImports\\moon_texture.5400x2700.jpg");
                CheckerboardSampler cBS = new CheckerboardSampler(white, black, 75);

                PhongMaterial materialWhite = new PhongMaterial(CC(white), CC(white), CC(white), 50);
                PhongMaterial materialSphere = new PhongMaterial(lvSphereTexture, lvSphereTexture, lvSphereTexture, 50);
                PhongMaterial materialBlack = new PhongMaterial(CC(black), CC(black), CC(black), 50);
                PhongMaterial materialUVDebug = new PhongMaterial(new uvDebugSampler(), new uvDebugSampler(),
                                new uvDebugSampler(), 50);
                PhongMaterial materialLvSphere = new PhongMaterial(lvSphere, lvSphere,
                                lvSphere, 50);
                PhongMaterial materialGold = new PhongMaterial(goldMaterial, goldMaterial,
                                goldMaterial, 50);
                PhongMaterial checkerBoardMaterial = new PhongMaterial(cBS, cBS,
                                cBS, 50);

                // Licht
                DirectionalLight lightSource1 = new DirectionalLight(Color.white, new Vec3(0, -40, 0));
                DirectionalLight lightSource2 = new DirectionalLight(Color.white, new Vec3(0, -40, 0));
                ArrayList<Light> lightsourcesList = new ArrayList<Light>();
                // lightsourcesList.add(lightSource2);
                lightsourcesList.add(lightSource1);

                // Objekte
                RectShape rechteck = new RectShape(new Vec3(0, -2, 0), 200, 200, materialGold);
                GroupShape rechteckRotation = new GroupShape(rechteck, rotate(new Vec3(1, 0, 0), 0));
                groupShape.add(rechteckRotation);

                SphereShape kugelUVDebug = new SphereShape(new Vec3(0, 0, 0), 3,
                                materialGold);
                groupShape.add(kugelUVDebug);

                // Szene
                GroupShape szene = new GroupShape(new BackgroundShape(materialWhite),
                                groupShape,
                                new Color(0.1, 0.1, 0.1),
                                identity());

                Raytracer rt = new Raytracer(szene, kamera, Color.gray, new Color(0.1, 0.1, 0.1), lightsourcesList);
                int n = 1;
                int m = 1;
                StratifiedGridSampler sampler = new StratifiedGridSampler(rt, n, m);
                image.sample(sampler);
                // image.sample(rt);

                // Bild erzeugen
                DirectionalLightTest.callLightTest();
                image.writePNG("a08-gamma");

                // startTest();

        }
}