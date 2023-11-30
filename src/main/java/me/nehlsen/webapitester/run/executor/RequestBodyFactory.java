package me.nehlsen.webapitester.run.executor;

import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class RequestBodyFactory {

    private static class EvaluationContext {
        public TaskExecutionContext context;

        public EvaluationContext(TaskExecutionContext context) {
            this.context = context;
        }
    }

    public String buildBody(TaskExecutionContext context) {
        if (taskHasBodyExpression(context)) {
            return evaluateBodyExpression(context);
        }

        return getTaskFixedBody(context);
    }

    private boolean taskHasBodyExpression(TaskExecutionContext context) {
        return context
                .getTask()
                .getParameters()
                .containsKey("body_expression");
    }

    private String getTaskFixedBody(TaskExecutionContext context) {
        return context
                .getTask()
                .getParameters()
                .getOrDefault("body", "");
    }

    private String getTaskBodyExpression(TaskExecutionContext context) {
        return context
                .getTask()
                .getParameters()
                .getOrDefault("body_expression", "");
    }

    private String evaluateBodyExpression(TaskExecutionContext context) {
        return createExpressionParser()
                .parseExpression(getTaskBodyExpression(context))
                .getValue(createEvaluationContext(context), String.class);
    }

    private static EvaluationContext createEvaluationContext(TaskExecutionContext context) {
        return new EvaluationContext(context);
    }

    private static SpelExpressionParser createExpressionParser() {
        return new SpelExpressionParser();
    }
}
