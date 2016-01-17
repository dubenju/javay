package javay.classloader;

public class DynamicClassLoade extends ClassLoader {
    
   
    public Class<?> findClass(byte[] b) throws ClassNotFoundException {

        return defineClass(null, b, 0, b.length);
    }
}
