package edu.hw11.util;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.Label;
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
            .defineMethod("fib", int.class, Visibility.PUBLIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibComputingAppender()))
            .make();

        Class<?> dynamicClass = dynamicType.load(
            ByteBuddyExample.class.getClassLoader(),
                ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Object instance = dynamicClass.newInstance();

        System.out.println(instance.getClass().getMethod("hello").invoke(instance));
        System.out.println(instance.getClass().getMethod("customIncr", int.class).invoke(instance, 3));

        System.out.println();

        for (int i = 1; i < 10; i++) {
            System.out.println(instance.getClass().getMethod("fib", int.class).invoke(instance, i));
        }
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

    public static class FibComputingAppender implements ByteCodeAppender {
        @Override
        public @NotNull Size apply(
            @NotNull MethodVisitor methodVisitor,
            Implementation.@NotNull Context context,
            @NotNull MethodDescription methodDescription
        ) {
            var label1 = new Label();
            var label2 = new Label();

            methodVisitor.visitCode();

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);

            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label1);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitInsn(Opcodes.IRETURN);

            methodVisitor.visitLabel(label1);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_2);

            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label2);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.IRETURN);

            methodVisitor.visitLabel(label2);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                "edu/hw11/util/FibExample",
                "fib",
                "(I)I"
            );
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitInsn(Opcodes.ISUB);
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                "edu/hw11/util/FibExample",
                "fib",
                "(I)I"
            );
            methodVisitor.visitInsn(Opcodes.IADD);
            methodVisitor.visitInsn(Opcodes.IRETURN);

            methodVisitor.visitMaxs(3, 1);
            methodVisitor.visitEnd();
            return new Size(3, 1);
        }
    }
}
