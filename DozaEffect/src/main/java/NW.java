public class NW {
    int type_of_core;
    int number_of_elems;
    double[]U = new double[number_of_elems];
    double[]W = new double[number_of_elems];
    double h;
    Core core;

    public NW(int type_of_core, int number_of_elems, double[] u, double[] w, double h) {
        this.type_of_core = type_of_core;
        this.number_of_elems = number_of_elems;
        U = u;
        W = w;
        this.h = h;
        this.core = new Core(type_of_core);
    }

    public double S_1n(double x){
        double sum = 0;
        for(int i = 0; i < number_of_elems; i++){
            sum+=1/h*core.result((U[i]-x)/h);
        }
        return (1.0/number_of_elems)*sum;
    }

    public double S_2n(double x){
        double sum = 0;
        for(int i = 0; i < number_of_elems; i++){
            sum +=1/h*W[i]*core.result((U[i]-x)/h);
        }
        return (1.0/number_of_elems)*sum;
    }

    public double V_1n(double x){
        double sum = 0;
        for(int j = 0; j < number_of_elems; j++){
            double h0 =  h;
            double s1 = 0;
            double s2 = 0;
            for(int i = 0; i < number_of_elems; i++){
                s1+=1.0/h0*core.result((U[i]-x)/h0);
                s2+=1.0/h0*core.result((U[i]-U[j])/h0);
            }
            if(!Double.isNaN(s1/s2))
                sum+=1.0/h*core.result((U[j]-x)/h)*s1/s2;
        }
        return (1.0/number_of_elems)*sum;
    }

    public double V_2n(double x){
        double sum = 0;
        for(int j = 0; j < number_of_elems; j++){
            double h0 =  h;
            double s1 = 0;
            double s2 = 0;
            for(int i = 0; i < number_of_elems; i++){
                s1+=1.0/h0*W[j]*core.result((U[i]-x)/h0);
                s2+=1.0/h0*W[j]*core.result((U[i]-U[j])/h0);
            }
            if(!Double.isNaN(s1/s2))
                sum+=1.0/h*W[j]*core.result((U[j]-x)/h)*s1/s2;
        }
        return (1.0/number_of_elems)*sum;
    }

    public double F_n(double x){
        if(Double.isNaN(S_2n(x)/S_1n(x)))
            return 0.0;
        return S_2n(x)/S_1n(x);
    }

    public double G_n(double x){
        if(Double.isNaN(V_2n(x)/V_1n(x)))
            return 0.0;
        return V_2n(x)/V_1n(x);
    }
}