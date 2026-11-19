package app;

import cgg_tools.Color;
import cgg_tools.Vec3;

public class DirectionalLightTest {

    public static void callLightTest() {
        testDirectionLight();
    }

    public static void testDirectionLight() {
        DirectionalLight dLight = new DirectionalLight(Color.white, new Vec3(-1, -1, -0.5));
        System.out.println(dLight.color_At(new Vec3(0, 0, 0)));
        System.out.println(dLight.toLight(new Vec3(0, 0, 0)));
    }
}
