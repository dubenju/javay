package javay.classloader;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.text.MessageFormat;  
  
/** 
 * 动态加载class文件 
 * @author Ken 
 * @since 2013-02-17 
 * 
 */  
public class DynamicClassLoader extends ClassLoader {  
      
    // 文件最后修改时间   
    private long lastModified;  
      
    // 加载class文件的classpath  
    private String classPath;  
      
    /** 
     * 检测class文件是否被修改 
     * @param filename 
     * @return 
     */  
    private boolean isClassModified(String name) {  
        File file = getFile(name);  
        if (file.lastModified() > lastModified) {  
            return true;  
        }  
        return false;  
    }  
      
    public Class<?> loadClass(String classPath, String name) throws ClassNotFoundException {  
        this.classPath = classPath;  
        if (isClassModified(name)) {  
            return findClass(name);  
        }  
        return null;  
    }  
  
    /** 
     * 获取class文件的字节码 
     * @param name      类的全名 
     * @return 
     */  
    private byte[] getBytes(String name) {  
        byte[] buffer = null;  
        FileInputStream in = null;  
        try {  
            File file = getFile(name);  
            lastModified = file.lastModified();  
            in = new FileInputStream(file);  
            buffer = new byte[in.available()];  
            in.read(buffer);  
            return buffer;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return buffer;  
    }  
  
    /** 
     * 获取class文件的真实路径 
     * @param name 
     * @return 
     */  
    private File getFile(String name) {  
        String simpleName = "";  
        String packageName = "";  
        if (name.indexOf(".") != -1) {  
            simpleName = name.substring(name.lastIndexOf(".") + 1);  
            packageName = name.substring(0, name.lastIndexOf(".")).replaceAll("[.]", "/");  
        } else {  
            simpleName = name;  
        }  
        File file = new File(MessageFormat.format("{0}/{1}/{2}.class", classPath, packageName, simpleName));  
        return file;  
    }  
  
    @Override  
    protected Class<?> findClass(String name) throws ClassNotFoundException {  
        byte[] byteCode = getBytes(name);  
        return defineClass(null, byteCode, 0, byteCode.length);  
    }  
}
