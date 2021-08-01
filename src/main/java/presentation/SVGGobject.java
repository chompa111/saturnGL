package presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.gobject.Group;
import graphical.basics.gobject.ShapeGobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SVGGobject extends Gobject {

    ArrayList<Shape> shapeList = new ArrayList<>();
    List<ShapeGobject> shapeGobjects = new ArrayList<>();

    private HashMap<String, SVGGobject> groups;

    public SVGGobject() {
    }

    public SVGGobject(String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(path);

            String xpathExpressionStyle = "//path/@style";
            String xpathExpressionD = "//path/@d";
            String xpathExpressionG = "//g/path";

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expressionD = xpath.compile(xpathExpressionD);
            XPathExpression expressionSyle = xpath.compile(xpathExpressionStyle);

            XPathExpression expressionG = xpath.compile(xpathExpressionG);

            NodeList svgPathsG = (NodeList) expressionG.evaluate(document, XPathConstants.NODESET);

            HashMap<String, List<ShapeGobject>> groupAux = new HashMap<>();
            for (int i = 0; i < svgPathsG.getLength(); i++) {
                var item = svgPathsG.item(i);

                var d = item.getAttributes().getNamedItem("d").getNodeValue();
                var style = item.getAttributes().getNamedItem("style").getNodeValue();
                var group = item.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
                System.out.print("");


                PathParser p = new PathParser();
                AWTPathProducer ph = new AWTPathProducer();
                ph.setWindingRule(1);
                p.setPathHandler(ph);
                p.parse(d);

                var shape = ph.getShape();
                System.out.println("STYLE:" + style);
                var shapegobject = new ShapeGobject(shape, style);
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


//            NodeList svgPathsStyle = (NodeList) expressionSyle.evaluate(document, XPathConstants.NODESET);
//            NodeList svgPathsD = (NodeList) expressionD.evaluate(document, XPathConstants.NODESET);
//
//            for (int i = 0; i < svgPathsD.getLength(); i++) {
//                var d = svgPathsD.item(i).getNodeValue();
//                var style = svgPathsStyle.item(i).getNodeValue();
//                PathParser p = new PathParser();
//                AWTPathProducer ph = new AWTPathProducer();
//                ph.setWindingRule(1);
//                p.setPathHandler(ph);
//                p.parse(d);
//
//                var shape = ph.getShape();
//                shapeList.add(shape);
//                shapeGobjects.add(new ShapeGobject(shape, style));
//            }
        } catch (Exception e) {
            System.out.println();
        }

    }

    @Override
    public void paint(Graphics g) {
//        for (Shape shape : shapeList) {
//            g.setColor(ColorHolder.randomColor());
//            ((Graphics2D) g).fill(shape);
//        }

        for (ShapeGobject shape : shapeGobjects) {
            shape.paint(g,true);
        }
    }

    @Override
    public LocationPair getBorders() {
        var list = new ArrayList<LocationPair>();
        for (ShapeGobject shapeGobject : shapeGobjects) {
            list.add(shapeGobject.getBorders());
        }
        return new LocationPair(list);
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
    public List<Location> getRefereceLocations() {
        var list = new ArrayList<Location>();

        for (ShapeGobject shapeGobject : shapeGobjects) {
            var aux = shapeGobject.getRefereceLocations();
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

}
