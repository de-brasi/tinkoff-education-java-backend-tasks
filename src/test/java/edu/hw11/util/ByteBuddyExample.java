package edu.hw11.util;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;

public class ByteBuddyExample {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException,
        NoSuchMethodException, InvocationTargetException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .defineMethod("hello", String.class, Visibility.PUBLIC)
            .intercept(
                new Implementation.Simple(new CustomByteCodeAppender())
            )
            .defineMethod("customIncr", int.class, Visibility.PUBLIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new IncrementVarAppender()))
            .make();

        Class<?> dynamicClass = dynamicType.load(ByteBuddyExample.class.getClassLoader())
            .getLoaded();

        Object instance = dynamicClass.newInstance();

        System.out.println(instance.getClass().getMethod("hello").invoke(instance));
        System.out.println(instance.getClass().getMethod("customIncr", int.class).invoke(instance, 3));
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

    public static class IncrementVarAppender implements ByteCodeAppender {
        @Override
        public @NotNull Size apply(
            @NotNull MethodVisitor methodVisitor,
            Implementation.@NotNull Context context,
            @NotNull MethodDescription methodDescription
        ) {
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1); // Загрузка аргумента (int) на стек
            methodVisitor.visitInsn(Opcodes.ICONST_1);   // Загрузка константы 1 на стек
            methodVisitor.visitInsn(Opcodes.IADD);       // Сложение (увеличение на 1)
            methodVisitor.visitInsn(Opcodes.IRETURN);    // Возвращение значени
            methodVisitor.visitEnd();

            return new Size(2, 1);
        }
    }
}
