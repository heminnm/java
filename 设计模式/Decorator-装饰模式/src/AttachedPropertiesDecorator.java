// 附加属性装饰者
public abstract class AttachedPropertiesDecorator implements Man{
    private Man man;

    public AttachedPropertiesDecorator(Man man) {
        this.man = man;
    }

    public void getManDesc() {
        man.getManDesc();
    }
}