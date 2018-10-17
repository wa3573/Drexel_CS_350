// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package SurveyApp;

import java.util.*;
import SurveyApp.CorrectResponse;
import SurveyApp.Output;
import SurveyApp.Prompt;

/************************************************************/
/**
 * 
 */
public abstract class Question {
	/**
	 * 
	 */
	private Prompt prompt;
	/**
	 * 
	 */
	private ArrayList<CorrectResponse> responsesUser;
	/**
	 * 
	 */
	private int numberResponses = 1;

	/**
	 * 
	 */
	public Question() {
	}

	/**
	 * 
	 * @return 
	 * @param prompt 
	 */
	public void setPrompt(Prompt prompt) {
		this.prompt = prompt;
	}

	/**
	 * 
	 * @return 
	 */
	public Prompt getPrompt() {
		return this.prompt;
	}

	/**
	 * 
	 * @return 
	 */
	public int getNumberResponsesUser() {
		return this.numberResponses;
	}

	/**
	 * 
	 * @return 
	 * @param index 
	 * @param response 
	 */
	public void setResponseUser(int index, CorrectResponse response) {
		this.responsesUser.set(index, response);
	}

	/**
	 * 
	 * @return 
	 * @param index 
	 */
	public CorrectResponse getResponseUser(int index) {
		return this.responsesUser.get(index);
	}

	/**
	 * 
	 * @return 
	 * @param responses 
	 */
	public void setResponsesUser(ArrayList<CorrectResponse> responses) {
		this.responsesUser = responses;
	}

	/**
	 * 
	 * @return 
	 */
	public ArrayList<CorrectResponse> getResponsesUser() {
		return this.responsesUser;
	}

	/**
	 * 
	 * @return 
	 * @param output 
	 */
	public void display(Output output) {
		output.displayString(this.prompt.toString());
	}
};
