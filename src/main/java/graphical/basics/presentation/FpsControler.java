package graphical.basics.presentation;

public class FpsControler {

    private double lastMesure = 0;

    public void controlFps(int fpsGoal) {
        double idealSleepTime = 1000.0 / fpsGoal;
        var currentTime = System.currentTimeMillis();
        var frameTime = currentTime - lastMesure;
        lastMesure = currentTime;
        double sleepCorrection = idealSleepTime - frameTime;

        if (sleepCorrection > 1) {
            try {
                Thread.sleep((int) sleepCorrection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
