package io.billanderson.surveyapp;

public class QuestionGradeVisitor implements QuestionVisitor {

    private boolean isCorrect;
    
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public void visit(Essay essay) {
	this.setCorrect(false);

    }

    @Override
    public void visit(Matching matching) {
	this.setCorrect(matching.isCorrect());

    }

    @Override
    public void visit(MultipleChoice multipleChoice) {
	this.setCorrect(multipleChoice.isCorrect());

    }

    @Override
    public void visit(Ranking ranking) {
	this.setCorrect(ranking.isCorrect());
    }

    @Override
    public void visit(ShortAnswer shortAnswer) {
	this.setCorrect(shortAnswer.isCorrect());
    }

    @Override
    public void visit(TrueFalse trueFalse) {	
	this.setCorrect(trueFalse.isCorrect());
    }

}
