import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Singleton {
    private void Singleton() {
    }

    private volatile static Singleton instance = null;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) instance = new Singleton();
            }
        }
        return instance;
    }
}

class OutClass {
    private void OutClass() {
    }

    private static class Inner {
        private static final OutClass INSTANCE = new OutClass();
    }

    public static OutClass getInst() {
        return Inner.INSTANCE;
    }
}

class m1 {
    public static void main(String[] args) {
        new Thread("a") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    //System.out.println("a="+Singleton.getInstance());
                    System.out.println("a=" + OutClass.getInst());
                }
            }
        }.start();
        new Thread("b") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    //System.out.println("b="+Singleton.getInstance());
                    System.out.println("b=" + OutClass.getInst());
                }
            }
        }.start();
        new Thread("b") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    //System.out.println("c="+Singleton.getInstance());
                    System.out.println("c=" + OutClass.getInst());
                }
            }
        }.start();


    }
}

class m2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<OutClass> c = new Callable<OutClass>() {
            @Override
            public OutClass call() {
                return OutClass.getInst();
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<OutClass> f1 = es.submit(c);
        Future<OutClass> f2 = es.submit(c);
        OutClass outClass = f1.get();
        OutClass outClass1 = f2.get();
        System.out.println(outClass == outClass1);
        es.shutdown();

    }
}

class Mt {
    public String getXX(int v, String v2) {
        return v + "____________" + v2;
    }

    public void xx() {
        System.out.println("111111111111111");
    }
}

class Dog {
    public String name;
    public int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class c3 {


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("b", 4));
        dogs.add(new Dog("f", 8));
        dogs.add(new Dog("a", 6));
        dogs.add(new Dog("b", 7));
        dogs.add(new Dog("e", 31));
        dogs.add(new Dog("f", 10));
        Stream<Dog> sorted = dogs.stream().sorted(Comparator.comparing(Dog::getAge).reversed().thenComparing(Dog::getName));//.forEach( System.out::println );
        Optional<Dog> any = sorted.findAny();
        Dog e = any.get();
        dogs.forEach((Dog d) -> System.out.println(d.getName()));
        char a = 9006;
    }
}

class FilterKeyword {
    // 替换符
    private static final String REPLACEMENT = "***";
    // 根节点
    private TrieNode rootNode = new TrieNode();

    // 前缀树
    private class TrieNode {
        // 关键词结束标识
        private boolean isKeywordEnd = false;
        // 子节点(key是下级字符,value是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }

    //添加节点
    public void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            // 指向子节点,进入下一轮循环
            tempNode = subNode;

            // 设置结束标识
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     *
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        // 指针1
        TrieNode tempNode = rootNode;
        // 指针2
        int begin = 0;
        // 指针3
        int position = 0;
        // 结果
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)) {
                // 若指针1处于根节点,将此符号计入结果,让指针2向下走一步
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头或中间,指针3都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现敏感词,将begin~position字符串替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                begin = ++position;
                // 重新指向根节点
                tempNode = rootNode;
            } else {
                // 检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }

    // 判断是否为符号
    private boolean isSymbol(Character c) {
        // 0x2E80~0x9FFF 是东亚文字范围
        return false;
    }


    public static void main(String[] args) {
        FilterKeyword fk = new FilterKeyword();
        Arrays.asList("王八", "王后", "嫖娼", "狐狸精").forEach(f -> {
            fk.addKeyword(f);
        });
        String t = "坏人就是王八蛋，就喜欢去夜店嫖娼妇女！";
        String rs = fk.filter(t);
        System.out.println(rs);
        new Thread(() -> System.out.println("It's a lambda function!")).start();
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        double allCost = cost.stream().map(x -> x + x * 0.05).reduce((sum, x) -> sum + x).get();
        Stream<Double> ds = cost.stream().map(x -> x + x * 0.05);
        Double aDouble = cost.stream().map(x -> x + 34).reduce((s, a) -> s + a).get();
        System.out.println(aDouble);
        Runnable runnable = ArrayList::new;
        System.out.println(2 << 2);


    }
}

