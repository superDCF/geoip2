package com.superfa;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception
import com.maxmind.geoip2.model.CountryResponse;

import static java.net.InetAddress.*;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {
    public static void main(String[] args) {
// A File object pointing to your GeoIP2 or GeoLite2 database
        File database = null;
        URL resourceUrl = Main.class.getClassLoader().getResource("Geolite2-Country.mmdb");
        if (resourceUrl != null) {
            database = new File(resourceUrl.getFile());
        } else {
            System.out.println("无法找到指定的资源文件：");
        }

// This creates the DatabaseReader object. To improve performance, reuse
// the object across lookups. The object is thread-safe.
        DatabaseReader reader = null;
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InetAddress ipAddress = null;
        try {
            ipAddress = getByName("128.101.101.101");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try {
            CountryResponse country1 = reader.country(ipAddress);
            System.out.println(country1.getCountry());            // 'US'
            System.out.println(country1.getContinent());
            System.out.println(country1.getRegisteredCountry());
            System.out.println(country1.getRepresentedCountry());
            System.out.println(country1.getTraits());
            System.out.println(country1.getMaxMind());
            System.out.println(country1.toJson());
            System.out.println(country1.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeoIp2Exception e) {
            throw new RuntimeException(e);
        }
    }
}