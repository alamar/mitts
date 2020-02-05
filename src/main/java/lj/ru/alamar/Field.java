package lj.ru.alamar;

import java.util.List;

public class Field {
    private List<Event> masses;

    public Field(List<Event> masses) {
        this.masses = masses;
    }

    public Vector gradient(Vector loc) {
        Vector gradient = Vector.zero();

        for (Event event : masses) {
            Vector massLoc = event.loc;
            Vector distance = massLoc.plus(loc.times(-1f));
            if (distance.nearZero())
                continue;

            gradient = gradient.plus(distance.norm()
                .times((1 / (distance.length() * distance.length())) * ((Mass)event.matter).mass));
        }

        return gradient;
    }
}
