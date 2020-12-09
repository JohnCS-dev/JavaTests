import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SomeValue {
    public int value;
    SomeValue(int v) {
        value = v;
    }
}

class Obj1 {}
class Obj2 {}
class Obj3 {}


public class SomeTest {
    static void func(int i, float f) {
        i = 5;
        f = 7.f;
    }

    static void funcObj(Integer i, Float f) {
        i = 5;
        f = 7.f;
    }

    static void funcObj(SomeValue v) {
        v.value++;
    }

    public static void main(String[] args) {
        int i = 0;
        float f = 0;
        Integer io = new Integer(0);
        Float fo = new Float(0.0f);
        SomeValue v = new SomeValue(5);
        func(i, f);
        funcObj(io++, fo++);
        funcObj(v);
        System.out.printf("%d %f\n", i, f);
        System.out.printf("Objects: %d %f\n", io, fo);
        System.out.printf("Object: %d\n", v.value);

        ArrayList<Object> list = new ArrayList<>();
        list.add(new Obj1());
        list.add(new Obj2());
        list.add(new Obj3());
        for (Object obj: list) {
            System.out.printf("List object: %s\n", obj.getClass());
        }

        String input = "Hello Java! Hello JavaScript! JavaSE 8.";
        Pattern pattern = Pattern.compile("Java(\\w*)");
        Matcher matcher = pattern.matcher(input);
        while(matcher.find())
            System.out.println(matcher.group());
    }
}
