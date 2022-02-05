package ru.job4j.generics;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RoleStoreTest {

    @Test
    public void whenAddAndFindThenUsernameIsPetr() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        Role result = store.findById("1");
        assertThat(result.getRole(), is("Admin"));
    }

    @Test
    public void whenAddAndFindThenUserIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        Role result = store.findById("10");
        assertNull(result);
    }

    @Test
    public void whenAddDuplicateAndFindUsernameIsPetr() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.add(new Role("1", "User"));
        Role result = store.findById("1");
        assertThat(result.getRole(), is("Admin"));
    }

    @Test
    public void whenReplaceThenUsernameIsMaxim() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.replace("1", new Role("1", "User"));
        Role result = store.findById("1");
        assertThat(result.getRole(), is("User"));
    }

    @Test
    public void whenNoReplaceUserThenNoChangeUsername() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.replace("10", new Role("10", "User"));
        Role result = store.findById("1");
        assertThat(result.getRole(), is("Admin"));
    }

    @Test
    public void whenDeleteUserThenUserIsNull() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.delete("1");
        Role result = store.findById("1");
        assertNull(result);
    }

    @Test
    public void whenNoDeleteUserThenUsernameIsPetr() {
        RoleStore store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.delete("10");
        Role result = store.findById("1");
        assertThat(result.getRole(), is("Admin"));
    }
}
