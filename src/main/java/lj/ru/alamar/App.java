package lj.ru.alamar;

import java.util.ArrayList;
import java.util.List;
import lj.ru.alamar.utils.MittsToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        List<Event> events = new ArrayList<>();

        /**Event large = new Event();
        large.matter = new Mass(0.1f);
        large.dir = Vector.t(1);
        large.loc = new Vector(0, 10, 0, 0);

        events.add(large);

        Event small = new Event();
        small.matter = new Mass(0.01f);
        small.dir = Vector.t(1).plusUnit(new Vector(0, 0.1f, 0, 0));
        small.loc = new Vector(0, 0, 10, 0);

        events.add(small);

        Event small2 = new Event();
        small2.matter = new Mass(0.01f);
        small2.dir = Vector.t(1).plusUnit(new Vector(0, -0.1f, 0, 0));
        small2.loc = new Vector(0, 0, -10, 0);

        events.add(small2);*/

        /*Event m1 = new Event();
        m1.matter = new Mass(0.01f);
        m1.dir = Vector.t(1).plusUnit(new Vector(0, 0, -0.01f, 0));
        m1.loc = new Vector(0, -10, 0, 0);

        events.add(m1);

        Event m2 = new Event();
        m2.matter = new Mass(0.01f);
        m2.dir = Vector.t(1).plusUnit(new Vector(0, 0, 0.01f, 0));
        m2.loc = new Vector(0, 10, 0, 0);

        events.add(m2);*/

        Event centralBody = new Event();
        centralBody.matter = new Mass(0.1f);
        centralBody.dir = Vector.t(1);
        centralBody.loc = new Vector(0, 0, 0, 0);

        events.add(centralBody);

        Event escaper = new Event();
        escaper.matter = new Mass(0.001f);
        escaper.dir = Vector.t(1).plusUnit(new Vector(0, 0, 0.1f, 0));
        escaper.loc = new Vector(0, 10, 0, 0);

        events.add(escaper);

        simulate(events, 5000, 10);
    }

    public static void simulate(List<Event> events, float depth, int delta) {
        List<Mass> masses = new ArrayList();
        for (Event event : events) {
            assert event.matter instanceof Mass;
            assert event.dir.isUnit();
            assert event.loc.isReduced();
            assert event.loc.t == 0;
            assert event.t == 0;
        }

        List<Event> nextEvents;
        while (depth-- > 0) {

            if ((depth % delta) == 0L) {
                for (Event event : events)
                    System.out.println(String.format("translate([%s, %s, %s]) sphere(1);", event.loc.y, event.loc.z, event.loc.t / (float)delta));
            }

            nextEvents = new ArrayList<>();

            Field field = new Field(events);

            for (Event event : events) {
                Event next = new Event();
                next.matter = event.matter;

                Vector gradient = field.gradient(event.loc);
                next.dir = event.dir.plusUnit(gradient);
                next.loc = event.loc.plus(next.dir);

                next.t = event.t + 1;

                nextEvents.add(next);
            }

            events = nextEvents;
        };

        for (Event event : events)
            System.err.println(ToStringBuilder.reflectionToString(event, new MittsToStringStyle()));
    }
}
