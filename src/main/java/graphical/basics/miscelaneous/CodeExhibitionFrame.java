package graphical.basics.miscelaneous;

import codec.engine.EngineType;
import graphical.basics.gobject.*;
import graphical.basics.presentation.AnimationContext;
import graphical.basics.presentation.PresentationConfig;
import graphical.basics.presentation.RTAnimation;
import graphical.basics.task.WaitTask;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fxyz3d.importers.obj.ObjImporter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static graphical.basics.gobject.JavaHilighter.INTELLIJ_BACKGROUND;

public class CodeExhibitionFrame<T> extends RTAnimation implements AnimationContext {

    @Override
    public void setup(PresentationConfig presentationConfig) {
        presentationConfig.setEngine(EngineType.JAVAFX);
        presentationConfig.setFramerate(60);
    }

    @Override
    public void buildAnimation() {

    }

    public void codeExhibitionFrame2() {
        {
            RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60) {
//
//
//            @Override
//            public void paint(Graphics g) {
//                super.paint(g);
//                g.translate(-40,0);
//                getGobjects().forEach(x->x.paint(g,true));
//            }
            };
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
            textArea.setBackground(INTELLIJ_BACKGROUND);
            textArea.setPopupMenu(new JPopupMenu("pepe"));
            textArea.setFont(Fonts.JETBRAINS_MONO.deriveFont(24f));
            RTextScrollPane sp = new RTextScrollPane(textArea);

            // You can set code content here
            textArea.setText("public class HelloWorld {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, World!\");\n\t}\n}");
            textArea.setCodeFoldingEnabled(true);

            SyntaxScheme scheme = textArea.getSyntaxScheme();
            scheme.getStyle(Token.RESERVED_WORD).foreground = JavaHilighter.INTELLIJ_ORANGE;
            scheme.getStyle(Token.DATA_TYPE).foreground = Color.blue;
            scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = false;
            scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(86, 133, 88);
            textArea.setAutoIndentEnabled(true);
            textArea.setHighlightCurrentLine(false);
            textArea.setCaretColor(JavaHilighter.INTELLIJ_GRAY);
            textArea.setForeground(JavaHilighter.INTELLIJ_GRAY);
            textArea.revalidate();
            scheme.getStyle(Token.MARKUP_TAG_DELIMITER).foreground = new Color(86, 133, 88);
//
//            var x = new JComponentGobject(Location.at(0, 0), sp);
//            add(x);

//
//            var c = CircleBuilder.aCircle().build();
//            add(c);
//
//
//            c.changeColor(Color.green).andThen(c.changeColor(Color.red)).repeat(100).executeInBackGround();
//            c.move(0, -500).forSeconds(1).andThen(c.move(0, 500).forSeconds(1)).repeat(100).executeInBackGround();
            // x.move(200, 200).andThen(x.move(-200, -200)).repeat(100).executeInBackGround();
        }
        {
            RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60) {
//
//
//            @Override
//            public void paint(Graphics g) {
//                super.paint(g);
//                g.translate(-40,0);
//                getGobjects().forEach(x->x.paint(g,true));
//            }
            };
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
            textArea.setBackground(INTELLIJ_BACKGROUND);
            textArea.setPopupMenu(new JPopupMenu("pepe"));
            textArea.setFont(Fonts.JETBRAINS_MONO.deriveFont(24f));
            RTextScrollPane sp = new RTextScrollPane(textArea);

            // You can set code content here
            textArea.setText("public class HelloWorld {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello, World!\");\n\t}\n}");
            textArea.setCodeFoldingEnabled(true);

