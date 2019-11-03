package Lesson2;

public interface BullActions {
    void voice();
    default void look() {
        System.out.println("with eyes using horns");
    }
    void fight();
}
