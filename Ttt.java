import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class Ttt extends Date {
    public static void main(String[] args) {
        int[] arr = {6, 7, 3, 1, 5, 7, 12, 4, 4, 33, 3234, 54, 3, 223, 44, 3, 5, 65, 23, 122, 32, 44, 556, 334, 65, 7, 886, 564, 21, 12, 432, 23432, 23432, 4324, 32423423, 234234, 234234, 324324, 234234, -2, -44};
        QuickSort_0.start(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}

class QuickSort_0 {
    public static void start(int[] arr, int start, int end) {
        if (start < end) {
            int index = getIndex(arr, start, end);
            start(arr, start, index - 1);
            start(arr, index + 1, end);
        }
    }

    static int getIndex(int[] arr, int start, int end) {
        int middle = arr[start];
        while (start < end) {
            while (start < end && arr[end] >= middle) {
                end--;
            }
            if (start < end) {
                arr[start] = arr[end];
                start++;
            }
            while (start < end && arr[start] < middle) {
                start++;
            }
            if (start < end) {
                arr[end] = arr[start];
                end--;
            }
            arr[start] = middle;
        }
        return end;
    }
}
//快速排序
class Sort {
    public static void sort() {
        int[] arr = {6, 7, 3, 1, 5, 7, 12, 4, 4, 33, 3234, 54, 3, 223, 44, 3, 5, 65, 23, 122, 32, 44, 556, 334, 65, 7, 886, 564, 21, 12, 432, 23432, 23432, 4324, 32423423, 234234, 234234, 324324, 234234, -2, -44};
        int sort[] = arr;
        int temp;
        for (int i = 0; i < sort.length - 1; i++) {
            for (int j = 0; j < sort.length - i - 1; j++) {
                if (sort[j] > sort[j + 1]) {
                    temp = sort[j];
                    sort[j] = sort[j + 1];
                    sort[j + 1] = temp;
                }
            }
        }
//        System.out.println("排列后的顺序为：");
//        for (int i = 0; i < sort.length; i++) {
//            System.out.print(sort[i] + "   ");
//        }
    }

    public static void main(String[] args) {
        sort();
    }
}

class VectorTest{
    public static void main(String[] args) {
        Vector v = new Vector(3, 2);
        System.out.println("Initial size: " + v.size());
        System.out.println("Initial capacity: " +
                v.capacity());
        v.addElement(1);
        v.addElement(2);
        System.out.println("Capacity after four additions: " + v.capacity());

    }
}