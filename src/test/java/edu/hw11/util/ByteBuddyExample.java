package edu.hw11.util;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
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
        private final Label notFirstFibNumber = new Label();
        private final Label notSecondFibNumber = new Label();
        private final Label iEqualOreMoreThanNMinus2 = new Label();
        private final Label loopEntry = new Label();

        @Override
        public @NotNull Size apply(
            @NotNull MethodVisitor methodVisitor,
            Implementation.@NotNull Context context,
            @NotNull MethodDescription methodDescription
        ) {
            final int nArgIndex = 1;
            final int fib1ArgIndex = 2;
            final int fib2ArgIndex = fib1ArgIndex + 2;
            final int fibSumArgIndex = fib2ArgIndex + 2;
            final int iArgIndex = fibSumArgIndex + 2;

            methodVisitor.visitCode();

            // LOCALS: load (с локальной на стек операндов),
            //         store (со стека операндов в локальную переменную)
            //         const(загрузка на стек операндов)

            // Проверить что 1 или 2 число фиббоначи
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, notFirstFibNumber);
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            // если n != 1, то
            methodVisitor.visitLabel(notFirstFibNumber);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, notSecondFibNumber);
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            // (тут аргумент не 1 и не 2)
            methodVisitor.visitLabel(notSecondFibNumber);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            // init long fib1
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib1ArgIndex);
            // init long fib2
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib2ArgIndex);
            // init long fibSum
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fibSumArgIndex);
            // init int i
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, iArgIndex);

            {
                // todo: debug only
                methodVisitor.visitVarInsn(Opcodes.ILOAD, fib1ArgIndex);
            }

            // Loop entry
            methodVisitor.visitLabel(loopEntry);
//            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitFrame(Opcodes.F_APPEND, 6,
                new Object[]{Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.LONG, Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER},
                0, null
            );

            // while i < (n - 2)
            // Compare integers: i and (n - 2)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, iArgIndex);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitInsn(Opcodes.ISUB);          // (n - 2) to stack top

            // Comparing
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, iEqualOreMoreThanNMinus2);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            //          fibSum = fib1 + fib2
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib1ArgIndex);
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);
            methodVisitor.visitInsn(Opcodes.LADD);
            //          fib1 = fib2
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib1ArgIndex);
            //          fib2 = fibSum
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fibSumArgIndex);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib2ArgIndex);
            //          i++
            methodVisitor.visitVarInsn(Opcodes.ILOAD, iArgIndex);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.IADD);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, iArgIndex);
            //          to loop start
            methodVisitor.visitJumpInsn(Opcodes.GOTO, loopEntry);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);


            // loop end
            methodVisitor.visitLabel(iEqualOreMoreThanNMinus2);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            // return
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            methodVisitor.visitEnd();
            return new Size(4, 9);
        }
    }
}