class Ad {
    public static void main(String[] args) {
        Ad ad = new Ad();
        Inc inc = ad.new Inc();
        Des des = ad.new Des();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(inc);
            t.start();
            new Thread(des).start();
        }

    }

    volatile static int j = 0;

    private synchronized void des() {
        j--;
        System.out.println("____我见_______" + Thread.currentThread().getName() + "----------" + j);
    }

    private synchronized void inc() {
        j++;
        System.out.println("_____我加______" + Thread.currentThread().getName() + "----------" + j);
    }

    class Inc implements Runnable {
        public void run() {
            for (int i = 0; i < 100; i++) {
                inc();
            }
        }
    }

    class Des implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                des();
            }
        }
    }
}

class Xx {


    public static void main(String[] args) {
        new Xx().mt();
    }

    int j = 0;

    private void mt() {
        for (int out = 0; out < 2; out++) {
            new Thread(() -> {
                for (int x = 0; x < 100; x++) {
                    des();
                }
            }).start();
            new Thread(() -> {
                for (int x = 0; x < 100; x++) {
                    inc();
                }
            }).start();
        }
    }

    private synchronized void des() {
        j--;
        System.out.println("____我见_______" + Thread.currentThread().getName() + "----------" + j);
    }

    private synchronized void inc() {
        j++;
        System.out.println("_____我加______" + Thread.currentThread().getName() + "----------" + j);
    }
}

class ThreadTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        new ThreadTest().init();
    }

    public void init() {
        final Business business = new Business();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 50; i++) {
                    business.SubThread(i);
                }
            }
        }
        ).start();
        for (int i = 0; i < 50; i++) {
            business.MainThread(i);
        }
    }

    private class Business {
        boolean bShouldSub = true;//这里相当于定义了控制该谁执行的一个信号灯

        public synchronized void MainThread(int i) {
            if (bShouldSub) try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < 5; j++) {
                System.out.println(Thread.currentThread().getName() + ":i=" + i + ",j=" + j);
            }
            bShouldSub = true;
            this.notify();
        }

        public synchronized void SubThread(int i) {
            if (!bShouldSub) try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println(Thread.currentThread().getName() + ":i=" + i + ",j=" + j);
            }
            bShouldSub = false;
            this.notify();
        }
    }
}

class TestParent {
    public TestParent() {
        System.out.println("Parent Constructor");
    }

    {
        System.out.println("Parent 普通代码块");
    }

    static {
        System.out.println("Parent 静态代码块");
    }

    static void parentStaticMethod() {
        System.out.println("Parent 静态方法");
    }

    void parentMethod() {
        System.out.println("Parent 普通方法");
    }

}

class Children extends TestParent {
    public Children() {
        System.out.println("Children Constructor");
    }

    {
        System.out.println("Children 普通代码块");
    }

    static {
        System.out.println("Children 静态代码块");
    }

    static void childrenStaticMethod() {
        System.out.println("Children 静态方法");
    }

    void childredMethod() {
        System.out.println("Childred 普通方法");
    }
}

class M {
    public static void main(String[] args) {
        new Children();
    }
}

class QuickSort {
    /**
     * 快速排序 * @param strDate * @param left * @param right
     */
    public void quickSort(String[] strDate, int left, int right) {
        String middle, tempDate;
        int i, j;
        i = left;
        j = right;
        middle = strDate[(i + j) / 2];
        System.out.println("middle" + middle);
        do {
            int m = Integer.valueOf(middle);
            //{"11", "66", "22", "3", "55", "32", "0", "32"}
            int num;
            while ((num = Integer.valueOf(strDate[i])) > m && i < right) {
                i++; //找出左边比中间值大的数
            }

            int num2;
            while ((num2 = Integer.valueOf(strDate[j])) < m && j > left) {
                j--; //找出右边比中间值小的数
            }
            if (i <= j) { //将左边大的数和右边小的数进行替换
                tempDate = strDate[i];
                strDate[i] = strDate[j];
                strDate[j] = tempDate;
                System.out.println(" strDate[i]=" + strDate[i] + "---strDate[j]+" + strDate[j]);
                i++;
                j--;
            }
        } while (i <= j); //当两者交错时停止
        if (i < right) {
            quickSort(strDate, i, right);//从
        }
        if (j > left) {
            quickSort(strDate, left, j);
        }
    }


