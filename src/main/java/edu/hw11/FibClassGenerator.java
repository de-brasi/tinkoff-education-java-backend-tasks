package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.pool.TypePool;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class FibClassGenerator {
    private FibClassGenerator() {}

    public static Class<?> doClassWithFibMethod() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .visit(new EnableFramesComputing())
            .defineMethod("fib", long.class, Visibility.PUBLIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibComputingAppender()))
            .make();

        return dynamicType.load(
                FibClassGenerator.class.getClassLoader(),
                ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
    }

    static class EnableFramesComputing implements AsmVisitorWrapper {
        @Override
        public final int mergeWriter(int flags) {
            return flags | ClassWriter.COMPUTE_FRAMES;
        }

        @Override
        public final int mergeReader(int flags) {
            return flags | ClassWriter.COMPUTE_FRAMES;
        }

        @Override
        public final ClassVisitor wrap(
            TypeDescription td, ClassVisitor cv, Implementation.Context ctx,
            TypePool tp, FieldList<FieldDescription.InDefinedShape> fields,
            MethodList<?> methods, int wflags, int rflags
        ) {
            return cv;
        }
    }

    public static class FibComputingAppender implements ByteCodeAppender {
        private final Label notFirstFibNumber = new Label();
        private final Label notSecondFibNumber = new Label();
        private final Label onExit = new Label();
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

            //  load (с локальной на стек операндов),
            //  store (со стека операндов в локальную переменную)
            //  const(загрузка на стек операндов)

            // Проверить что 1 или 2 число фиббоначи
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, notFirstFibNumber);
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            // если n != 1, то проверить n на равенство с 2
            // stack: {} locals: {Opcode.INTEGER}
            methodVisitor.visitLabel(notFirstFibNumber);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, notSecondFibNumber);
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            // (тут аргумент не 1 и не 2)
            // stack: {} locals: {Opcode.INTEGER}
            methodVisitor.visitLabel(notSecondFibNumber);
            // init long fib1
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib1ArgIndex);  // fib1ArgIndex = 0
            // init long fib2
            methodVisitor.visitInsn(Opcodes.LCONST_1);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib2ArgIndex);  // fib2ArgIndex = 1
            // init long fibSum
            methodVisitor.visitInsn(Opcodes.LCONST_0);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fibSumArgIndex);  // fibSumArgIndex = 0
            // init int i
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitVarInsn(Opcodes.ISTORE, iArgIndex);  // iArgIndex = 0

            // stack: {} locals: {integer, long, long_2nd, long, long_2nd, long, long_2nd, integer}

            // Loop entry
            methodVisitor.visitLabel(loopEntry);

            // while iArgIndex < (nArgIndex - 2)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, iArgIndex);
            methodVisitor.visitVarInsn(Opcodes.ILOAD, nArgIndex);
            methodVisitor.visitInsn(Opcodes.ICONST_2);
            methodVisitor.visitInsn(Opcodes.ISUB);          // (n - 2) to stack top

            // Comparing
            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, onExit);

            //          fibSum = fib1 + fib2
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib1ArgIndex);
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);
            methodVisitor.visitInsn(Opcodes.LADD);
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fibSumArgIndex);  // stack: {}
            //          fib1 = fib2
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);  // fib1ArgIndex = fib2ArgIndex
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib1ArgIndex);  // stack: {}
            //          fib2 = fibSum
            methodVisitor.visitVarInsn(Opcodes.LLOAD, fibSumArgIndex);  // fib2ArgIndex = fibSumArgIndex
            methodVisitor.visitVarInsn(Opcodes.LSTORE, fib2ArgIndex);  // stack: {}
            //          i++
            methodVisitor.visitVarInsn(Opcodes.ILOAD, iArgIndex);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitInsn(Opcodes.IADD);  // i += 1
            methodVisitor.visitVarInsn(Opcodes.ISTORE, iArgIndex);  // stack: {}
            //          to loop start
            methodVisitor.visitJumpInsn(Opcodes.GOTO, loopEntry);

            // loop end
            methodVisitor.visitLabel(onExit);

            methodVisitor.visitVarInsn(Opcodes.LLOAD, fib2ArgIndex);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            methodVisitor.visitEnd();
            return new Size(4, 8);
        }
    }

}
