package app.Shapes;

import app.Hit;
import app.Ray;

public interface Shape {

    abstract Hit intersect(Ray ray);

}
