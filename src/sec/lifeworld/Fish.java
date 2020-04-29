package sec.lifeworld;

public class Fish extends Animal implements Pet {

    private String name;

    public Fish(String name) {
        super(0);
        this.name = name;
    }

    public Fish() {
        super(0);
        this.name = null;
    }

    @Override
    public void eat() {
        System.out.println("Fish eats meals!");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void play() {
        System.out.println("Fish don't play!");
    }

    @Override
    public void walk() {
        System.out.println("Fish don't walk, it's swimming the whole day");
        ;
    }
}
