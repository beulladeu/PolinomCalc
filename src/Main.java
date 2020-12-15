import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Пути
//C:\Users\beulladeu\IdeaProjects\Polinom\src\subj\result.txt

public class Main {

    //фунцкция, разбирающая строку коэффициентов в массив
    public static double[] parse_string_to_array(String str){
        if (str.equals("")) str = "0";
        String[] temp = str.split(" ");//делит строку на элементы с разделителем "пробел"
        double[] coefficients = new double[temp.length];
        for (int i = 0; i < coefficients.length; i++){
            coefficients[i] = Double.parseDouble(temp[i]);
        }
        return coefficients;
    }

    //фунцкция, формирующая строку коэффициентов из массива
    public static String parse_array_to_string(double[] coefficients){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            str.append(coefficients[i]);
            str.append(" ");
        }
        return str.toString();
    }

    //функция, считывания введенных коэффициентов и степени полинома с консоли
    public static double[] manual_writer_coefficients() throws IOException {
        StringBuilder inputLine = new StringBuilder();
        //для получения пользовательского ввода
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите степень многочлена");
        int pow = 0;
        while(true){
            if (sc.hasNextInt()) {//ожидается ввод, пока не будет введено целое число
                pow = sc.nextInt();
                if(pow <= 10 && pow >= 0) break;
                else{
                    System.out.println("Cтепень многочлена должна быть положительной и не превышать 10");
                }
            }
        }
        pow++;

        System.out.println("Введите коэффициенты многочлена");
        while (pow != 0){
            //ожидается ввод, пока не будет введено целое число, либо число типа double
            while (!(sc.hasNextDouble())){
                sc.next();
                System.out.println("Нужно вводить число");
            }
            double number = sc.nextDouble();
            inputLine.append(number);//добавление числа в строку
            inputLine.append(" ");
            pow--;
        }
        //возвращается массив, разобранный из строки
        return parse_string_to_array(inputLine.toString());
    }

    //функция, обрабатывающая ввод пользователя
    public static int manual_writer_user_choice() throws IOException {
        int num = 0;
        //для получения пользовательского ввода
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = stdIn.readLine();
        try {
            num = Integer.parseInt(inputLine.trim()); //trim удаляет пробелы в начале и в конце строки
        }
        catch (NumberFormatException ex) {
            System.out.println("Необходимо вводить цифры 1 или 2");
        }
        if(num != 1 && num != 2){
            num = 0;
        }
        return num;
    }

    //функция, обрабатывающая выбор знака операции
    public static int manual_writer_math_operation() throws IOException {
        int num = 0;
        //для получения пользовательского ввода
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

    //функция записи массива коэффициентов в файл
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

    //функция чтения коэффициентов из файла в массив
    public static ArrayList<double[]> file_reader(String inputFileName){
        ArrayList<double[]> coefficients = new ArrayList<>();
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            line = reader.readLine();
            if (line == null) {//проверка файла на пустоту
                System.out.println("Файл пуст.");
                return null;
            }

            double[] arr = parse_string_to_array(line);
            coefficients.add(arr);
            line = reader.readLine();

            //разбор строки и запись коэффициентов в массив
            arr = parse_string_to_array(line);
            coefficients.add(arr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return coefficients;
    }


    public static void main(String[] args) throws IOException {

        double[] coefficients1 = new double[0];//коэффициенты первого полинома
        double[] coefficients2 = new double[0];//коэффициенты второго полинома
        double[][] result = new double[2][0];//коэффициенты результирующего полинома

        boolean file_work = false;// включает режим работы с файлами
        boolean edit_file = false;// включает режим работы редактирования файлов


        //обрабатывает режим ввода вручную или из файла
        int num = 0;
        while(num == 0){
            System.out.println("1 - Ввести вручную");
            System.out.println("2 - Вывести из файла");
            num = manual_writer_user_choice();
            if(num == 1){//ручной ввод
                System.out.println("Первый многочлен");
                coefficients1 = manual_writer_coefficients();
                System.out.println("Второй многочлен");
                coefficients2 = manual_writer_coefficients();
            }
            else if(num == 2){
                // включает режим работы с файлами
                file_work = true;
            }
        }

        //если включен режим работы с файлами
        if(file_work){
            int num1 = 0;
            while(num1 == 0){
                System.out.println("1 - Открыть файл");
                System.out.println("2 - Открыть файл на редактирование");
                num1 = manual_writer_user_choice();
                if(num1 == 1){//просто открыть и считать оттуда данные
                    System.out.println("Введите путь к файлу");
                    //для пользовательского ввода с консоли
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String inputLine = stdIn.readLine();
                    File file = new File(inputLine);
                    if(!file.exists()){//проверка существует ли файл
                        System.out.println("Файл не существует. Укажите путь заново.");
                        num1 = 0;
                        continue;
                    }

                    ArrayList<double[]> arr = file_reader(inputLine);
                    //проверка - если файл пуст, то пользователь возвращается в режим выбора
                    //открыть файл или открыть файл на редактирование
                    if(arr == null || arr.isEmpty()) {
                        num1 = 0;
                        continue;
                    }
                    coefficients1 = arr.get(0);//запись первого многочлема
                    coefficients2 = arr.get(1);//запись второго многочлена
                    break;
                }
                else if(num1 == 2){//открыть на редактирование и считать новые данные
                    // включает режим работы редактирования файла
                    edit_file = true;
                }
            }
        }

        //если включен режим работы редактирования файлов
        if(edit_file){
            int num1 = 0;
            while(num1 == 0) {
                num1 = 1;
                System.out.println("Введите путь к файлу");
                //для пользовательского ввода с консоли
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String inputLine = stdIn.readLine();
                File file = new File(inputLine);
                //проверка существования файла
                //то пользователь возвращается в режим ввода пути к файлу
                if (!file.exists()) {
                    System.out.println("Файл не существует. Укажите путь заново.");
                    num1 = 0;
                    continue;
                }
                //вывод на экран содержимого файла
                System.out.println("Содержимое файла:");
                ArrayList<double[]> data = file_reader(inputLine);
                if(data != null) {//проверка не был ли файл пустым
                    System.out.println(Arrays.toString(data.get(0)));
                    System.out.println(Arrays.toString(data.get(1)));
                }else {
                    System.out.println("Файл пуст");
                }

                System.out.println("Введите новые значения коэффициентов, которые затем запишутся в файл");
                System.out.println("Первый многочлен");
                coefficients1 = manual_writer_coefficients();
                System.out.println("Второй многочлен");
                coefficients2 = manual_writer_coefficients();

                ArrayList<double[]> arr = new ArrayList<>();
                arr.add(coefficients1);
                arr.add(coefficients2);
                file_writer(inputLine, arr);//запись новых значений полиномов в файл
            }
        }


        //обработка ввода математической операции
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

            if (num == 1){//сложение
                Polynomial sum = a.sum(b);
                System.out.print("Результат ");
                System.out.println(sum);
                result[0] = sum.reverseArray(sum.getPolynomial());
            }
            else if(num == 2){//вычитание
                Polynomial diff = a.diff(b);
                System.out.print("Результат ");
                System.out.println(diff);
                result[0] = diff.reverseArray(diff.getPolynomial());
            }
            else if(num == 3){//умножение
                Polynomial mult = a.mult(b);
                System.out.print("Результат ");
                System.out.println(mult);
                result[0] = mult.reverseArray(mult.getPolynomial());
            }
            else if(num == 4){//деление
                Polynomial[] divide = a.divide(b);
                System.out.print("Результат ");
                System.out.println(Arrays.toString(divide));
                result[0] = divide[0].reverseArray(divide[0].getPolynomial());
                result[1] = divide[1].reverseArray(divide[1].getPolynomial());
            }
        }


        //обработка выбора пользователя сохранять результат в файл или нет
        num = 0;
        while(num == 0){
            System.out.println("1 - Сохранить результат в файл");
            System.out.println("2 - Не сохранять");
            num = manual_writer_user_choice();//обработка ввода пользователя
            if(num == 1){
                System.out.println("Введите путь к файлу");
                //для пользовательского ввода пути к файлу с консоли
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String inputLine = stdIn.readLine();
                ArrayList<double[]> arr = new ArrayList<>();
                arr.add(result[0]);
                arr.add(result[1]);
                file_writer(inputLine, arr);//запись коэффициентов в файл
            }
            else if(num == 2){
                break;
            }
        }
    }
}
