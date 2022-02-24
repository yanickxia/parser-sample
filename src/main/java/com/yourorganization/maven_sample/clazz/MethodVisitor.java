package com.yourorganization.maven_sample.clazz;


import org.apache.bcel.generic.*;

import java.util.Arrays;
import java.util.List;

public class MethodVisitor extends EmptyVisitor {

    private final ConstantPoolGen cp;
    private final List<InnerMethod> methodCalls;

    public MethodVisitor(ConstantPoolGen cp, List<InnerMethod> methodCalls) {
        this.cp = cp;
        this.methodCalls = methodCalls;
    }

    @Override
    public void visitINVOKEVIRTUAL(INVOKEVIRTUAL i) {
        methodCalls.add(InnerMethod.builder()
                .arguments(Arrays.asList(i.getArgumentTypes(cp)))
                .methodName(i.getMethodName(cp))
                .referenceType(i.getReferenceType(cp))
                .build());
    }

    @Override
    public void visitINVOKEINTERFACE(INVOKEINTERFACE i) {
        methodCalls.add(InnerMethod.builder()
                .arguments(Arrays.asList(i.getArgumentTypes(cp)))
                .methodName(i.getMethodName(cp))
                .referenceType(i.getReferenceType(cp))
                .build());
    }

    @Override
    public void visitINVOKESPECIAL(INVOKESPECIAL i) {
        methodCalls.add(InnerMethod.builder()
                .arguments(Arrays.asList(i.getArgumentTypes(cp)))
                .methodName(i.getMethodName(cp))
                .referenceType(i.getReferenceType(cp))
                .build());
    }

    @Override
    public void visitINVOKESTATIC(INVOKESTATIC i) {
        methodCalls.add(InnerMethod.builder()
                .arguments(Arrays.asList(i.getArgumentTypes(cp)))
                .methodName(i.getMethodName(cp))
                .referenceType(i.getReferenceType(cp))
                .build());
    }

    @Override
    public void visitINVOKEDYNAMIC(INVOKEDYNAMIC i) {
        methodCalls.add(InnerMethod.builder()
                .arguments(Arrays.asList(i.getArgumentTypes(cp)))
                .methodName(i.getMethodName(cp))
                .referenceType(i.getReferenceType(cp))
                .build());
    }

}
