package graphical.basics.animations.mandelbrotset;

public class ComplexNumber {

    double real;
    double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void mutiply(ComplexNumber n2) {
        double realcopy = real;
        double imaginarycopy = imaginary;
        this.real = (this.real * n2.real) - (this.imaginary * n2.imaginary);
        this.imaginary = (realcopy * n2.imaginary) + (n2.real * imaginarycopy);
    }

    public void add(ComplexNumber n2) {
        this.real += n2.real;
        this.imaginary += n2.imaginary;
    }

    public static ComplexNumber mutiply(ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber((n1.real * n2.real) - (n1.imaginary * n2.imaginary), (n1.real * n2.imaginary) + (n2.real * n1.imaginary));
    }

    public static ComplexNumber add(ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber(n1.real + n2.real, n1.imaginary + n2.imaginary);
    }


    public double magnitude() {
        return Math.sqrt((real * real) + (imaginary * imaginary));
    }
}
