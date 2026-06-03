import java.lang.reflect.*;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("java.util.ArrayList");
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("Methods of ArrayList:");
        for (Method m : methods) {
            System.out.println(" - " + m.getName());
        }
        // Invoke a method dynamically
        Object list = clazz.getDeclaredConstructor().newInstance();
        Method add = clazz.getMethod("add", Object.class);
        add.invoke(list, "Hello Reflection");
        System.out.println("List content: " + list);
    }
}
/*
Methods of ArrayList:
    - trimToSize
    - ensureCapacity
    - size
    - isEmpty
    - contains
    - indexOf
    - lastIndexOf
    - clone
    - toArray
    - toArray
    - get
    - set
    - add
    - add
    - remove
    - clear
    - addAll
    - addAll
    - removeAll
    - retainAll
    - listIterator
    - listIterator
    - iterator
    - subList
List content: [Hello Reflection]
*/