package com.yourorganization.maven_sample.clazz;

import lombok.SneakyThrows;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassFileParser {
    private final ClassParser classParser;
    private final JavaClass javaClass;
    private final ConstantPoolGen constantPoolGen;

    @SneakyThrows
    public ClassFileParser(ClassParser classParser) {
        this.classParser = classParser;
        this.javaClass = classParser.parse();
        this.constantPoolGen = new ConstantPoolGen(this.javaClass.getConstantPool());
    }

    @SneakyThrows
    public List<Method> methods() {
        return Arrays.asList(classParser.parse().getMethods());
    }

    public List<InnerMethod> methodInnerCalls(Method method) {
        MethodGen mg = new MethodGen(method, this.javaClass.getClassName(), this.constantPoolGen);
        List<InnerMethod> innerMethods = new ArrayList<>();
        for (InstructionHandle ih = mg.getInstructionList().getStart();
             ih != null; ih = ih.getNext()) {
            Instruction i = ih.getInstruction();

            if (!visitInstruction(i)) {
                i.accept(new MethodVisitor(this.constantPoolGen, innerMethods));
            }
        }
        return innerMethods;
    }

    private boolean visitInstruction(Instruction i) {
        short opcode = i.getOpcode();
        return ((InstructionConst.getInstruction(opcode) != null)
                && !(i instanceof ConstantPushInstruction)
                && !(i instanceof ReturnInstruction));
    }
}
