// 存款装饰者
public class DepositDecoratorImpl extends AttachedPropertiesDecorator{
    private String deposit = "有存款";

    public DepositDecoratorImpl(Man man) {
        super(man);
    }

    public void addDeposit() {
        System.out.print(deposit + " ");
    }

    @Override
    public void getManDesc() {
        super.getManDesc();
        addDeposit();
    }
}