package ru.job4j.generics;

public class Animal {
    private String name;

    public Animal(int age, String name) {
        this.name = name;
    }

    public Animal() {
        this.name = this.getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " eats.");
    }

    @Override
    public String toString() {
        return "Animal{"
                + "name='" + name + '\''
                + '}';
    }
}
