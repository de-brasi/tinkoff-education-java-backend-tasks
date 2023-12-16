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
            .defineMethod("fib", long.class, Visibility.PUBLIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibComputingAppender()))
            .make();

        Class<?> dynamicClass = dynamicType.load(
            ByteBuddyExample.class.getClassLoader(),
                ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Object instance = dynamicClass.newInstance();

        for (int i = 1; i < 10; i++) {
            System.out.println(instance.getClass().getMethod("fib", int.class).invoke(instance, i));
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
        private final Label label1 = new Label();
        private final Label label2 = new Label();
        private final Label label3 = new Label();
        private final Label labelGotoEntry = new Label();

        @Override
        public @NotNull Size apply(
            @NotNull MethodVisitor methodVisitor,
            Implementation.@NotNull Context context,
            @NotNull MethodDescription methodDescription
        ) {
            final int nArgIndex = 1;
            final int fib1ArgIndex = 2;
            final int fib2ArgIndex = 3;
            final int fibSumArgIndex = 4;
            final int iArgIndex = 5;

            methodVisitor.visitCode();

            // LOCALS: load (с локальной на стек операндов),
            //         store (со стека операндов в локальную переменную)
            //         const(загрузка на стек операндов)

            //  0: lconst_0
            // todo: 0 на стек операндов
            methodVisitor.visitInsn(Opcodes.LCONST_0);

            //  1: lstore_1
            // todo: загрузить 0 со стека в переменную fib1
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib1ArgIndex);

            {
                // todo: debug only
                methodVisitor.visitVarInsn(Opcodes.ILOAD, fib1ArgIndex);
            }

            //  2: lconst_1
            // todo: 1 на стек операндов
            methodVisitor.visitInsn(Opcodes.LCONST_1);

            //  3: lstore_3
            // todo: загрузить 1 со стека в переменную fib2
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib2ArgIndex);


            // todo: 0 на стек операндов
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            // todo: загрузить 0 со стека в переменную fibSum
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fibSumArgIndex);

            //  4: iload_0
            // todo: значение аргумента n на стек операндов
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);

            //  5: iconst_1
            // todo: число 1 на стек операндов
            methodVisitor.visitInsn(Opcodes.ICONST_1);

            //  6: if_icmpne     11
            // todo: сравнить значение n и число 1
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label1);


            //  9: lload_1
            // todo: если равны, то загрузить значение fib1 на стек
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib1ArgIndex);

            //  10: lreturn
            // todo: возврат
            methodVisitor.visitInsn(Opcodes.LRETURN);


            //  11: iload_0
            methodVisitor.visitLabel(label1);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            // todo: значение аргумента n на стек операндов
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);

            //  12: iconst_2
            // todo: число 2 на стек операндов
            methodVisitor.visitInsn(Opcodes.ICONST_2);

            //  13: if_icmpne     18
            // todo: сравнить значение n и число 2
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label2);

            //  16: lload_3
            // todo: если равны, то загрузить значение fib2 на стек
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);

            //  17: lreturn
            // todo: возврат
            methodVisitor.visitInsn(Opcodes.LRETURN);


            //  18: iconst_0
            methodVisitor.visitLabel(label2);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            // todo: число 0 на стек операндов
            methodVisitor.visitInsn(Opcodes.ICONST_0);

            //  19: istore        7
            // todo: загрузить 0 со стека в переменную i
            methodVisitor.visitVarInsn(Opcodes.ISTORE, iArgIndex);

            //  21: iload         7
            methodVisitor.visitLabel(labelGotoEntry);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            // todo: значение аргумента i на стек операндов
            methodVisitor.visitVarInsn(Opcodes.ILOAD, iArgIndex);

            //  23: iload_0
            // todo: значение аргумента n на стек операндов
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);

            //  24: iconst_2
            // todo: число 2 на стек операндов
            methodVisitor.visitInsn(Opcodes.ICONST_2);

            //  25: isub
            // todo: что происходит? что на вершине стека?результат?
            methodVisitor.visitInsn(Opcodes.ISUB);


            //  26: if_icmpge     45
            // todo: сравнить значение i и (n - 2)
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label3);

            //  29: lload_1
            // TODO: если сравнение IF_ICMPGE правда (то есть i >= (n-2)), то прыжок на label3?
            // todo: если i < (n-2), то загрузить значение fib1 на стек
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib1ArgIndex);

            //  30: lload_3
            // todo: загрузить значение fib2 на стек
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);

            //  31: ladd
            // todo: fib1 + fib2
            methodVisitor.visitInsn(Opcodes.LADD);

            //  32: lstore        5
            // todo: сохранить результат в fibSum
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fibSumArgIndex);

            //  34: lload_3
            // todo: значение аргумента fib2 на стек операндов
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);

            //  35: lstore_1
            // todo: загрузить fib2 со стека в переменную fib1
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib1ArgIndex);

            //  36: lload         5
            // todo: значение аргумента fibSum на стек операндов
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fibSumArgIndex);

            //  38: lstore_3
            // todo: загрузить fibSum со стека в переменную fib2
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib2ArgIndex);

            //  39: iinc          7, 1
            // todo: увеличить значение i на 1
            methodVisitor.visitIincInsn(iArgIndex, 1);

            //  42: goto          21
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelGotoEntry);

            //  45: lload_3
            methodVisitor.visitLabel(label3);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            // todo: загрузить ответ из fib2 на стек операндов
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);

            //  46: lreturn
            methodVisitor.visitInsn(Opcodes.LRETURN);

            methodVisitor.visitEnd();
            return new Size(4, 8 + 1);
        }
    }
}
