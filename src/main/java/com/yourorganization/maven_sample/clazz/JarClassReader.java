package com.yourorganization.maven_sample.clazz;

import net.lingala.zip4j.ZipFile;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.jar.JarFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

public class JarClassReader {

    private String rootJar;
    private String tmpDir;

    public JarClassReader(String rootJar) {
        this.rootJar = rootJar;
    }

    public ClassLoader getClassLoader() throws IOException {
        unzipJar();
        List<URL> urls = getJarContent(this.rootJar);
        return new LaunchedURLClassLoader(false, urls.toArray(new URL[]{}), null);
    }

    private void unzipJar() throws IOException {
        String tmpdir = Files.createTempDirectory("unzip").toFile().getAbsolutePath();
        new ZipFile(this.rootJar).extractAll(tmpdir);
        this.tmpDir = tmpdir + "/";
    }

    public List<URL> getJarContent(String jarPath) throws IOException {
        List<URL> content = new ArrayList<>();
        JarFile jarFile = new JarFile(new File(jarPath));
        Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry entry = (JarEntry) e.nextElement();
            String name = entry.getName();
            if (name.startsWith("BOOT-INF/lib/") && !name.equals("BOOT-INF/lib/")) {
                content.add(new URL("jar:file:" + this.tmpDir + name + "!/"));
            }
        }
        content.add(new URL("file:" + this.tmpDir + "BOOT-INF/classes/"));
        return content;
    }
}
