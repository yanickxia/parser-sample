package com.yourorganization.maven_sample.clazz;

import lombok.Builder;
import lombok.Getter;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

import java.util.List;

@Builder
@Getter
public class InnerMethod {
    private ReferenceType referenceType;
    private String methodName;
    private List<Type> arguments;
}
