import java.util.List;
import java.util.stream.Collectors;

record Person(String name, int age) {}

public class RecordDemo {
    public static void main(String[] args) {
        List<Person> people = List.of(
            new Person("Mohana", 18),
            new Person("Tara", 17),
            new Person("Clara", 19)
        );
        people.forEach(System.out::println);
        List<Person> adults = people.stream()
                                    .filter(p -> p.age() >= 18)
                                    .collect(Collectors.toList());
        System.out.println("Adults: " + adults);
    }
}
/*
Person[name=Mohana, age=18]
Person[name=Tara, age=17]
Person[name=Clara, age=19]
Adults: [Person[name=Mohana, age=18], Person[name=Clara, age=19]]
*/