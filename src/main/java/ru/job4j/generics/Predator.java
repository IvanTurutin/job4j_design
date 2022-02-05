package ru.job4j.generics;

public class Predator extends Animal {

    public Predator(int age, String name) {
        super(age, name);
    }

    public Predator() {
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats other animals.");
    }

}
