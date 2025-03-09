class test{
    public static void main(String[] args) {
        Pizza food = new Pizza();
        food.cook();
        Soup food1 = new Soup();
        food1.cook();
    }
}

interface Food{
    public void cook();
}

class Pizza implements Food{
    public void cook() {
        System.out.println("Готовим пиццу...");
    }
}

class Soup implements Food{
    public void cook() {
        System.out.println("Готовим суп...");
    }
}

abstract class FoodCreator{
    public abstract Food createFood();

    public void prepareMeal() {
        Food food = createFood();
        food.cook();
    }
}