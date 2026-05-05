package graphical.basics.presentation;

import java.util.function.Consumer;

public class DefaultAnimationStarter {

    private DefaultAnimationStarter() {
    }

    public static void startEnv(Consumer<PresentationConfig> config) {
        new Thread(() -> {
            new RTAnimation() {

                @Override
                public void setup(PresentationConfig presentationConfig) {
                    config.accept(presentationConfig);
                }

                @Override
                public void buildAnimation() {
                    // do nothing there
                }
            };
        }).start();
    }

    public static void startEnv() {
        startEnv(x -> {
        });
    }
}
