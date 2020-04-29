package sec.lifeworld;

public class Spider extends Animal {


    public Spider() {
        super(6);
    }

    @Override
    public void eat() {
        System.out.println("Spider eats flyes!");
    }
}
