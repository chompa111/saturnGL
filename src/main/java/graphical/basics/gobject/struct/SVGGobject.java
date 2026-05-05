package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.animations.ifood2.SVGShapeExtractor;
import graphical.basics.gobject.Group;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import graphical.basics.value.DoubleHolder;
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
import java.util.stream.Collectors;

public class SVGGobject extends Gobject {

    ArrayList<Shape> shapeList = new ArrayList<>();
    List<ShapeGobject> shapeGobjects = new ArrayList<>();

    private HashMap<String, SVGGobject> groups;

    public SVGGobject() {
    }

    public SVGGobject(String path, Color color, Color color2) {
        try {
            SVGShapeExtractor.extractShapes(path)
                    .stream()
                    .peek(shapeList::add)
                    .map(s -> new ShapeGobject(s, color,color2))
                    .forEach(shapeGobjects::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            HashMap<String, List<ShapeGobject>> groupAux = new HashMap<>();
            for (int i = 0; i < svgPathsG.getLength(); i++) {
                var item = svgPathsG.item(i);

                var shapeType = item.getNodeName();

                Shape shape = null;

                if (shapeType.equals("rect")) {
                    var width = Double.parseDouble(item.getAttributes().getNamedItem("width").getNodeValue());
                    var height = Double.parseDouble(item.getAttributes().getNamedItem("height").getNodeValue());
                    var x = item.getAttributes().getNamedItem("x") == null ? 0 : Double.parseDouble(item.getAttributes().getNamedItem("x").getNodeValue());
                    var y = item.getAttributes().getNamedItem("y") == null ? 0 : Double.parseDouble(item.getAttributes().getNamedItem("y").getNodeValue());
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
                var style = item.getAttributes().getNamedItem("style") == null ? "" : item.getAttributes().getNamedItem("style").getNodeValue();
                var group = Optional.ofNullable(item.getParentNode().getAttributes().getNamedItem("id")).map(Node::getNodeValue).orElse("pepe");
                var groupTransform = Optional.ofNullable(item.getParentNode().getAttributes().getNamedItem("transform"))
                        .map(Node::getNodeValue).orElse(null);

                var fill = Optional.ofNullable(item.getAttributes().getNamedItem("fill")).map(Node::getNodeValue).orElse(null);
                if (fill != null) {
                    fill = "fill:" + fill;
                    style = fill + ";" + style;
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


                var shapegobject = ShapeGobject.fromSVGStyle(shape, style);
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
        for (ShapeGobject shape : shapeGobjects) {
            shape.paint(g, true);
        }
    }

    @Override
    public LocationPair getBorders() {
        var list = new ArrayList<LocationPair>();
        for (ShapeGobject shapeGobject : shapeGobjects) {
            list.add(shapeGobject.getBorders());
        }
        return new LocationPair(list, scale.getValue());
    }

    @Override
    public List<ColorHolder> getColors() {
        var list = new ArrayList<ColorHolder>();

        for (ShapeGobject shapeGobject : shapeGobjects) {
            var aux = shapeGobject.getColors();
            if (aux != null)
                list.addAll(aux);
        }

        return list;
    }

    @Override
    public List<Location> getReferenceLocations() {
        var list = new ArrayList<Location>();

        for (ShapeGobject shapeGobject : shapeGobjects) {
            var aux = shapeGobject.getReferenceLocations();
            if (aux != null)
                list.addAll(aux);
        }
        return list;
    }

    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }

    public List<ShapeGobject> getShapeGobjects() {
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

    public void resize(double factor) {
        var transform = new AffineTransform();
        var mid = this.getMidPoint();
        // Traduzir o objeto para a origem
        transform.translate(mid.getX(), mid.getY());
        // Aplicar a escala
        transform.scale(factor, factor);
        // Traduzir o objeto de volta ao seu ponto central original
        transform.translate(-mid.getX(), -mid.getY());
        // Aplicar a transformação ao shape original
        shapeGobjects.forEach(s -> s.resize(transform));
        setPositionTo(mid);
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

    @Override
    public Gobject copy() {
        var copy = new SVGGobject();
        copy.shapeGobjects = shapeGobjects.stream().map(ShapeGobject::copy)
                .map(c -> (ShapeGobject) c).collect(Collectors.toList());
        copy.scale = new DoubleHolder(scale.getValue());
        return copy;
    }
}
