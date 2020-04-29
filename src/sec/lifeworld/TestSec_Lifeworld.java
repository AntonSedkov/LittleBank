package sec.lifeworld;

public class TestSec_Lifeworld {

    static Cat myCat = new Cat("Barsik");
    static Animal spider = new Spider();
    static Pet myFish = new Fish("Watergod");

    public static void main(String[] args) {
        myCat.getName();
        System.out.println(myCat.getName());
        myCat.walk();
        myCat.play();
        myCat.eat();

        System.out.println("");
        spider.eat();
        spider.walk();

        System.out.println("");
        System.out.println(myFish.getName());
        ((Fish) myFish).walk();
        ((Fish) myFish).eat();
        myFish.play();
    }


}
