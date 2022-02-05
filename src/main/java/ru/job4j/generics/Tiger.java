package ru.job4j.generics;

public class Tiger extends Predator {
    public Tiger(int age, String name) {
        super(age, name);
    }

    public Tiger() {
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats antelope.");
    }
}
