package io.billanderson.surveyapp;

/*
 * QuestionIdentificationVisitor implements the Visitor design pattern.
 * It is used to determine characteristics of questions which determine
 * program flow which is dependent on the type of question.
 */
public class QuestionIdentificationVisitor implements QuestionVisitor {

    private boolean isCorrect;
    private boolean isGradable;
    private boolean isEssay;
    private boolean isMultipleChoice;
    private boolean isMatching;
    private boolean isRanking;
    private boolean isTrueFalse;
    private boolean isCountable;
    
    public boolean isCorrect() {
        return isCorrect;
    }
    
    public boolean isGradable() {
	return isGradable;
    }

    public void setGradable(boolean isGradable) {
	this.isGradable = isGradable;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }

    public boolean isMatching() {
        return isMatching;
    }

    public void setMatching(boolean isMatching) {
        this.isMatching = isMatching;
    }

    public boolean isRanking() {
        return isRanking;
    }

    public void setRanking(boolean isRanking) {
        this.isRanking = isRanking;
    }

    public boolean isTrueFalse() {
        return isTrueFalse;
    }

    public void setTrueFalse(boolean isTrueFalse) {
        this.isTrueFalse = isTrueFalse;
    }

    public boolean isCountable() {
	return isCountable;
    }

    public void setCountable(boolean isCountable) {
	this.isCountable = isCountable;
    }

    public boolean isEssay() {
	return isEssay;
    }

    public void setEssay(boolean isEssay) {
	this.isEssay = isEssay;
    }

    @Override
    public void visit(Essay essay) {
	this.setGradable(false);
	this.setEssay(true);
	this.setMatching(false);
	this.setMultipleChoice(false);
	this.setRanking(false);
	this.setTrueFalse(false);
    }

    @Override
    public void visit(Matching matching) {
	this.setGradable(true);
	this.setEssay(false);
	this.setMatching(true);
	this.setMultipleChoice(false);
	this.setRanking(false);
	this.setTrueFalse(false);
	this.setCountable(false);
    }

    @Override
    public void visit(MultipleChoice multipleChoice) {
	this.setGradable(true);
	this.setEssay(false);
	this.setMatching(false);
	this.setMultipleChoice(true);
	this.setRanking(false);
	this.setTrueFalse(false);
	this.setCountable(true);
    }

    @Override
    public void visit(Ranking ranking) {
	this.setGradable(true);
	this.setEssay(false);
	this.setMatching(true);
	this.setMultipleChoice(false);
	this.setRanking(true);
	this.setTrueFalse(false);
	this.setCountable(false);
    }

    @Override
    public void visit(ShortAnswer shortAnswer) {
	this.setGradable(true);
	this.setEssay(false);
	this.setMatching(false);
	this.setMultipleChoice(false);
	this.setRanking(false);
	this.setTrueFalse(false);
	this.setCountable(true);
    }

    @Override
    public void visit(TrueFalse trueFalse) {	
	this.setGradable(true);
	this.setEssay(false);
	this.setMatching(false);
	this.setMultipleChoice(true);
	this.setRanking(false);
	this.setTrueFalse(true);
	this.setCountable(true);
    }
}
