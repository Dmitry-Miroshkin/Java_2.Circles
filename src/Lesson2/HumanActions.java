package Lesson2;

public interface HumanActions {
    void talk();
    default void look() {
        System.out.println("with eyes");
    }
}
