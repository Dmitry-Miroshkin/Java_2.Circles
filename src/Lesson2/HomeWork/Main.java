package Lesson2.HomeWork;

public class Main {
    private static String string = "10 3 1 2\n2 3 F 2\n5 6 7 1\n300 3 1 0";

    private static String[][] stringToArr() {
        String[][] array = new String[4][4];
        String[] arrlines = string.split("\n");
        for (int i = 0; i < 4; i++) {
            String[] arrrows = arrlines[i].split(" ");
            System.arraycopy(arrrows, 0, array[i], 0, arrrows.length);
        }
        return array;
    }

    private static int strToIntSum(String[][] arr) {
        int summ = 0;
        int[][] intArr = new int[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                intArr[i][j] = Integer.parseInt(arr[i][j]);
                summ += intArr[i][j];
            }
        }
        return summ / 2;
    }

    public static void main(String[] args) {
        try {
            System.out.println(strToIntSum(stringToArr()));
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println(" Строка не подходит к массиву");
        } catch (NumberFormatException ex2) {
            System.out.println("В строке неверные данные.");
        }
    }
}