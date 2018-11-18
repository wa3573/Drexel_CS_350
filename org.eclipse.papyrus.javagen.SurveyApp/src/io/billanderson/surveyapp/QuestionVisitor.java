package io.billanderson.surveyapp;

public interface QuestionVisitor {
    void visit(Essay essay);
    void visit(Matching matching);
    void visit(MultipleChoice multipleChoice);
    void visit(Ranking ranking);
    void visit(ShortAnswer shortAnswer);
    void visit(TrueFalse trueFalse);
}
