package codec;

import codec.engine.EngineType;
import com.sun.jdi.ArrayReference;
import graphical.basics.gobject.CircleBuilder;
import graphical.basics.gobject.TextGobject;
import graphical.basics.gobject.latex.Rect;
import graphical.basics.gobject.struct.Gobject;
import graphical.basics.location.Location;
import graphical.basics.presentation.Positioning;
import graphical.basics.presentation.Presentation;
import graphical.basics.presentation.PresentationConfig;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BenchMark {


    public static void runBenchMark() {


        var presenatation = new Presentation() {

            @Override
            public void setup(PresentationConfig presentationConfig) {
                presentationConfig.setEngine(EngineType.NATIVE_JAVA);
                presentationConfig.setDisableCodec(true);
            }

            @Override
            protected void buildPresentation() {

            }
        };

        try {
            var resultsNative = doBenchMark(EngineType.NATIVE_JAVA);
            presenatation.add(new TextGobject(new Font(Font.DIALOG, Font.PLAIN, 30), "Engine: Java-Native", Location.at(20, 150), Color.white));

            for (int i = 0; i < resultsNative.size(); i++) {
                var rect = new Rect(Location.at(50, 200 + i * 110), Location.at(50 + (resultsNative.get(i) / 10), (200 + i * 110) + 50), Color.orange);
                var text = new TextGobject(resultsNative.get(i) + ":ms", Location.at(500, 500), Color.white);
                Positioning.align(text, rect, Positioning.Reference.LEFT);

                presenatation.add(text);
                presenatation.add(rect);

            }

        } catch (Exception e) {
            presenatation.add(new TextGobject(new Font(Font.DIALOG, Font.ITALIC, 40), "Not able to run beanchmark with Engine: Native-Java", Location.at(20, 150), Color.red));
        }

        try {
            var resultasJFX = doBenchMark(EngineType.JAVAFX);

            presenatation.add(new TextGobject(new Font(Font.DIALOG, Font.PLAIN, 30), "Engine: JavaFx", Location.at(20, 550), Color.white));

            for (int i = 0; i < resultasJFX.size(); i++) {
                var rect = new Rect(Location.at(50, 600 + i * 110), Location.at(50 + (resultasJFX.get(i) / 10), (600 + i * 110) + 50), Color.BLUE);
                var text = new TextGobject(resultasJFX.get(i) + ":ms", Location.at(500, 500), Color.white);
                Positioning.align(text, rect, Positioning.Reference.LEFT);

                presenatation.add(text);
                presenatation.add(rect);

            }
        } catch (Exception e) {
            presenatation.add(new TextGobject(new Font(Font.DIALOG, Font.ITALIC, 40), "Not able to run beanchmark with Engine: JavaFX", Location.at(20, 550), Color.red));
        }


        presenatation.build();


    }


    private static List<Long> doBenchMark(EngineType engineType) {


        var results = new ArrayList<Long>();

        var presenatation = new Presentation() {

            @Override
            public void setup(PresentationConfig presentationConfig) {
                presentationConfig.setEngine(engineType);
                presentationConfig.setPreviewWindowBarVisible(false);
                presentationConfig.setDisableCodec(true);
                presentationConfig.setDisablePreview(true);
            }

            @Override
            protected void buildPresentation() {

            }
        };

        Field gobjectsField = getGobjectField();
        gobjectsField.setAccessible(true);
        Method processFrameMethod = getProcessFrameMethod();


        for (int j = 0; j < 1000; j++) {
            var bola = CircleBuilder.aCircle().build();
            addGobject(presenatation, gobjectsField, bola);
        }
        long before = System.currentTimeMillis();
        processFrame(processFrameMethod, presenatation);
        results.add(System.currentTimeMillis() - before);


        for (int j = 0; j < 9000; j++) {
            var bola = CircleBuilder.aCircle().build();
            addGobject(presenatation, gobjectsField, bola);
        }
        before = System.currentTimeMillis();
        processFrame(processFrameMethod, presenatation);
        results.add(System.currentTimeMillis() - before);

        for (int j = 0; j < 90000; j++) {
            var bola = CircleBuilder.aCircle().build();
            addGobject(presenatation, gobjectsField, bola);
        }
        before = System.currentTimeMillis();
        processFrame(processFrameMethod, presenatation);
        results.add(System.currentTimeMillis() - before);

        return results;


    }

    private static Field getGobjectField() {
        try {
            return Presentation.class.getDeclaredField("gobjects");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Method getProcessFrameMethod() {
        try {
            return Presentation.class.getDeclaredMethod("processFrame");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void processFrame(Method method, Presentation presentation) {
        try {
            method.invoke(presentation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void addGobject(Presentation p, Field f, Gobject g) {
        try {
            var gobjects = (ArrayList<Gobject>) f.get(p);
            gobjects.add(g);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BenchMark.runBenchMark();
    }
}