            SyntaxScheme scheme = textArea.getSyntaxScheme();
            scheme.getStyle(Token.RESERVED_WORD).foreground = JavaHilighter.INTELLIJ_ORANGE;
            scheme.getStyle(Token.DATA_TYPE).foreground = Color.blue;
            scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).underline = false;
            scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(86, 133, 88);
            textArea.setAutoIndentEnabled(true);
            textArea.setHighlightCurrentLine(false);
            textArea.setCaretColor(JavaHilighter.INTELLIJ_GRAY);
            textArea.setForeground(JavaHilighter.INTELLIJ_GRAY);
            textArea.revalidate();
            scheme.getStyle(Token.MARKUP_TAG_DELIMITER).foreground = new Color(86, 133, 88);

            CompletionProvider provider = createCompletionProvider();

            // An AutoCompletion acts as a "middle-man" between a text component
            // and a CompletionProvider. It manages any options associated with
            // the auto-completion (the popup trigger key, whether to display a
            // documentation window along with completion choices, etc.). Unlike
            // CompletionProviders, instances of AutoCompletion cannot be shared
            // among multiple text components.
            AutoCompletion ac = new AutoCompletion(provider);
            ac.install(textArea);

            //  LanguageSupportFactory.get().register(textArea);
            LanguageSupportFactory lsf = LanguageSupportFactory.get();
            JavaLanguageSupport support = (JavaLanguageSupport) lsf.
                    getSupportFor(SyntaxConstants.SYNTAX_STYLE_JAVA);


            support.install(textArea);


//            var x = new JComponentGobject(Location.at(0, 0), sp);
//            new WaitTask(seconds(2)).execute();
//            add(x);

