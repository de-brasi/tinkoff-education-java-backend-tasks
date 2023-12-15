package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class FibClassGenerator {
    private FibClassGenerator() {}

    public static Class<?> doClassWithFibMethod() {
        // TODO:
        //  1) сделать класс с bytebuddy;                                                          - done
        //  2) сделать метод у класса с помощью bytebuddy (простой метод который вернет строку);   - done
        //  3) метод при помощи байткода bytebuddy
        //  4) сделать этот метод на ASM;
        //  5) метод fib на ASM

        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(
                named("toString").and(returns(String.class)).and(takesArguments(0))
            )
            .intercept(new Implementation.Simple(new CustomByteCodeAppender()))
            .make();

        Class<?> dynamicClass = dynamicType.load(FibClassGenerator.class.getClassLoader())
            .getLoaded();

        return dynamicClass;
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