    public static void main(String[] args) {
        String[] strVoid = new String[]{"11", "66", "22", "3", "55", "32", "0", "32"};
        QuickSort sort = new QuickSort();
        sort.quickSort(strVoid, 0, strVoid.length - 1);

        for (int i = 0; i < strVoid.length; i++) {
            System.out.println(strVoid[i] + " ");
        }

    }

}

class QickSort {
    public static void main(String[] args) {
        int[] data = {4, 6, 7, 2, 1, 9, 0, 12};
        quickSort(data, 0, data.length - 1);
        for (int i : data) {
            System.out.println(i);
        }
    }

    public static void quickSort(int[] data, int start, int end) {
        if (start < end) {
            int index = getIndex(data, start, end);
            quickSort(data, start, index - 1);
            quickSort(data, index + 1, end);
        }
    }

    public static int getIndex(int[] data, int start, int end) {
        int i = start;
        int j = end;
        int x = data[i];
        while (i < j) {
            while (i < j && data[j] >= x) {
                j--;
            }
            if (i < j) {
                data[i] = data[j];
                i++;
            }
            while (i < j && data[i] < x) {
                i++;
            }
            if (i < j) {
                data[j] = data[i];
                j--;
            }
        }
        data[i] = x;
        return j;
    }
}

@SuppressWarnings("Duplicates")
class Ttttt {
    public void producter(BlockingQueue bq) {
        new Thread(() -> {
            for (int i = 0; i < 2110; i++) {
                try {
                    bq.put(i + "");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void consume(BlockingQueue bq) {
        new Thread(() -> {
            try {
                for (int i = 0; i < 11000; i++) {
                    try {
                        System.out.println("---" + bq.take().toString());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        BlockingQueue bq = new ArrayBlockingQueue(20);
        Ttttt t = new Ttttt();
        t.producter(bq);
        t.consume(bq);

    }
}

@SuppressWarnings("Duplicates")
class QuickTest {
    public static void main(String[] args) {
        int[] arr = {5, 3, 21, 6, 7, 8, 34, 11};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int index = getIndex(arr, start, end);
            quickSort(arr, start, index - 1);
            quickSort(arr, index + 1, end);
            System.out.println("int start,int end" + start + "+++++++++++++" + end);
        }
    }

    static int getIndex(int[] arr, int start, int end) {
        int i = start;
        int j = end;
        int x = arr[i];
        while (i < j) {
            while (i < j && arr[j] >= x) {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }
            while (i < j && arr[i] < x) {
                i++;
            }
            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }
        arr[i] = x;
        return i;
    }
}

class CddlassLoad {
    public static void main(String[] args) {
        xxxxxxxxxxxx xxxxxxxxxxxx = new xxxxxxxxxxxx();

        System.out.println(String.class.getClass().getClassLoader());
        System.out.println(xxxxxxxxxxxx.getClass().getClassLoader());
        System.out.println(xxxxxxxxxxxx.getClass().getClassLoader().getParent());


    }
}

class xxxxxxxxxxxx {
    public static void main(String[] args) throws Exception {
        xxxxxxxxxxxx.split("A B啊  3 个",9);
        System.out.println("s 是");

    }
    public static void split(String source, int num) throws Exception {
        int k = 0;
        String temp = "";
        for (int i = 0; i < source.length(); i++) {
            byte[] b = (source.charAt(i)+"" ).getBytes();
            k = k + b.length;
            System.out.println(b.length + "--"+source.charAt(i) );
            if (k > num) {
                break;
            }
            temp = temp + source.charAt(i);
        }
        System.out.println(temp);
    }
}




