import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @Description: 自定义Classloader
 * @Author: sunzhiyu
 * @CreateDate: 2021/1/15 21:57
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> clz = new HelloClassLoader().findClass("Hello");
            clz.getMethod("hello").invoke(clz.newInstance());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try (InputStream inputStream = getClass().getResourceAsStream(name + ".xlass")) {
            bytes = inputStream2byte(inputStream);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(bytes)) {
            throw new ClassNotFoundException("class not found:" + name);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

}
