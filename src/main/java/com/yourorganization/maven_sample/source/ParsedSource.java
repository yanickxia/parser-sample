package com.yourorganization.maven_sample.source;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ParsedSource {
    private Map<MethodDeclaration, List<MethodCallExpr>> methodDeclarations;
    private List<ImportDeclaration> importDeclarations;
}
