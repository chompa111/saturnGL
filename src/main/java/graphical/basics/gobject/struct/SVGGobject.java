package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Group;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.*;

public class SVGGobject extends Gobject {

    ArrayList<Shape> shapeList = new ArrayList<>();
    List<ShapeGobject2> shapeGobjects = new ArrayList<>();

    private HashMap<String, SVGGobject> groups;

    public SVGGobject() {
    }

    public SVGGobject(String path) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(path);

            String xpathExpressionG = "//g/path|//circle|//rect";//"/path";

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();

            XPathExpression expressionG = xpath.compile(xpathExpressionG);

            NodeList svgPathsG = (NodeList) expressionG.evaluate(document, XPathConstants.NODESET);

            HashMap<String, List<ShapeGobject2>> groupAux = new HashMap<>();
            for (int i = 0; i < svgPathsG.getLength(); i++) {
                var item = svgPathsG.item(i);

                var shapeType = item.getNodeName();

                Shape shape = null;

                if (shapeType.equals("rect")) {
                    var width = Double.parseDouble(item.getAttributes().getNamedItem("width").getNodeValue());
                    var height = Double.parseDouble(item.getAttributes().getNamedItem("height").getNodeValue());
                    var x = Double.parseDouble(item.getAttributes().getNamedItem("x").getNodeValue());
                    var y = Double.parseDouble(item.getAttributes().getNamedItem("y").getNodeValue());
                    shape = new Rectangle((int) x, (int) y, (int) width, (int) height);
                }

                if (shapeType.equals("circle")) {
                    var cx = Double.parseDouble(item.getAttributes().getNamedItem("cx").getNodeValue());
                    var cy = Double.parseDouble(item.getAttributes().getNamedItem("cy").getNodeValue());
                    var r = Double.parseDouble(item.getAttributes().getNamedItem("r").getNodeValue());
                    shape = new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r);
                }


                var transform = Optional.ofNullable(item.getAttributes().getNamedItem("transform"))
                        .map(Node::getNodeValue).orElse(null);
                var style = item.getAttributes().getNamedItem("style").getNodeValue();
                var group = Optional.ofNullable(item.getParentNode().getAttributes().getNamedItem("id")).map(Node::getNodeValue).orElse("pepe");
                var groupTransform = Optional.ofNullable(item.getParentNode().getAttributes().getNamedItem("transform"))
                        .map(Node::getNodeValue).orElse(null);

                var fill= Optional.ofNullable(item.getAttributes().getNamedItem("fill")).map(Node::getNodeValue).orElse(null);
                if(fill!=null){
                    fill="fill:"+fill;
                    style=fill+";"+style;
                }


                if (shapeType.equals("path")) {
                    var d = item.getAttributes().getNamedItem("d").getNodeValue();
                    PathParser p = new PathParser();
                    AWTPathProducer ph = new AWTPathProducer();
                    ph.setWindingRule(1);
                    p.setPathHandler(ph);
                    p.parse(d);
                    shape = ph.getShape();
                }

                if (transform != null) {
                    shape = getTransformFromSVG(transform).createTransformedShape(shape);
                }
                if (groupTransform != null) {
                    shape = getTransformFromSVG(groupTransform).createTransformedShape(shape);
                }


                var shapegobject = ShapeGobject2.fromSVGStyle(shape, style);
                shapeList.add(shape);
                shapeGobjects.add(shapegobject);
                if (!groupAux.containsKey(group)) {
                    groupAux.put(group, new ArrayList<>());

                }
                groupAux.get(group).add(shapegobject);
            }
            groups = new HashMap<>();
            groupAux.forEach((k, v) -> {
                var svgGobject = new SVGGobject();
                svgGobject.shapeGobjects = v;
                groups.put(k, svgGobject);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void paint(Graphics g) {
        for (ShapeGobject2 shape : shapeGobjects) {
            shape.paint(g, true);
        }
    }

    @Override
    public LocationPair getBorders() {
        var list = new ArrayList<LocationPair>();
        for (ShapeGobject2 shapeGobject : shapeGobjects) {
            list.add(shapeGobject.getBorders());
        }
        return new LocationPair(list,scale.getValue());
    }

    @Override
    public List<ColorHolder> getColors() {
        var list = new ArrayList<ColorHolder>();

        for (ShapeGobject2 shapeGobject : shapeGobjects) {
            var aux = shapeGobject.getColors();
            if (aux != null)
                list.addAll(aux);
        }

        return list;
    }

    @Override
    public List<Location> getRefereceLocations() {
        var list = new ArrayList<Location>();

        for (ShapeGobject2 shapeGobject : shapeGobjects) {
            var aux = shapeGobject.getRefereceLocations();
            if (aux != null)
                list.addAll(aux);
        }
        return list;
    }

    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }

    public List<ShapeGobject2> getShapeGobjects() {
        return shapeGobjects;
    }

    public SVGGobject getGroup(String... s) {
        var excepts = new HashSet<>(Arrays.asList(s));

        var sVGGobject = new SVGGobject();
        sVGGobject.shapeGobjects = new ArrayList<>();
        groups.forEach((k, v) -> {
            if (excepts.contains(k)) {
                sVGGobject.shapeGobjects.addAll(v.shapeGobjects);
            }
        });
        return sVGGobject;
    }

    public SVGGobject getGroupExcept(String... s) {

        var excepts = new HashSet<>(Arrays.asList(s));

        var sVGGobject = new SVGGobject();
        sVGGobject.shapeGobjects = new ArrayList<>();
        groups.forEach((k, v) -> {
            if (!excepts.contains(k)) {
                sVGGobject.shapeGobjects.addAll(v.shapeGobjects);
            }
        });

        return sVGGobject;
    }


    public Group toGroupGobject() {
        return new Group((ArrayList) shapeGobjects);
    }

    public AffineTransform getTransformFromSVG(String svgStringValue) {
        var af = new AffineTransform();
        var method = svgStringValue.substring(0, svgStringValue.lastIndexOf("("));
        var values = svgStringValue.substring(svgStringValue.lastIndexOf("(") + 1, svgStringValue.lastIndexOf(")")).split(",");

        switch (method) {
            case "translate":
                af.translate(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                break;

            case "rotate":
                if (values.length == 3) {
                    af.rotate(Math.toRadians(Double.parseDouble(values[0])), Double.parseDouble(values[1]), Double.parseDouble(values[2]));
                } else {
                    af.rotate(Math.toRadians(Double.parseDouble(values[0])));
                }
                break;
            case "scale":
                if (values.length == 1) {
                    af.scale(Double.parseDouble(values[0]), Double.parseDouble(values[0]));
                } else {
                    af.scale(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                }
                break;

            case "matrix":

                af = new AffineTransform(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]),
                        Float.parseFloat(values[4]), Float.parseFloat(values[5]));
        }

        return af;
    }
}
