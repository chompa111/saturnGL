package graphical.basics.miscelaneous;

import graphical.basics.gobject.*;

public class ProperPresentation extends Presentation {
    @Override
    public void buildAnimation() {

        super.buildAnimation();
        addSlide(new VideoSlide("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv1.mov"));
        addSlide(new VideoSlide("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv2.mov"));
        addSlide(new VideoSlide("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv3.mov"));
        addSlide(new VideoSlide("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv4.mov"));
        addSlide(new VideoSlide("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv5.mov"));
        addSlide(new VideoSlide("C:\\Users\\PICHAU\\Desktop\\repos\\Saturn\\video\\mv6.mov"));
//        addSlide(new RTAnimationSlide(() -> new RTEx(this)));
//        addSlide(new RTAnimationSlide(() -> new Pong(this)));

//        addSlide(new RTAnimationSlide(() -> new RTEx(this)));


    }

    public static void main(String[] args) {
        new ProperPresentation().buildAnimation();
    }

}
