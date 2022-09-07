package flyproject.flybuff.utils;

import flyproject.flybuff.FlyBuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Version {
    public static void check() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (FlyBuff.isPreview()) {
            try {
                URL url = new URL("https://fastmcmirror.org/flybuff-dev.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                    sb.append("\n");
                }
                sb.delete(sb.length() - 1, sb.length());
            } catch (java.io.IOException e) {
                try {
                    URL url = new URL("https://cdn.jsdelivr.net/gh/killerprojecte/pages@master/flybuff-dev.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                        sb.append("\n");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                } catch (IOException malformedURLException) {
                    System.err.println(Color.color("&b[FlyBuff 开发渠道更新] &f>>> &e正在使用的FlyBuff-DEV版本: " + FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion()));
                    System.err.println(Color.color(" &c无法检查更新"));
                    return;
                }
            }
            if (!sb.toString().equals(FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion())) {
                System.err.println(Color.color("&b[FlyBuff 开发渠道更新] &f>>> &e正在使用的FlyBuff-DEV版本: " + FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion()));
                System.err.println(Color.color(" &a最新开发版本: " + sb.toString() + " 请前往GitHub更新"));
            } else {
                System.out.println(Color.color("&b[FlyBuff 开发渠道更新] &f>>> &e正在使用的FlyBuff-DEV版本: " + FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion()));
                System.out.println(Color.color(" &a恭喜你 你正在使用最新的FlyBuff开发版本"));
            }
            System.err.println("[FlyBuff] 开发版本 可能会遇到BUG 请及时反馈！");
            return;
        }
        try {
            URL url = new URL("https://fastmcmirror.org/flybuff.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            while ((str = reader.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
            sb.delete(sb.length() - 1, sb.length());
        } catch (java.io.IOException e) {
            try {
                URL url = new URL("https://cdn.jsdelivr.net/gh/killerprojecte/pages@master/flybuff.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                    sb.append("\n");
                }
                sb.delete(sb.length() - 1, sb.length());
            } catch (IOException malformedURLException) {
                System.err.println(Color.color("&b[FlyBuff 更新] &f>>> &e正在使用的FlyBuff版本: " + FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion()));
                System.err.println(Color.color(" &c无法检查更新"));
                return;
            }
        }
        if (!sb.toString().equals(FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion())) {
            System.err.println(Color.color("&b[FlyBuff 更新] &f>>> &e正在使用的FlyBuff版本: " + FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion()));
            System.err.println(Color.color(" &a最新版本: " + sb.toString() + " 请前往MCBBS更新"));
        } else {
            System.out.println(Color.color("&b[FlyBuff 更新] &f>>> &e正在使用的FlyBuff版本: " + FlyBuff.getPlugin(FlyBuff.class).getDescription().getVersion()));
            System.out.println(Color.color(" &a恭喜你 你正在使用最新版本的FlyBuff"));
        }
    }
}
