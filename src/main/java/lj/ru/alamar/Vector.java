package lj.ru.alamar;

import static java.lang.Math.sqrt;

public class Vector {
    private static final float EPSILON = 1e-30f;
    private static final Vector ZERO = new Vector(0, 0, 0, 0);

    public final float x, y, z, t;

    public Vector(float x, float y, float z, float t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    boolean isReduced() {
        return x == 0;
    }

    boolean isZero() {
        return x == 0 && y == 0 && z == 0 && t == 0;
    }

    boolean isUnit() {
        return 1.0 - sqrt(x * x + y * y + z * z + t * t) < EPSILON;
    }

    public Vector plusUnit(Vector o) {
        float norm = (float)sqrt((x + o.x) * (x + o.x) +
            (y + o.y) * (y + o.y) +
            (z + o.z) * (z + o.z) +
            (t + o.t) * (t + o.t));

        Vector sum = new Vector(
            (x + o.x) / norm,
            (y + o.y) / norm,
            (z + o.z) / norm,
            (t + o.t) / norm);

        return sum;
    }

    public Vector plus(Vector o) {
        Vector sum = new Vector(x + o.x, y + o.y, z + o.z, t + o.t);

        return sum;
    }


    public static Vector zero() {
        return ZERO;
    }

    public static Vector x(float x) {
        return new Vector(x, 0,0,0);
    }

    public static Vector t(float t) {
        return new Vector(0, 0, 0, t);
    }

    public Vector times(float n) {
        return new Vector(x * n, y * n, z * n, t * n);
    }

    public boolean nearZero() {
        return length() < EPSILON;
    }

    public float length() {
        return (float) sqrt(x * x + y * y + z * z + t * t);
    }

    public Vector norm() {
        if (nearZero())
            return t(1);
        else
            return plusUnit(new Vector(0, 0, 0,  0));
    }
}
