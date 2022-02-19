package com.yourorganization.maven_sample.source;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.List;
import java.util.Map;

public class ParsedSource {
    private Map<MethodDeclaration, List<MethodCallExpr>> methodDeclarations;

    public ParsedSource(Map<MethodDeclaration, List<MethodCallExpr>> methodDeclarations) {
        this.methodDeclarations = methodDeclarations;
    }

    public Map<MethodDeclaration, List<MethodCallExpr>> getMethodDeclarations() {
        return methodDeclarations;
    }
}
