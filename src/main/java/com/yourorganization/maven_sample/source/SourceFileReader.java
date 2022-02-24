package com.yourorganization.maven_sample.source;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceFileReader {
    private final String projectDir;

    public SourceFileReader(String projectDir) {
        this.projectDir = projectDir;
    }


    public ParsedSource parsedSource(String clazz) {
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        List<MethodCallExpr> methodCallExprs = new ArrayList<>();
        List<ImportDeclaration> importDeclarations = new ArrayList<>();

        Map<MethodDeclaration, List<MethodCallExpr>> methodCalls = new HashMap<>();
        String file = projectDir + "/" + clazz.replaceAll("\\.", "/") + ".java";
        try {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodDeclaration n, Object arg) {
                    super.visit(n, arg);
                    methodDeclarations.add(n);
                    methodCalls.put(n, new ArrayList<>());
                }

                @Override
                public void visit(ImportDeclaration n, Object arg) {
                    super.visit(n, arg);
                    importDeclarations.add(n);
                }

                @Override
                public void visit(MethodCallExpr n, Object arg) {
                    super.visit(n, arg);
                    methodCallExprs.add(n);
                }
            }.visit(StaticJavaParser.parse(new File(file)), null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (MethodCallExpr methodCallExpr : methodCallExprs) {
            MethodDeclaration methodDeclaration = foundMatchMethodDeclaration(methodDeclarations, methodCallExpr);
            methodCalls.get(methodDeclaration).add(methodCallExpr);
        }


        return ParsedSource.builder()
                .importDeclarations(importDeclarations)
                .methodDeclarations(methodCalls)
                .build();
    }

    private static MethodDeclaration foundMatchMethodDeclaration(List<MethodDeclaration> methodDeclarations, MethodCallExpr methodCallExpr) {
        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            if (methodCallExpr.getBegin().get().line > methodDeclaration.getBegin().get().line && methodCallExpr.getEnd().get().line < methodDeclaration.getEnd().get().line) {
                return methodDeclaration;
            }
        }
        return null;
    }


    public void listMethodCalls() {
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new VoidVisitorAdapter<Object>() {

                    @Override
                    public void visit(MethodDeclaration n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(" [L " + n.getBegin().get().line + "] " + n);
                    }

                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(" [L " + n.getBegin().get().line + "] " + n);
                    }
                }.visit(StaticJavaParser.parse(file), null);
                System.out.println(); // empty line
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(new File(this.projectDir));
    }
}
