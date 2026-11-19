package app.Shapes;

import static cgg_tools.Mat4x4.*;

import javax.swing.GroupLayout.Group;

import app.Hit;
import app.Material;
import app.Ray;
import cgg_tools.*;

public class RoboBall implements Shape {
        private GroupShape root;

        public RoboBall(Mat4x4 transformation, Vec3 positionTorso, double sizeschulterGelenk, double sizeTorso,
                        double sizeArm, double schulterRotation, double ellbogenRotation, double fußgelenkRotation,
                        Material material, Color ambient_light) {

                // Torso
                SphereShape torso = new SphereShape(positionTorso, sizeTorso, material);
                this.root = new GroupShape(torso, transformation, ambient_light); // Root mit globaler Transformation
                System.out.println("Torso Position: " + positionTorso);

                // Schulter Gelenk Rechts
                Mat4x4 schulterGelenkTransform = multiply(

                                move(positionTorso.x() + sizeTorso * sizeschulterGelenk,
                                                positionTorso.y() + sizeTorso * 0.45,
                                                positionTorso.z()),

                                rotate(new Vec3(0, 0, 1), -70),
                                rotate(0, 1, 0, schulterRotation));

                System.out.println("SchultergelenkktransformVektor" + schulterGelenkTransform);
                ClosedCylinderShape schulterGelenkShape = new ClosedCylinderShape(sizeArm * 1.3, 0, 0.3,
                                material, material);
                GroupShape schulterGelenk = new GroupShape(schulterGelenkShape,
                                schulterGelenkTransform,
                                ambient_light);

                // Ober Arm Rechts
                Mat4x4 oberArmTransform = multiply(
                                move(0, sizeArm * 0.5, 0),
                                rotate(new Vec3(0, 0, 1), 0));
                ClosedCylinderShape upperArmShape = new ClosedCylinderShape(sizeArm, 0, 1,
                                material, material);
                GroupShape upperArm = new GroupShape(upperArmShape, oberArmTransform, ambient_light);

                // Ellbogen Gelenk Rechts (am Ende des Oberarms)
                Mat4x4 ellBogenGelenk = multiply(move(0, 1, 0), rotate(0, 0, 1, ellbogenRotation));
                SphereShape ellbogenShape = new SphereShape(new Vec3(0, 0, 0), sizeArm * 1.2, material);
                GroupShape ellbogen = new GroupShape(ellbogenShape,
                                ellBogenGelenk,
                                ambient_light);

                // Mittel Arm rechts (relativ zum Ellbogengelenk)
                Mat4x4 lowerArmTransform = multiply(rotate(new Vec3(0, 0, 1), -105), move(0, sizeArm * -3, 0));
                ClosedCylinderShape lowerArmShape = new ClosedCylinderShape(sizeArm, 1, 3, material, material);
                GroupShape lowerArm = new GroupShape(lowerArmShape, lowerArmTransform, ambient_light);

                // Fußgelenk
                Mat4x4 handgelenkTransform = multiply(move(0, 2.9, 0), rotate(0, 0, 1, fußgelenkRotation));
                SphereShape handgelenkShape = new SphereShape(new Vec3(0, 0, 0), sizeArm * 1.3, material);
                GroupShape handGelenk = new GroupShape(handgelenkShape, handgelenkTransform, ambient_light);
                System.err.println("Handgelenk daten" + handgelenkShape);

                schulterGelenk.add(upperArm);
                upperArm.add(ellbogen);
                ellbogen.add(lowerArm);
                lowerArm.add(handGelenk);
                this.root.add(schulterGelenk);

                // Schulter Gelenk Links
                Mat4x4 schulterGelenkLinksTransform = multiply(
                                move(positionTorso.x() - sizeTorso * sizeschulterGelenk,
                                                positionTorso.y() + sizeTorso * 0.45,
                                                positionTorso.z()),
                                rotate(new Vec3(0, 0, 1), 70), // Gespiegelte Rotation
                                rotate(0, 1, 0, schulterRotation));

                ClosedCylinderShape schulterGelenkLinksShape = new ClosedCylinderShape(sizeArm * 1.3, 0, 0.3,
                                material, material);
                GroupShape schulterGelenkLinks = new GroupShape(schulterGelenkLinksShape,
                                schulterGelenkLinksTransform,
                                ambient_light);

                // Ober Arm Links
                Mat4x4 oberArmLinksTransform = multiply(
                                move(0, sizeArm * 0.5, 0),
                                rotate(new Vec3(0, 0, 1), 0));
                ClosedCylinderShape upperArmLinksShape = new ClosedCylinderShape(sizeArm, 0, 1,
                                material, material);
                GroupShape upperArmLinks = new GroupShape(upperArmLinksShape, oberArmLinksTransform, ambient_light);

                // Ellbogen Gelenk Links
                Mat4x4 ellBogenGelenkLinks = multiply(move(0, 1, 0), rotate(0, 0, 1, ellbogenRotation));
                SphereShape ellbogenLinksShape = new SphereShape(new Vec3(0, 0, 0), sizeArm * 1.2, material);
                GroupShape ellbogenLinks = new GroupShape(ellbogenLinksShape,
                                ellBogenGelenkLinks,
                                ambient_light);

                // Unter Arm Links
                Mat4x4 lowerArmLinksTransform = multiply(rotate(new Vec3(0, 0, 1), 105), move(0, sizeArm * -3, 0)); // Gespiegelte
                                                                                                                    // Rotation
                ClosedCylinderShape lowerArmLinksShape = new ClosedCylinderShape(sizeArm, 1, 3, material, material);
                GroupShape lowerArmLinks = new GroupShape(lowerArmLinksShape, lowerArmLinksTransform, ambient_light);

                // Handgelenk Links
                Mat4x4 handgelenkLinksTransform = multiply(move(0, 2.9, 0), rotate(0, 0, 1, fußgelenkRotation));
                SphereShape handgelenkLinksShape = new SphereShape(new Vec3(0, 0, 0), sizeArm * 1.3, material);
                GroupShape handGelenkLinks = new GroupShape(handgelenkLinksShape, handgelenkLinksTransform,
                                ambient_light);

                // Hierarchie für linke Seite
                schulterGelenkLinks.add(upperArmLinks);
                upperArmLinks.add(ellbogenLinks);
                ellbogenLinks.add(lowerArmLinks);
                lowerArmLinks.add(handGelenkLinks);
                this.root.add(schulterGelenkLinks);

        }

        @Override
        public Hit intersect(Ray ray) {
                return root.intersect(ray);
        }

}
