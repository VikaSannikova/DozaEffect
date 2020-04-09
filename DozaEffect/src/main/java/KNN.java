import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Math.abs;

public class KNN {
    int type_of_core;
    int number_of_elems;
    Double[] U;
    Double[] W;
    double h;
    int k;
    Core core;

    public KNN(int type_of_core, int number_of_elems, double[] u, double[] w, int k) {
        U = new Double[number_of_elems];
        W = new Double[number_of_elems];
        ArrayList<Pair> ranges = new ArrayList<>();
        for (int i = 0; i < number_of_elems; i++) {
            ranges.add(i, new Pair(u[i], w[i]));
        }
        Collections.sort(ranges, Pair.compare_by_u);
        this.type_of_core = type_of_core;
        this.number_of_elems = number_of_elems;
        for (int i = 0; i < number_of_elems; i++) {
            U[i] = ranges.get(i).getU();
            W[i] = ranges.get(i).getW();
        }
        this.core = new Core(type_of_core);
        this.k = k;
    }

    public double S_1n(double x) {
        double sum = 0;
        h = findH(x, U);
        for (int i = 0; i < number_of_elems; i++) {
            sum += 1 / h * core.result((U[i] - x) / h);
        }
        return (1.0 / number_of_elems) * sum;
    }

    public double S_2n(double x) {
        double sum = 0;
        h = findH(x, U);
        for (int i = 0; i < number_of_elems; i++) {
            sum += 1 / h * W[i] * core.result((U[i] - x) / h);
        }
        return (1.0 / number_of_elems) * sum;
    }

    public double F_n(double x){
        if(Double.isNaN(S_2n(x)/S_1n(x)))
            return 0.0;
        return S_2n(x)/S_1n(x);
    }

    public double findH(double x, Double[] u) {
        ArrayList<Double> uu = new ArrayList<>(Arrays.asList(u));
        uu.add(x);
        Collections.sort(uu);
        int index = uu.indexOf(x);
        int pos = 0;
        if (index > 0 && index <number_of_elems){
            pos = (abs(uu.get(index - 1) - x) < abs(uu.get(index + 1) - x) ? index - 1 : index);
        } else if (index == 0){
            pos = 0;
        } else if (index == number_of_elems){
            pos = number_of_elems - 1;
        }
        if (pos >= k / 2 && pos < number_of_elems - k / 2) {
            return (U[pos + k / 2] - U[pos - k / 2]);
        } else if (pos < k / 2) {
            return (U[pos + k / 2] - U[0]);
        } else if (pos >= number_of_elems - k / 2) {
            return (U[number_of_elems - 1] - U[pos - k / 2]);
        }
        System.out.println("ERROR");
        return 0;
    }
}

class Pair {
    private Double u;
    private Double w;

    public Pair(Double u, Double w) {
        this.u = u;
        this.w = w;
    }

    public Double getU() {
        return u;
    }

    public void setU(Double u) {
        this.u = u;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
    }

    public static final Comparator<Pair> compare_by_u = new Comparator<Pair>() {
        @Override
        public int compare(Pair lhs, Pair rhs) {
            return lhs.getU().compareTo(rhs.getU());
        }
    };

    @Override
    public String toString() {
        return "(" + this.getU() + ", " + this.getW() + ")";
    }
}
