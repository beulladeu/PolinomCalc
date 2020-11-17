import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Пути
//C:\Users\beulladeu\IdeaProjects\Polinom\src\test\polinoms.txt

public class Main {

    public static double[] parse_string_to_array(String str){
        if (str.equals("")) str = "0";
        String[] temp = str.split(" ");
        double[] coefficients = new double[temp.length];
        for (int i = 0; i < coefficients.length; i++){
            coefficients[i] = Double.parseDouble(temp[i]);
        }
        return coefficients;
    }

    public static String parse_array_to_string(double[] coefficients){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            str.append(coefficients[i]);
            str.append(" ");
        }
        return str.toString();
    }

    public static double[] manual_writer_coefficients() throws IOException {
        StringBuilder inputLine = new StringBuilder();
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите степень многочлена");
        int pow = 0;
        if (sc.hasNextInt()) {
            pow = sc.nextInt();
        }


        System.out.println("Введите коэффициенты многочлена");
        while (pow != 0){
            while (!(sc.hasNextDouble())){
                sc.next();
                System.out.println("Нужно вводить число");
            }
            double number = sc.nextDouble();
            inputLine.append(number);
            inputLine.append(" ");
            pow--;
        }

        return parse_string_to_array(inputLine.toString());
    }

    public static int manual_writer_user_choice() throws IOException {
        int num = 0;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = stdIn.readLine();
        try {
            num = Integer.parseInt(inputLine.trim()); //trim удаляет пробелы в начале в конце строки
        }
        catch (NumberFormatException ex) {
            System.out.println("Необходимо вводить цифры 1 или 2");
        }
        if(num != 1 && num != 2){
            num = 0;
        }
        return num;
    }

    public static int manual_writer_math_operation() throws IOException {
        int num = 0;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = stdIn.readLine();
        try {
            num = Integer.parseInt(inputLine.trim()); //trim удаляет пробелы в начале в конце строки
        }
        catch (NumberFormatException ex) {
            System.out.println("Необходимо вводить цифры 1, 2, 3 или 4");
        }
        if(num != 1 && num != 2 && num != 3 && num != 4){
            num = 0;
        }
        return num;
    }

    public static void file_writer(String outputFileName, ArrayList<double[]> coefficients) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (int i = 0; i < coefficients.size(); i++) {
                double[] arr = coefficients.get(i);
                writer.write(parse_array_to_string(arr));
                writer.write('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<double[]> file_reader(String inputFileName){
        ArrayList<double[]> coefficients = new ArrayList<>();
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            line = reader.readLine();
            if (line == null) {
                System.out.println("Файл пуст.");
                return null;
                //exit(1); //программа закроется
            }

            double[] arr = parse_string_to_array(line);
            coefficients.add(arr);
            line = reader.readLine();
            arr = parse_string_to_array(line);
            coefficients.add(arr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return coefficients;
    }


    public static void main(String[] args) throws IOException {

        double[] coefficients1 = new double[0];
        double[] coefficients2 = new double[0];
        double[][] result = new double[2][0];


        boolean file_work = false;
        boolean edit_file = false;


        int num = 0;
        while(num == 0){
            System.out.println("1 - Ввести вручную");
            System.out.println("2 - Вывести из файла");
            num = manual_writer_user_choice();
            if(num == 1){
                System.out.println("Первый многочлен");
                coefficients1 = manual_writer_coefficients();
                System.out.println("Второй многочлен");
                coefficients2 = manual_writer_coefficients();
            }
            else if(num == 2){
                file_work = true;
            }
        }

        if(file_work){
            int num1 = 0;
            while(num1 == 0){
                System.out.println("1 - Открыть файл"); //просто открыть
                System.out.println("2 - Открыть файл на редактирование");//открыть на редактирование
                num1 = manual_writer_user_choice();
                if(num1 == 1){
                    System.out.println("Введите путь к файлу");

                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String inputLine = stdIn.readLine();
                    File file = new File(inputLine);
                    if(!file.exists()){
                        System.out.println("Файл не существует. Укажите путь заново.");
                        num1 = 0;
                        continue;
                    }

                    ArrayList<double[]> arr = file_reader(inputLine);
                    if(arr == null || arr.isEmpty()) {
                        num1 = 0;
                        continue;
                    }
                    coefficients1 = arr.get(0);
                    coefficients2 = arr.get(1);

                    break;
                }
                else if(num1 == 2){
                    edit_file = true;
                }
            }
        }

        if(edit_file){
            int num1 = 0;
            while(num1 == 0) {
                num1 = 1;

                System.out.println("Введите путь к файлу");
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String inputLine = stdIn.readLine();
                File file = new File(inputLine);
                if (!file.exists()) {
                    System.out.println("Файл не существует. Укажите путь заново.");
                    num1 = 0;
                    continue;
                }
                System.out.println("Содержимое файла:");
                ArrayList<double[]> data = file_reader(inputLine);
                if(data != null) {
                    System.out.println(Arrays.toString(data.get(0)));
                    System.out.println(Arrays.toString(data.get(1)));
                }

                System.out.println("Введите новые значения коэффициентов, которые затем запишутся в файл");

                System.out.println("Первый многочлен");
                coefficients1 = manual_writer_coefficients();
                System.out.println("Второй многочлен");
                coefficients2 = manual_writer_coefficients();

                ArrayList<double[]> arr = new ArrayList<>();
                arr.add(coefficients1);
                arr.add(coefficients2);
                file_writer(inputLine, arr);
            }
        }


        num = 0;
        while(num == 0){
            System.out.println("Выберите математическую операцию:");
            System.out.println("1 - Сложение");
            System.out.println("2 - Вычитание");
            System.out.println("3 - Умножение");
            System.out.println("4 - Деление");
            num = manual_writer_math_operation();

            Polynomial a = new Polynomial(coefficients1.length, coefficients1);
            Polynomial b = new Polynomial(coefficients2.length, coefficients2);
            System.out.println(a);
            System.out.println(b);

            if (num == 1){
                Polynomial sum = a.sum(b);
                result[0] = sum.getPolynomial();
                System.out.print("Результат ");
                System.out.println(sum);
            }
            else if(num == 2){
                Polynomial diff = a.diff(b);
                result[0] = diff.getPolynomial();
                System.out.print("Результат ");
                System.out.println(diff);
            }
            else if(num == 3){
                Polynomial mult = a.mult(b);
                result[0] = mult.getPolynomial();
                System.out.print("Результат ");
                System.out.println(mult);
            }
            else if(num == 4){
                Polynomial[] divide = a.divide(b);
                result[0] = divide[0].getPolynomial();
                result[1] = divide[1].getPolynomial();
                System.out.print("Результат ");
                System.out.println(Arrays.toString(divide));
            }
        }


        num = 0;
        while(num == 0){
            System.out.println("1 - Сохранить результат в файл");
            System.out.println("2 - Не сохранять");
            num = manual_writer_user_choice();
            if(num == 1){
                System.out.println("Введите путь к файлу");
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String inputLine = stdIn.readLine();

                ArrayList<double[]> arr = new ArrayList<>();
                arr.add(result[0]);
                arr.add(result[1]);
                file_writer(inputLine, arr);
            }
            else if(num == 2){
                break;
            }
        }
/*
        //тесты
        double[] coefficients1 = new double[] { 5, 2, 1, 6 };
        Polynomial a = new Polynomial(coefficients1.length, coefficients1);

        double[] coefficients2 = new double[] { 1, 0, 3 };
        Polynomial b = new Polynomial(coefficients2.length, coefficients2);

        Polynomial test_sum = a.sum(b);

        Polynomial test_mult = a.mult(b);

        Polynomial test_diff = a.diff(b);

        Polynomial[] test_divide = a.divide(b);


        System.out.println(test_sum);
        System.out.println(test_mult);
        System.out.println(test_diff);
        System.out.println(Arrays.toString(test_divide));
*/
    }
}
