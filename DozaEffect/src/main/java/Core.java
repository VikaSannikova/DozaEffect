import static java.lang.Math.*;

public class Core {
    int type;
    public Core(int type) {
        this.type = type;
    }

    public double result(double x){
        switch (this.type){
            case(0):
                return K_0(x);
            case(1):
                return K_1(x);
            case(2):
                return K_2(x);
            case(3):
                return K_3(x);
            case(4):
                return K_4(x);
            case(5):
                return K_5(x);
            case(6):
                return K_6(x);
            default:
                System.out.printf("ERROR");
                return 0;
        }
    }

    public double K_0(double x){ // |K|^2=3/5; v^2=1/5
        int I = 0;
        if(abs(x)<=1){
            I = 1;
        }
        return 3.0/4.0*(1-pow(x,2))*I;
    }

    public double K_1(double x){ // |K|^2=5/7; v^2=1/7
        int I = 0;
        if(abs(x)<=1){
            I = 1;
        }
        return 15.0/16.0*pow((1-pow(x,2)),2)*I;
    }

    public double K_2(double x){ // -1<=x<=1; |K|^2=1/2; v^2=1/3
        return 0.5;
    }

    public double K_3(double x){ // -1<=x<=1; |K|^2=2/3; v^2=1/6
        return 1-abs(x);
    }

    public double K_4(double x){ // -1<=x<=1; |K|^2=pi^2/16; v^2=(pi^2-8)/pi^2
        return PI/4*cos(PI*x/2);
    }

    public double K_5(double x){ // -oo<=x<=oo; |K|^2=1/4; v^2=2
        return 0.5*exp(-abs(x));
    }

    public double K_6(double x){ // -oo<=x<=oo; |K|^2=1/(2sqrt(x)); v^2=1
        return 1.0/(sqrt(2*PI))*exp(-pow(x,2)/2);
    }
}
