// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package SurveyApp;

import java.util.*;
import SurveyApp.Question;

/************************************************************/
/**
 * 
 */
public class Matching extends Question {
    /**
     * 
     */
    private static final long serialVersionUID = 637344781593547142L;
    /**
     * 
     */
    private ArrayList<CorrectResponse> responsesLeftUser;
    /**
     * 
     */
    private ArrayList<CorrectResponse> responsesRight;
    /**
     * 
     */
    private int numUserResponses;
    /**
     * 
     */
    private ArrayList<CorrectResponse> responsesLeftSystem;

    /**
     * 
     */
    public Matching() {
    }

    public Matching(ArrayList<CorrectResponse> responsesLeftSystem, ArrayList<CorrectResponse> responsesRight) {
	this.responsesLeftSystem = responsesLeftSystem;
	this.responsesRight = responsesRight;
	this.numUserResponses = responsesRight.size();
    }

    /**
     * 
     * @param index1
     * @param index2
     * @return
     */
    public void swapResponsesLeftUser(int index1, int index2) {
	Collections.swap(this.responsesLeftUser, index1, index2);
    }

    /**
     * 
     * @param responses
     * @return
     */
    public void setResponsesLeftUser(ArrayList<CorrectResponse> responses) {
	this.responsesLeftUser = responses;
    }

    /**
     * 
     * @return
     */
    public ArrayList<CorrectResponse> getResponsesLeftUser() {
	return this.responsesLeftUser;
    }

    /**
     * 
     * @param responses
     * @return
     */
    public void setResponsesRight(ArrayList<CorrectResponse> responses) {
	this.responsesRight = responses;
	this.numUserResponses = responses.size();
    }

    /**
     * 
     * @return
     */
    public ArrayList<CorrectResponse> getResponsesRight() {
	return this.responsesRight;
    }

    /**
     * 
     * @param responses
     * @return
     */
    public void setResponsesLeftSystem(ArrayList<CorrectResponse> responses) {
	this.responsesLeftSystem = responses;
    }

    /**
     * 
     * @return
     */
    public ArrayList<CorrectResponse> getResponsesLeftSystem() {
	return this.responsesLeftSystem;
    }

    public int getNumUserResponses() {
	return numUserResponses;
    }

    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[Matching]\t\t" + this.getPrompt() + " : " + currentAnswer;

	str += "\n\t(Possible choices) " + this.getResponsesLeftSystem();

	if (this.getResponsesRight() != null) {
	    str += "\n\t(Corresponding matches) " + this.getResponsesRight();
	}

	return str;
    }
};