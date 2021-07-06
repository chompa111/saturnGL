package presentation;

import java.awt.*;
import java.io.IOException;

import graphical.basics.gobject.Polygon;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMPathElement;
import org.apache.batik.anim.dom.SVGPathSupport;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.GenericDOMImplementation;
//import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
//import org.apache.batik.dom.svg.SVGOMPathElement;
//import org.apache.batik.dom.svg.SVGPathSupport;
import org.apache.batik.gvt.GraphicsNode;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGPoint;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Converter {

    ArrayList<ArrayList<SVGPoint>> pathPoints;
    int separate_points;

    /**
     * Constructor
     */
    public Converter(int in_separate_points) {
        pathPoints = new ArrayList<ArrayList<SVGPoint>>();
        separate_points = in_separate_points;
    }

    public List<Polygon> polygons() {
        var polygons = new ArrayList<Polygon>();
        for (ArrayList<SVGPoint> points : pathPoints) {
            double[] xs = new double[points.size()];
            double[] ys = new double[points.size()];

            for (int i = 0; i < points.size(); i++) {
                xs[i] = points.get(i).getX();
                ys[i] = points.get(i).getY();
            }
            polygons.add(new Polygon(xs, ys, Color.magenta));

        }

        return polygons;
    }

    public void readPathSvg(String f) {

        try {
            SVGDocument svgDoc;
            UserAgent userAgent;
            DocumentLoader loader;
            BridgeContext ctx;
            GVTBuilder builder;
            GraphicsNode rootGN;

            userAgent = new UserAgentAdapter();
            loader = new DocumentLoader(userAgent);
            ctx = new BridgeContext(userAgent, loader);
            ctx.setDynamicState(BridgeContext.DYNAMIC);
            builder = new GVTBuilder();


            URI fileURI = new File(f).toURI();
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory svgf = new SAXSVGDocumentFactory(parser);
            svgDoc = (SVGDocument) svgf.createDocument(fileURI.toString());

            rootGN = builder.build(ctx, svgDoc);

            //System.out.println(rootGN.STROKE);

            NodeList paths = svgDoc.getElementsByTagName("path");

            for (int i = 0; i < paths.getLength(); i++) {

                SVGOMPathElement path = (SVGOMPathElement) paths.item(i);
                //System.out.println(i +":" + path.getAttribute("d"));
                pathPoints.add(new ArrayList<SVGPoint>());

                //System.out.println( path.getTotalLength() );

                float total_path_length = path.getTotalLength();
                float unit_length = total_path_length / (float) separate_points;

                for (int j = 0; j < separate_points; j++) {

                    //SVGPoint tmp_point = path.getPointAtLength(unit_lenght*separate_points);
                    SVGPoint tmp_point = SVGPathSupport.getPointAtLength(path, unit_length * j);
                    pathPoints.get(i).add(tmp_point);
                    //System.out.println(j + " x:" + tmp_point.getX() +" y:"+ tmp_point.getY());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writePolygonSvg(String filename) {

        try {
            // Get a DOMImplementation
            DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
            String svgNamespaceURI = "http://www.w3.org/2000/svg";

            // Create an instance of org.w3c.dom.Document
            Document document = domImpl.createDocument(svgNamespaceURI, "svg", null);

            // create root
            Element root = document.getDocumentElement();
            this.setSvgHeader(root, svgNamespaceURI);


            // Add polygon tag
            addPolygonPoints(document, root, svgNamespaceURI);

            DOMSource source = new DOMSource(document);
            File writeXML = new File(filename);
            FileOutputStream fos = new FileOutputStream(writeXML);
            StreamResult sr = new StreamResult(fos);

            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(source, sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSvgHeader(Element root, String ns) {
        root.setAttributeNS(ns, "width", "800px");
        root.setAttributeNS(ns, "height", "600px");
        root.setAttributeNS(ns, "x", "0px");
        root.setAttributeNS(ns, "y", "0px");
        root.setAttributeNS(ns, "enable-background", "new 0 0 800 600");
    }

    private void addPolygonPoints(Document document, Element root, String ns) {


        for (int i = 0; i < pathPoints.size(); i++) {
            Element polygon_element = document.createElement("polygon");
            root.appendChild(polygon_element);

            String points_value = "";

            for (int j = 0; j < pathPoints.get(i).size(); j++) {

                points_value = points_value + pathPoints.get(i).get(j).getX() + ","
                        + pathPoints.get(i).get(j).getY() + " ";
            }

            polygon_element.setAttributeNS(ns, "points", points_value);
            polygon_element.setAttributeNS(ns, "stroke", "#000000");
            polygon_element.setAttributeNS(ns, "fill", "#FFFFFF");
        }
    }

    public static void main(String[] args) throws IOException {


        int separation_points = 100;


        Converter cv = new Converter(separation_points);
        cv.readPathSvg("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\src\\main\\resources\\saturn-font-logo.svg");
        // cv.writePolygonSvg(out_filename);

         var polis=cv.polygons();
        System.out.println();

    }
}