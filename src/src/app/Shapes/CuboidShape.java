package app.Shapes;

import cgg_tools.Color;
import static cgg_tools.Mat4x4.*;

import java.util.ArrayList;
import java.util.List;

import app.Material;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;

public class CuboidShape {
        GroupShape gruppenKnoten;
        Vec3 nxAxis = new Vec3(1, 0, 0);
        Vec3 nyAxis = new Vec3(0, 1, 0);
        Vec3 nzAxis = new Vec3(0, 0, 1);

        public CuboidShape(GroupShape gruppenKnoten, Vec3 center, Vec3 size, Material material) {
                RectShape rechteck = new RectShape(new Vec3(0, 0, 0), 1, 1, material);

                ArrayList<Shape> würfelSeiten = new ArrayList<>();
                // oben
                Mat4x4 transformierungOben = move(0, 0.5, 0);

                GroupShape rechteckOben = new GroupShape(rechteck, null, transformierungOben);
                würfelSeiten.add(rechteckOben);

                // unten
                Mat4x4 transformierungUnten = multiply(move(0, -0.5, 0), rotate(nxAxis, 180));
                GroupShape rechteckUnten = new GroupShape(rechteck, null, transformierungUnten);
                würfelSeiten.add(rechteckUnten);
                // links
                Mat4x4 transformierungLinks = multiply(move(-0.5, 0, 0), rotate(nzAxis, 90));
                GroupShape rechteckLinks = new GroupShape(rechteck, null,
                                transformierungLinks);
                würfelSeiten.add(rechteckLinks);

                // rechts
                Mat4x4 transformierungRechts = multiply(move(0.5, 0, 0), rotate(nzAxis,
                                -90));
                GroupShape rechteckRechts = new GroupShape(rechteck, null,
                                transformierungRechts);
                würfelSeiten.add(rechteckRechts);

                // vorne
                Mat4x4 transformierungVorne = multiply(move(0, 0, 0.5), rotate(nxAxis, 90));
                GroupShape rechteckVorne = new GroupShape(rechteck, null,
                                transformierungVorne);
                würfelSeiten.add(rechteckVorne);

                // hinten
                Mat4x4 transformierungHinten = multiply(move(0, 0, -0.5), rotate(nxAxis, -90));
                GroupShape rechteckHinten = new GroupShape(rechteck, null,
                                transformierungHinten);
                würfelSeiten.add(rechteckHinten);

                Mat4x4 scaling = scale(size.x(), size.y(), size.z());
                Mat4x4 translation = move(center.x(), center.y(), center.z());

                Mat4x4 globalTransform = multiply(translation, scaling);

                GroupShape fullCuboid = new GroupShape(würfelSeiten, null, globalTransform);
                gruppenKnoten.add(fullCuboid);

        }
}
