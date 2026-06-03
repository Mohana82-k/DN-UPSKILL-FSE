public class SimpleClass {
    public void sayHello() {
        System.out.println("Hello");
    }
}
/*
Compiled from "SimpleClass.java"
public class SimpleClass {
  public SimpleClass();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public void sayHello();
    Code:
       0: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #13                 // String Hello
       5: invokevirtual #15                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return
}*/
