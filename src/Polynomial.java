import java.util.Arrays;

import static java.lang.System.exit;

public class Polynomial {
    private int pow; //степень полинома
    private double[] polynomial; //коэффициенты полинома

    //конструктор объекта Polinomial
    //с инициализацией степени и коэффициентов полинома
    public Polynomial(int size, double[] array){
        this.pow = size;
        this.polynomial = new double[this.pow];

        //для удобства реализации операций над полиномами
        //массив коэффициенов записывается в обратном порядке
        this.polynomial = this.reverseArray(array);
    }

    //конструктор объекта Polinomial
    //c инициализацией полинома заданной степени, заполненного нулями
    private Polynomial(int pow) {
        this.pow = pow;
        this.polynomial = new double[this.pow];
    }

    public Polynomial sum(Polynomial a){//метод сложения двух полиномов
        double[] res = new double[Math.max(this.pow, a.getPow())];
        int min_size = Math.min(this.pow, a.getPow());
        for (int i = 0; i < min_size; i++) {
            //коэффициенты полиномов складываются
            res[i] = this.polynomial[i] + a.getPolynomial()[i];
        }
        //если полиномы разных степеней, то старшие коэффициенты большего полинома
        // копируются в начало результирующего полинома
        if (this.pow > a.pow) System.arraycopy(this.polynomial, min_size, res, min_size, res.length-min_size);
        if (a.pow > this.pow) System.arraycopy(a.polynomial, min_size, res, min_size, res.length-min_size);
        res = this.reverseArray(res);
        return new Polynomial(res.length, res);
    }

    public Polynomial diff(Polynomial a) {//метод разности полиномов
        Polynomial p = new Polynomial(a.getPolynomial().length, this.reverseArray(a.getPolynomial()));
        //коэффициенты вычитаемого полинома меняют знак на противоположный
        //затем уменьшаемый полином складывается с вычитаемым полиномом
        for (int i = 0; i < p.getPolynomial().length; i++) {
            p.getPolynomial()[i] = p.getPolynomial()[i] * (-1);
        }
        return this.sum(p);
    }

    public Polynomial mult(Polynomial a){//метод умножения полиномов
        double[] res = new double[this.pow + a.getPow()];
        //сумма умножения каждого элемента одного полинома с каждым элементом второго полинома
        for (int i = this.pow - 1; i >= 0; i--) {
            for (int j = a.getPow() - 1; j >= 0; j--) {
                res[i + j] += this.polynomial[i] * a.getPolynomial()[j];
            }
        }
        res = this.reverseArray(res);
        return new Polynomial(res.length, res);
    }

    private boolean is_zero(Polynomial a){//метод проверки полинома на заполненность нулями
        for (int i = 0; i < a.polynomial.length; i++){
            if(a.polynomial[i] != 0) return false;
        }
        return true;
    }


    public Polynomial[] divide(Polynomial a) {//метод деления полиномов
        if(this.is_zero(a)){//проверка деления на ноль
            System.out.println("Zero division");
            exit(1);
        }
        Polynomial temp = this;
        int m = a.getPow() - 1;
        Polynomial res = new Polynomial(temp.getPow() - a.getPow() + 1);
        //алгоритм деления полиномов столбиком
        for (int i = temp.getPow() - 1; i >= m; i--) {
            if(a.getPolynomial()[m] == 0) {
                continue;
            }
            res.getPolynomial()[i - m] = temp.getPolynomial()[i] / a.getPolynomial()[m];
            for (int j = m; j >= 0; j--) {
                temp.getPolynomial()[i - m + j] -= a.getPolynomial()[j] * res.getPolynomial()[i - m];
            }
        }
        if(res.polynomial[res.pow - 1] == 0){
            return new Polynomial[]{temp, res};
        }
        return new Polynomial[]{res, temp};
    }

    public double[] reverseArray(double[] arr) {//метод записи массива в обратном порядке
        int j = 0;
        double[] res = new double[arr.length];
        for (int i = arr.length - 1; i >= 0; i--, j++) {
            res[j] = arr[i];
        }
        return res;
    }

    //метод, возвращающий коэффициенты полинома
    public double[] getPolynomial() {
        return polynomial;
    }

    public int getPow() {//метод, возвращающий степень полинома
        return pow;
    }

    //метод, возвращающий строку красивой записи полинома
    @Override
    public String toString() {
        if(this.is_zero(this)){
            return "0";
        }
        StringBuilder s = new StringBuilder("");
        for (int i = this.pow - 1; i >= 0; i--) {
            if(this.polynomial[i] == 0) continue;
            if(this.polynomial[i] < 0 && i != this.pow - 1){
                s = new StringBuilder(s.substring(0, s.length() - 1));
            }
            s.append(this.polynomial[i]);
            if (i == 1) {
                s.append("x");
            }
            else if (i != 0) {
                s.append("x^");
                s.append(i);
            }

            s.append(" ");
        }
        String str = s.toString().trim();
        str = str.replaceAll(" ", " + ");
        str = str.replaceAll("-", " -");
        return str.trim();
    }

    //метод, проверяющий полиномы на равенство
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polynomial polynomial = (Polynomial) o;

        if (this.pow != polynomial.getPow()) return false;
        return Arrays.equals(this.polynomial, polynomial.getPolynomial());
    }
}
