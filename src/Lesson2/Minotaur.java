package Lesson2;

public class Minotaur implements HumanActions, BullActions {
    @Override
    public void voice() {

    }

    @Override
    public void fight() {

    }

    @Override
    public void talk() {

    }

    @Override
    public void look() {
        HumanActions.super.look();
    }
}
