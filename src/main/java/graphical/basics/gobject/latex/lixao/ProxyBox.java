package graphical.basics.gobject.latex.lixao;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.objenesis.ObjenesisHelper;
import org.scilab.forge.jlatexmath.Box;

import java.lang.reflect.Method;

public class ProxyBox implements MethodInterceptor {

    Box slave;

    public ProxyBox(Box slave) {
        this.slave = slave;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        var result = method.invoke(slave, args);
        System.out.println("invocastes o metodo:" + method.getName());
        return result;
    }


    public static Box createProxy(Box box) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(box.getClass());
        enhancer.setCallbackType(ProxyBox.class);

        var interceptor = new ProxyBox(box);

        final Class<?> proxyClass = enhancer.createClass();
        Enhancer.registerCallbacks(proxyClass, new Callback[]{interceptor});
        return (Box) ObjenesisHelper.newInstance(proxyClass);
    }
}
