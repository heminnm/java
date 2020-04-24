public class DecoratorTest {
    public static void main(String[] args) {
        Man man = new NormalMan("张三");
        Man man1 = new CarDecoratorImpl(man);
        Man man2 = new HouseDecoratorImpl(man1);
        Man man3 = new DepositDecoratorImpl(man2);
        System.out.println("层层装饰:");
        man3.getManDesc();
        System.out.println();

        System.out.println("重复装饰（有两个'有存款'）:");
        Man man4 = new DepositDecoratorImpl(man3);
        man4.getManDesc();
        System.out.println();

        System.out.println("任意修饰:");
        Man man5 = new QualityDecoratorImpl(man1);
        man5.getManDesc();
        System.out.println();

        System.out.println("直接得到修饰结果:");
        Man man6 = new HouseDecoratorImpl(new DepositDecoratorImpl(new NormalMan("李四")));
        man6.getManDesc();
        System.out.println();

    }
}
