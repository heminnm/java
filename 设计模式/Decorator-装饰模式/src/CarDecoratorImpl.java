// 小车装饰者
public class CarDecoratorImpl extends AttachedPropertiesDecorator{
    private String car = "有车";

    public CarDecoratorImpl(Man man) {
        super(man);
    }

    public void addCar() {
        System.out.print(car + " ");
    }

    @Override
    public void getManDesc() {
        super.getManDesc();
        addCar();
    }
}
