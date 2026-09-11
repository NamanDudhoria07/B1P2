import java.util.*;
class BusRoute {
    String code, name;
    int priority;

    public BusRoute(String code, String name, int priority) {
        this.code = code;
        this.name = name;
        this.priority = priority;
    }

    public BusRoute(String code, String name) {
        this(code, name, 0);
    }

    public int compareTo(BusRoute other) {
        if (priority != other.priority)
            return priority - other.priority;

        return code.compareTo(other.code);
    }

    static BusRoute[] rankRoutes(BusRoute[] routes) {
        // Bubble sort - stable and no built-in sort
        for (int i = 0; i < routes.length - 1; i++)
            for (int j = 0; j < routes.length - i - 1; j++)
                if (routes[j].compareTo(routes[j + 1]) > 0) {
                    BusRoute t = routes[j];
                    routes[j] = routes[j + 1];
                    routes[j + 1] = t;
                }

        return routes;
    }
}

public class Bus_route_Ranking_Engine {
    public static void main(String[] args) {

        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] result = BusRoute.rankRoutes(routes);

        for (BusRoute r : result)
            System.out.println(r.code);
    }
}