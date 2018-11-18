package io.billanderson.surveyapp;

import java.util.ArrayList;
import java.util.HashMap;
import static io.billanderson.surveyapp.Menu.DIVIDER;;

public class Tabulation {
    private Survey survey;

    private ArrayList<Question> questions;
    private int numberQuestions;

    private ArrayList<Survey> results;

    public Tabulation(Survey survey, ArrayList<Survey> results) {
	this.survey = survey;
	this.results = results;

	this.questions = survey.getQuestions();
	this.numberQuestions = this.questions.size();
    }

    public String questionToString(Question question) {
	String str = "[" + question.typeToString() + "] " + question.getPrompt();
	
	return str;
    }
    
    public void printHeader() {
	System.out.println(DIVIDER);
	System.out.println("\t\t\t\t || Tabulation ||");
	System.out.println(DIVIDER);
    }
    
    public void tabulate() {
	QuestionIdentificationVisitor identificationVisitor = new QuestionIdentificationVisitor();
	
	this.printHeader();
	
	for (int i = 0; i < numberQuestions; i++) {
	    Question currentQuestion = this.getQuestion(i);
	    currentQuestion.accept(identificationVisitor);

	    if (identificationVisitor.isCountable()) {
		HashMap<String, Integer> responseCounts = new HashMap<String, Integer>();

		for (Survey result : this.getResults()) {
		    Question currentResultQuestion = result.getQuestion(i);
		    ArrayList<CorrectResponse> resultResponses = currentResultQuestion.getResponsesUser();

		    for (CorrectResponse response : resultResponses) {
			String str = response.toString();
			int newTotal;
			if (responseCounts.containsKey(str)) {
			    newTotal = responseCounts.get(str) + 1;
			} else {
			    newTotal = 1;
			}

			responseCounts.put(str, newTotal);
		    }
		}

		System.out.println((i + 1) + ") " + this.questionToString(currentQuestion));
		for (String response : responseCounts.keySet()) {
		    String str = response + ": [" + responseCounts.get(response) + "]";
		    System.out.println(str);
		}
		
		System.out.println();
		
	    } else if (identificationVisitor.isRanking() || identificationVisitor.isMatching()) {
		HashMap<ArrayList<String>, Integer> responseCounts = new HashMap<ArrayList<String>, Integer>();

		for (Survey result : this.getResults()) {
		    Question currentResultQuestion = result.getQuestion(i);
		    ArrayList<CorrectResponse> resultResponses = currentResultQuestion.getResponsesUser();

		    ArrayList<String> rankedStrings = new ArrayList<String>();

		    for (CorrectResponse response : resultResponses) {
			String str = response.toString();
			rankedStrings.add(str);
		    }

		    int newTotal;
		    if (responseCounts.containsKey(rankedStrings)) {
			newTotal = responseCounts.get(rankedStrings) + 1;
		    } else {
			newTotal = 1;
		    }

		    responseCounts.put(rankedStrings, newTotal);

		}
		
		System.out.println((i + 1) + ") " + this.questionToString(currentQuestion));
		if (!identificationVisitor.isRanking()) {
		    System.out.println("Right side: " + ((Matching) currentQuestion).getResponsesRight());
		    System.out.println("Left side permutations:");
		} else {
		    System.out.println("Permutations:");
		}
		
		for (ArrayList<String> response : responseCounts.keySet()) {
		    String str = "[" +responseCounts.get(response) + "] " + response;
		    System.out.println(str);
		}
		
		System.out.println();
		
	    } else if (identificationVisitor.isEssay()) {
		System.out.println((i + 1) + ") " + this.questionToString(currentQuestion));
		
		for (Survey result : results) {
		    Question currentResultQuestion = result.getQuestion(i);
		    ArrayList<CorrectResponse> resultResponses = currentResultQuestion.getResponsesUser();
		    
		    for (CorrectResponse response : resultResponses) {
			System.out.println(response);
		    }
		}
		
		System.out.println();
	    }
	}
    }

    public Survey getSurvey() {
	return survey;
    }

    public void setSurvey(Survey survey) {
	this.survey = survey;
    }

    public ArrayList<Question> getQuestions() {
	return questions;
    }

    public Question getQuestion(int index) {
	return questions.get(index);
    }

    public void setQuestions(ArrayList<Question> questions) {
	this.questions = questions;
    }

    public ArrayList<Survey> getResults() {
	return results;
    }

    public void setResults(ArrayList<Survey> results) {
	this.results = results;
    }
}
