package com.yourorganization.maven_sample.source.utils;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MethodParsedUtils {
    public List<MethodCallExpr> innerCalls(MethodDeclaration methodDeclaration) {
        List<MethodCallExpr> methodCallExprs =  methodDeclaration.getBody()
                .map(BlockStmt::getStatements)
                .map(statements -> {
                    return statements.stream()
                            .filter(it -> it.isExpressionStmt())
                            .map(s -> s.asExpressionStmt())
                            .map(s -> s.getExpression())
                            .filter(s -> s.isMethodCallExpr())
                            .map(s -> s.asMethodCallExpr())
                            .collect(Collectors.toList());
                }).orElse(Lists.newArrayList());
        return methodCallExprs;
    }
}
