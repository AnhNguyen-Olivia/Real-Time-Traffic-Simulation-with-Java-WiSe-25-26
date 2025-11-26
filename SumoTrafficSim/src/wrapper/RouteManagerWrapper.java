package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import java.util.ArrayList;
import java.util.List;


public class RouteManagerWrapper {
    private final SumoTraciConnection connection;
    
    public RouteManagerWrapper(SumoTraciConnection connection) {
        this.connection = connection;
    }

    public RouteWrapper getRoute(String id) {
        return new RouteWrapper(id, connection);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllRouteIds() throws Exception {
        return (List<String>) connection.do_job_get(
            de.tudresden.sumo.cmd.Route.getIDList());
    }
    
    public List<RouteWrapper> getAllRoutes() throws Exception {
        List<String> routeIds = getAllRouteIds();
        List<RouteWrapper> routes = new ArrayList<>();
        
        for (String routeId : routeIds) {
            routes.add(getRoute(routeId));
        }
        
        return routes;
    }

    public List<RouteWrapper> getRoutesContainingEdge(String edgeId) throws Exception {
        if (edgeId == null || edgeId.isEmpty()) {
            throw new IllegalArgumentException("Edge ID cannot be null or empty");
        }   
        
        List<RouteWrapper> matchingRoutes = new ArrayList<>();
        List<String> routeIds = getAllRouteIds();
        
        for (String routeId : routeIds) {
            RouteWrapper route = getRoute(routeId);
            if (route.containsEdge(edgeId)) {
                matchingRoutes.add(route);
            }
        }
        
        return matchingRoutes;
    }

    public List<RouteWrapper> getLongRoutes(int minEdgeCount) throws Exception {
        if (minEdgeCount < 0) {
            throw new IllegalArgumentException("Minimum edge count must be non-negative");
        }
        
        List<RouteWrapper> longRoutes = new ArrayList<>();
        List<String> routeIds = getAllRouteIds();
        
        for (String routeId : routeIds) {
            RouteWrapper route = getRoute(routeId);
            if (route.getEdgeCount() >= minEdgeCount) {
                longRoutes.add(route);
            }
        }
        
        return longRoutes;
    }
}
