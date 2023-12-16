package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class FibClassGenerator {
    private FibClassGenerator() {}

    public static Class<?> doClassWithFibMethod() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .defineMethod("fib", int.class, Visibility.PUBLIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibComputingAppender()))
            .make();

        return dynamicType.load(FibClassGenerator.class.getClassLoader()).getLoaded();
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