//            Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
//            Browser browser = engine.newBrowser();
//            BrowserView view = BrowserView.newInstance(browser);
//
//            var fx = WebPageEmbeder.secrect("http://www.google.com");
//            var w = new SaturnContainer(fx);
//            add(w);

            boolean useOsr = false;
            try {
                var browser  = new Browser2("http://www.google.com", false, false, new String[]{});
                var satBrowser= new SaturnContainer(browser.getPanel()){
                };
               // add(satBrowser);

                new WaitTask(seconds(10)).andThen(satBrowser.move(100,100).andThen(satBrowser.move(-100,-100)).repeat(100)).executeInBackGround();
            } catch (UnsupportedPlatformException e) {
                e.printStackTrace();
            } catch (CefInitializationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




//
//            var fx2 = WebPageEmbeder.secrect("http://www.google.com");
//            var w2 = new SaturnContainer(fx2);
//            w2.changeSetPosition(500, 0);
//            add(w2);

//            ExternalWindowEmbeddingExample example = new ExternalWindowEmbeddingExample();
//            example.setVisible(true);

//            var x=new SaturnContainer(sp);
//            add(x);

            var jfxP = new JFXPanel();
            jfxP.setSize(1000, 1000);

            var w1 = new SaturnContainer(jfxP);
            Platform.runLater(() -> initFX(jfxP));
            //w.setPositionTo(Location.at(500,500));
           // add(w1);

           // w1.move(400, 400).andThen(w1.move(-400, -400)).repeat(1000).executeInBackGround();


            var jfxP2 = new JFXPanel();
            jfxP2.setSize(1000, 1000);

            var w12 = new SaturnContainer(jfxP2);
            Platform.runLater(() -> initFX2(jfxP2));
            // w12.setPositionTo(Location.at(500,500));
            add(w12);

//            for(int i=1;i<2;i++){
//                var y=new SaturnContainer(jfxP);
//                y.changeSetPosition(Math.random()*1000,Math.random()*1000);
//                add(y);
//            }

//            new WaitTask(seconds(3)).execute();

            //w.transform(CircleBuilder.aCircle().build()).executeInBackGround();
            // w.getAngle().change(0.7).forSeconds(3).executeInBackGround();
//
//            w.move(400,400).andThen(w.move(-400,-400)).repeat(100).executeInBackGround();

//            add(new SaturnContainer(new JFrame(){
//                {
//                    setSize(200,200);
//                    setVisible(true);
//
//                }
//            }));


            for (int i = 0; i < 100; i++) {
                var c = CircleBuilder.aCircle().withColor(Color.red).withRadius(7).build();
                add(c);
                int finalI = i;
                c.addBehavior(() -> {
                    c.setColor(new Color(finalI % 255, (finalI * finalI) % 255, (finalI * finalI) % 255));
                });
                c.move(0, 0).forFrames(1).andThen(() -> c.move(Math.random() * 100 - 50, Math.random() * 100 - 50)).repeat(100).executeInBackGround();
            }
            var c = CircleBuilder.aCircle().withRadius(400).build();
            add(c);
            addClickListener(c,()-> System.out.println("peba"));
            addDragBehavior(c);


            //x.setPositionTo(Location.at(0,40));

            //        x.move(200, 200).andThen(x.move(-200, -200)).repeat(100).executeInBackGround();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        var ref = new Object() {
            CodeExhibitionFrame prep;
        };
        new Thread(() -> {

            SwingUtilities.invokeLater(() -> {
                String laf = UIManager.getSystemLookAndFeelClassName();
                try {
                    UIManager.setLookAndFeel(laf);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                ref.prep = new CodeExhibitionFrame();
            });
        }).start();

        Thread.sleep(2000);


        ref.prep.codeExhibitionFrame2();
    }


    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));
        // ... etc ...
        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "void"));
        provider.addCompletion(new BasicCompletion(provider, "volatile"));
        provider.addCompletion(new BasicCompletion(provider, "while"));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sysout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;

    }


    private static void initFX(JFXPanel jfxPanel) {
        // Create a Box (3D Cube)
        var box = new javafx.scene.shape.Box(100, 100, 100);
        box.setTranslateX(200);
        box.setTranslateY(200);
        box.setTranslateZ(200);
        box.setMaterial(new javafx.scene.paint.PhongMaterial(javafx.scene.paint.Color.RED));

//        box.setRotationAxis(new Point3D(1,1,0));
//        box.setRotate(30);

        RotateTransition rotateTransitionCW = new RotateTransition(Duration.seconds(4), box);
        rotateTransitionCW.setAxis(new Point3D(1, 0.9, 0));
        rotateTransitionCW.setByAngle(360);
        rotateTransitionCW.setCycleCount(Animation.INDEFINITE);
        rotateTransitionCW.play();

        // Create a Group

        javafx.scene.PointLight light = new javafx.scene.PointLight(javafx.scene.paint.Color.WHITE);
        light.setTranslateX(200);
        light.setTranslateY(-100);
        light.setTranslateZ(-200);

        var group = new javafx.scene.Group(box, light);

        // Create a Scene
        Scene scene = new Scene(group, 1000, 1000, true, SceneAntialiasing.BALANCED);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        // Set up the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(scene.getWidth() / -2);
        camera.setTranslateY(scene.getHeight() / -2);
        camera.setTranslateZ(0);
        //scene.setCamera(camera);

        jfxPanel.setScene(scene);
        jfxPanel.setVisible(true);
    }

    private void initFX2(JFXPanel jfxPanel) {
        // Create a Group

        javafx.scene.PointLight light = new javafx.scene.PointLight(javafx.scene.paint.Color.WHITE);
        light.setTranslateX(200);
        light.setTranslateY(-100);
        light.setTranslateZ(-200);

        // Load the .obj model
        try {
            ObjImporter importer = new ObjImporter();
            var x = getClass().getResource("/nas-logo-avatar.obj");
            var model = importer.load(x).getRoot();

            javafx.scene.Group root = new javafx.scene.Group(light, model);


            Scene scene = new Scene(root, 800, 600, true, SceneAntialiasing.BALANCED);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            PerspectiveCamera camera = new PerspectiveCamera(true);

            configureCamera(camera, root);

            //camera.setTranslateY(+100);
            scene.setCamera(camera);

            RotateTransition rotateTransitionCW = new RotateTransition(Duration.seconds(10), root);
            rotateTransitionCW.setAxis(new Point3D(1, 0, 0));
            rotateTransitionCW.setByAngle(360);
            rotateTransitionCW.setCycleCount(Animation.INDEFINITE);
            rotateTransitionCW.play();

            jfxPanel.setScene(scene);
            jfxPanel.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void configureCamera(Camera camera, Node targetNode) {

        // Calculate the translation to center the camera on the target node
        double targetX = targetNode.getBoundsInLocal().getCenterX();
        double targetY = targetNode.getBoundsInLocal().getCenterY();
        double targetZ = targetNode.getBoundsInLocal().getCenterZ();

        double currentX = camera.getLocalToSceneTransform().getTx();
        double currentY = camera.getLocalToSceneTransform().getTy();
        double currentZ = camera.getLocalToSceneTransform().getTz();

        double translateX = targetX - currentX;
        double translateY = targetY - currentY;
        double translateZ = targetZ - currentZ;

        // Create timeline for camera translation animation
        Translate translate = new Translate(translateX, translateY, translateZ);
        camera.getTransforms().add(translate);
    }




}

