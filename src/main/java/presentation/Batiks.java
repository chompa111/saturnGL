package presentation;

import graphical.basics.ColorHolder;
import graphical.basics.gobject.Gobject;
import graphical.basics.gobject.ShapeGobject;
import graphical.basics.location.Location;
import graphical.basics.location.LocationPair;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.apache.xalan.xsltc.DOM;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Batiks extends Gobject {

    ArrayList<Shape> shapeList = new ArrayList<>();
    ArrayList<ShapeGobject> shapeGobjects = new ArrayList<>();


    public Batiks() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\saturn-font-logo.svg");

            String xpathExpressionStyle = "//path/@style";
            String xpathExpressionD = "//path/@d";

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expressionD = xpath.compile(xpathExpressionD);
            XPathExpression expressionSyle = xpath.compile(xpathExpressionStyle);
            NodeList svgPathsD = (NodeList) expressionD.evaluate(document, XPathConstants.NODESET);
            NodeList svgPathsStyle = (NodeList) expressionSyle.evaluate(document, XPathConstants.NODESET);


            for (int i = 0; i < svgPathsD.getLength(); i++) {
                var d = svgPathsD.item(i).getNodeValue();
                var style = svgPathsStyle.item(i).getNodeValue();
                PathParser p = new PathParser();
                AWTPathProducer ph = new AWTPathProducer();
                ph.setWindingRule(1);
                p.setPathHandler(ph);
                p.parse(d);

                var shape=ph.getShape();
                shapeList.add(shape);
                shapeGobjects.add(new ShapeGobject(shape,style));
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void paint(Graphics g) {
//        for (Shape shape : shapeList) {
//            g.setColor(ColorHolder.randomColor());
//            ((Graphics2D) g).fill(shape);
//        }

        for(ShapeGobject shape: shapeGobjects){
            shape.paint(g);
        }
    }

    @Override
    public LocationPair getBorders() {
        return null;
    }

    @Override
    public List<ColorHolder> getColors() {
        return null;
    }

    @Override
    public List<Location> getRefereceLocations() {
        return null;
    }
}
