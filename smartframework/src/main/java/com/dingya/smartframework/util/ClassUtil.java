package com.dingya.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * @Author: dingya
 * @Description:类操作工具类
 * @Date: Created in 9:43 2018/6/27
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获得指定包名下所有的类
     *
     * @param packageName 包名
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        // 类容器
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
//                System.out.println("url: " + url);
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            System.out.println("jarFile" + jarFile);
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
//                                    System.out.println("jarEntryName: " + jarEntryName);
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        addClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("get class set failure" + e);
            throw new RuntimeException();
        }
        return classSet;
    }

    /**
     * 向容器中增加指定路径下的所有类
     *
     * @param set         类容器
     * @param packagePath 包路径
     * @param packageName 包名
     */
    public static void addClass(Set<Class<?>> set, String packagePath, String packageName) {
        // 加载packagePath一级子目录下的.class文件和文件夹
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });
        // 如果是.class文件，向容器中增加这个类。否则，利用递归，向容器中增加其余类
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (files[i].isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (!StringUtil.isEmpty(packageName)) {
                    className = packageName + "." + className;
                    addClass(set, className);
                }
            } else {
                String subPackagePath = fileName;
                String subPackageName = fileName;
                if (!StringUtil.isEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                if (!StringUtil.isEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(set, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 向容器中增加单个类
     *
     * @param set       类容器
     * @param className 类文件路径名
     */
    public static void addClass(Set<Class<?>> set, String className) {
        Class<?> cls = loadClass(className, false);
        set.add(cls);
    }

    /**
     * 加载类
     *
     * @param className     类名
     * @param isInitialized 类是否初始化：是否加载类的静态代码块
     * @return 类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取类加载器
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
