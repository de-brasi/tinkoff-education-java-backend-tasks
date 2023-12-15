package edu.hw11.util;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackManipulation.Size;
import net.bytebuddy.implementation.bytecode.constant.MethodConstant;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import org.jetbrains.annotations.NotNull;

public class ByteBuddyExample {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(new Implementation.Simple(new CustomByteCodeAppender()))
            .make();

        Class<?> dynamicClass = dynamicType.load(ByteBuddyExample.class.getClassLoader())
            .getLoaded();

        Object instance = dynamicClass.newInstance();
        System.out.println(instance.toString());
    }

    public static class CustomByteCodeAppender implements ByteCodeAppender {
        @Override
        public @NotNull Size apply(
            @NotNull MethodVisitor methodVisitor,
            Implementation.@NotNull Context context,
            MethodDescription methodDescription
        ) {
            StackManipulation.Size operandStackSize = new StackManipulation.Compound(
                new TextConstant("Custom implementation"),
                MethodReturn.REFERENCE
            ).apply(methodVisitor, context);

            return new Size(operandStackSize.getMaximalSize(), methodDescription.getStackSize());
        }
    }
}
