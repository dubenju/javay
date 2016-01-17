package javay.classloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author vma
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, InterruptedException {
        String path = "D:\\deploy\\JDBC\\ClassLoader\\build\\classes\\classloader\\LocalClass.class";
        ManageClassLoader mc = new ManageClassLoader();
        while(true){
       Class c = mc.loadClass(path);
        Object o = c.newInstance();
       Method m = c.getMethod("getName");
        m.invoke(o);
        System.out.println(c.getClassLoader());
        Thread.sleep(5000);
        }


    }
}