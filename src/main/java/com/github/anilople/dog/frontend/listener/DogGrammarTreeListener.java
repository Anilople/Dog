package com.github.anilople.dog.frontend.listener;

import com.github.anilople.dog.backend.ast.Evaluation;
import com.github.anilople.dog.backend.ast.NumberName;
import com.github.anilople.dog.backend.ast.StringName;
import com.github.anilople.dog.backend.ast.VariableName;
import com.github.anilople.dog.backend.ast.lambda.Application;
import com.github.anilople.dog.backend.ast.lambda.Function;
import com.github.anilople.dog.backend.ast.lambda.LambdaExpression;
import com.github.anilople.dog.backend.ast.lambda.Name;
import com.github.anilople.dog.backend.ast.number.IntegerName;
import com.github.anilople.dog.backend.ast.number.NaturalName;
import com.github.anilople.dog.frontend.DogBaseListener;
import com.github.anilople.dog.frontend.DogParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 对解析出来的语法树，在遍历时的监听器.
 * 负责遍历完成后，可以建立出{@link List<Evaluation>}供后端使用
 */
public class DogGrammarTreeListener extends DogBaseListener {

    private final List<Evaluation> evaluations = new ArrayList<>();

    private final Stack<LambdaExpression> lambdaExpressionStack = new Stack<>();

    private final Stack<Name> nameStack = new Stack<>();

    private final Stack<NumberName> numberNameStack = new Stack<>();

    @Override
    public void exitEvaluation(DogParser.EvaluationContext ctx) {
        LambdaExpression lambdaExpression = lambdaExpressionStack.pop();
        Evaluation evaluation = new Evaluation(lambdaExpression);
        evaluations.add(evaluation);
    }

    @Override
    public void exitApplicationLabel(DogParser.ApplicationLabelContext ctx) {
        LambdaExpression right = lambdaExpressionStack.pop();
        LambdaExpression left = lambdaExpressionStack.pop();
        Application application = new Application(left, right);
        lambdaExpressionStack.push(application);
    }

    @Override
    public void exitName(DogParser.NameContext ctx) {
        Name name = nameStack.pop();
        lambdaExpressionStack.push(name);
    }

    @Override
    public void exitFunctionLabel(DogParser.FunctionLabelContext ctx) {
        LambdaExpression body = lambdaExpressionStack.pop();
        Name name = (Name) lambdaExpressionStack.pop();
        Function function = new Function(name, body);
        lambdaExpressionStack.push(function);
    }

    @Override
    public void exitVariableName(DogParser.VariableNameContext ctx) {
        VariableName variableName = new VariableName(ctx.getText());
        nameStack.push(variableName);
    }

    @Override
    public void exitStringName(DogParser.StringNameContext ctx) {
        assert ctx.getText().length() >= 2;
        // 带有2边的双引号的字符串
        String stringContentQuote = ctx.getText();
        assert '"' == stringContentQuote.charAt(0);
        assert '"' == stringContentQuote.charAt(stringContentQuote.length() - 1);

        // 实际上，想要的字符串内容
        String realContent = stringContentQuote.substring(1, stringContentQuote.length() - 1);
        StringName stringName = new StringName(realContent);
        nameStack.push(stringName);
    }

    @Override
    public void exitNumberName(DogParser.NumberNameContext ctx) {
        NumberName numberName = numberNameStack.pop();
        nameStack.push(numberName);
    }

    @Override
    public void exitNaturalName(DogParser.NaturalNameContext ctx) {
        final String text = ctx.getText();
        final int value = Integer.parseInt(text);
        final NaturalName naturalName = new NaturalName(value);
        numberNameStack.push(naturalName);
    }

    @Override
    public void exitIntegerName(DogParser.IntegerNameContext ctx) {
        final String text = ctx.getText();
        final IntegerName integerName = new IntegerName(text);
        numberNameStack.push(integerName);
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
}
