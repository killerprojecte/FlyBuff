package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ResourceX {
    public static void saveFile(String name,boolean replace){
        URL url = FlyBuff.instance.getClass().getClassLoader().getResource(name);
        if (url==null){
            FlyBuff.instance.getLogger().severe(name + " 无法解压!");
            return;
        }
        File file = new File(FlyBuff.instance.getDataFolder() + "/" + name);
        if (!replace) {
            if (file.exists()) return;
        } else {
            if (file.exists()) {
                FlyBuff.instance.getLogger().warning("文件 " + name + " 正在以覆盖方式解压");
                file.delete();
            }
        }
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            FlyBuff.instance.getLogger().severe("无法解压资源 " + name + ":" + e.getMessage());
        }
        connection.setUseCaches(false);
        try {
            saveFile(connection.getInputStream(),file);
        } catch (IOException e) {
            FlyBuff.instance.getLogger().severe("无法解压资源 " + name + ":" + e.getMessage());
        }
    }

    private static void saveFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
