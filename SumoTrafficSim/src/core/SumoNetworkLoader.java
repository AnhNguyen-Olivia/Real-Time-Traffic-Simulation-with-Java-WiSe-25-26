package core;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class SumoNetworkLoader {

    public final Map<String, Node> nodes = new LinkedHashMap<>();
    public final List<Road> roads = new ArrayList<>();
    // junction ids that have tlLogic (traffic lights)
    public final Set<String> tlJunctionIds = new HashSet<>();

    // scale and offset applied to SUMO coordinates (meters -> pixels)
    private final double scale;
    private final double offsetX;
    private final double offsetY;

    public SumoNetworkLoader(double scale, double offsetX, double offsetY) {
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void load(String netFilePath) throws Exception {
        File f = new File(netFilePath);
        if (!f.exists()) throw new IllegalArgumentException("File not found: " + netFilePath);

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(f);
        doc.getDocumentElement().normalize();

        // 1) parse nodes
        NodeList nodeList = doc.getElementsByTagName("node");
        for (int i = 0; i < nodeList.getLength(); ++i) {

            org.w3c.dom.Node xmlNode = nodeList.item(i);
            Element e = (Element) xmlNode;

            String id = e.getAttribute("id");
            String xs = e.getAttribute("x");
            String ys = e.getAttribute("y");

            if (xs == null || ys == null || xs.isEmpty() || ys.isEmpty()) continue;
            double x = Double.parseDouble(xs);
            double y = Double.parseDouble(ys);

            // SUMO uses y increasing north? KEEP orientation; you can flip if needed:
            int px = (int) Math.round(x * scale + offsetX);
            int py = (int) Math.round(y * scale + offsetY);

            nodes.put(id, new Node(id, px, py));
        }

        // 2) parse edges and lanes (use lane shapes when present)
        NodeList edgeList = doc.getElementsByTagName("edge");
        Pattern coordPattern = Pattern.compile("[ ,]+");
        for (int i = 0; i < edgeList.getLength(); ++i) {
            Element edgeEl = (Element) edgeList.item(i);

            // skip internal edges if any (function="internal")
            if (edgeEl.hasAttribute("function") && !edgeEl.getAttribute("function").isEmpty()) continue;

            String edgeId = edgeEl.getAttribute("id");
            String from = edgeEl.getAttribute("from");
            String to = edgeEl.getAttribute("to");
            Node fromNode = nodes.get(from);
            Node toNode = nodes.get(to);
            if (fromNode == null || toNode == null) continue;

            Road road = new Road(edgeId, fromNode, toNode);

            // parse lane children
            NodeList laneList = edgeEl.getElementsByTagName("lane");
            for (int li = 0; li < laneList.getLength(); ++li) {
                Element laneEl = (Element) laneList.item(li);
                String laneId = laneEl.getAttribute("id");
                String shape = laneEl.getAttribute("shape"); // "x1,y1 x2,y2 ..."
                if (shape == null || shape.isEmpty()) {
                    // fallback: simple two-point lane from node coords:
                    List<Double> coords = new ArrayList<>();
                    coords.add((double)fromNode.x);
                    coords.add((double)fromNode.y);
                    coords.add((double)toNode.x);
                    coords.add((double)toNode.y);
                    road.lanes.add(new Lane(laneId, coords));
                } else {
                    // parse shape and scale/offset values have already been applied to nodes,
                    // but lane shapes in SUMO are in map coordinates (meters) — convert them here using same scale/offset
                    String[] pts = shape.trim().split("\\s+");
                    List<Double> coords = new ArrayList<>();
                    for (String p : pts) {
                        String[] xy = p.split(",");
                        double x = Double.parseDouble(xy[0]);
                        double y = Double.parseDouble(xy[1]);
                        coords.add(x * scale + offsetX);
                        coords.add(y * scale + offsetY);
                    }
                    road.lanes.add(new Lane(laneId, coords));
                }
            }

            roads.add(road);
        }

        // 3) parse tlLogic elements (junction ids with traffic light logic)
        NodeList tlList = doc.getElementsByTagName("tlLogic");
        for (int i = 0; i < tlList.getLength(); ++i) {
            Element t = (Element) tlList.item(i);
            String id = t.getAttribute("id");
            if (id != null && !id.isEmpty()) tlJunctionIds.add(id);
        }

        // done
    }
}