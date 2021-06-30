package graphical.basics.gobject.latex.lixao;

import org.scilab.forge.jlatexmath.Box;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class Gambeta {

    public static void proxyfy(Box box, List<BoxFrame> boxes) {
        var children = getChieldren(box);
        if (children == null || children.size() == 0) {
            return;
        }

        if (children.size() == 15) {
            System.out.println();
        }



        var proxies = new LinkedList<Box>();
        for (int i = 0; i < children.size(); i++) {
            var child = children.get(i);
            proxyfy(child, boxes);
            var proxy = ProxyBox.createProxy(child);
            System.out.println(proxy.toString());
            copyFromSlave(proxy, child);
            proxies.addLast(proxy);
        }
        setChildren(box, proxies);
    }

    public static List<Box> getChieldren(Box box) {
        try {
            var field = Box.class.getDeclaredField("children");
            field.setAccessible(true);
            return (List<Box>) field.get(box);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setChildren(Box box, List<Box> boxes) {
        try {
            var field = Box.class.getDeclaredField("children");
            field.setAccessible(true);
            field.set(box, boxes);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void setOnBox(Box box, Object o, String name) {
        try {
            var field = Box.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(box, o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static Object getOnBox(Box box, String name) {
        try {
            var field = Box.class.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(box);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void copyFromSlave(Box proxy, Box slave) {
        for (Field field : slave.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                setFinalStatic(field, proxy, field.get(slave));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Field field : Box.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                setFinalStatic(field, proxy, field.get(slave));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    static void setFinalStatic(Field field, Object obj, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(obj, newValue);
    }


}
