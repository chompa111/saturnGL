package graphical.basics.gobject.struct;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Group;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
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

            String xpathExpressionG = "//g/path";

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();

            XPathExpression expressionG = xpath.compile(xpathExpressionG);

            NodeList svgPathsG = (NodeList) expressionG.evaluate(document, XPathConstants.NODESET);

            HashMap<String, List<ShapeGobject2>> groupAux = new HashMap<>();
            for (int i = 0; i < svgPathsG.getLength(); i++) {
                var item = svgPathsG.item(i);

                var d = item.getAttributes().getNamedItem("d").getNodeValue();
                var style = item.getAttributes().getNamedItem("style").getNodeValue();
                var group = item.getParentNode().getAttributes().getNamedItem("id").getNodeValue();


                PathParser p = new PathParser();
                AWTPathProducer ph = new AWTPathProducer();
                ph.setWindingRule(1);
                p.setPathHandler(ph);
                p.parse(d);

                var shape = ph.getShape();
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
            System.out.println(2);
        }

    }

    @Override
    public void paint(Graphics g) {
        for (ShapeGobject2 shape : shapeGobjects) {
            shape.paint(g,true);
        }
    }

    @Override
    public LocationPair getBorders() {
        var list = new ArrayList<LocationPair>();
        for (ShapeGobject2 shapeGobject : shapeGobjects) {
            list.add(shapeGobject.getBorders());
        }
        return new LocationPair(list);
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

}
