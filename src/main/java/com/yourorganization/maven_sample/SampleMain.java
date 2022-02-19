package com.yourorganization.maven_sample;

import com.yourorganization.maven_sample.clazz.JarClassReader;
import com.yourorganization.maven_sample.source.ParsedSource;
import com.yourorganization.maven_sample.source.SourceFileReader;

public class SampleMain {
    public static void main(String[] args) throws Exception {
        JarClassReader jarClassReader = new JarClassReader("/home/yanick/codes/a-platform/target/aplatform-0.0.1-SNAPSHOT.jar");
        ClassLoader classLoader = jarClassReader.getClassLoader();
        System.out.println(classLoader.loadClass("com.testerhome.aplatform.dao.GitlabProjectMapper"));

        ///////////////////////////////////////
        SourceFileReader sourceFileReader = new SourceFileReader("/home/yanick/codes/a-platform/src/main/java");
        ParsedSource parsedSource = sourceFileReader.parsedSource("com.testerhome.aplatform.controller.GitlabController");
//        sourceFileReader.listMethodCalls();
        System.out.println(parsedSource);
    }
}
