package com.yourorganization.maven_sample;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.yourorganization.maven_sample.clazz.ClassFileParser;
import com.yourorganization.maven_sample.clazz.InnerMethod;
import com.yourorganization.maven_sample.clazz.JarClassReader;
import com.yourorganization.maven_sample.source.ParsedSource;
import com.yourorganization.maven_sample.source.SourceFileReader;
import com.yourorganization.maven_sample.source.utils.MethodParsedUtils;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Method;

import java.util.List;

public class SampleMain {
    public static void main(String[] args) throws Exception {
        JarClassReader jarClassReader = new JarClassReader("/home/yanick/codes/a-platform/target/aplatform-0.0.1-SNAPSHOT.jar");
        ClassLoader classLoader = jarClassReader.getClassLoader();
        Class<?> aClass = classLoader.loadClass("com.testerhome.aplatform.controller.GitlabController");
        ClassParser classParser = jarClassReader.getClassParser(aClass);
        ClassFileParser classFileParser = new ClassFileParser(classParser);

        List<Method> methods = classFileParser.methods();
        for (Method method : methods) {
            List<InnerMethod> innerMethods = classFileParser.methodInnerCalls(method);
        }


        ///////////////////////////////////////
        SourceFileReader sourceFileReader = new SourceFileReader("/home/yanick/codes/a-platform/src/main/java");
        ParsedSource parsedSource = sourceFileReader.parsedSource("com.testerhome.aplatform.controller.GitlabController");

        for (MethodDeclaration methodDeclaration : parsedSource.getMethodDeclarations().keySet()) {
            List<MethodCallExpr> methodCallExprs = MethodParsedUtils.innerCalls(methodDeclaration);
        }

//        sourceFileReader.listMethodCalls();
        System.out.println(parsedSource);
    }
}
