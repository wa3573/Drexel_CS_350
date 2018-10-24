// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package SurveyApp;

import SurveyApp.Question;

/************************************************************/
/**
 * 
 */
public class Essay extends Question {
    /**
     * 
     */
    private static final long serialVersionUID = -1150954643071657406L;
    /**
     * 
     */
    private int numberResponses = 1;

    /**
     * 
     */
    public Essay() {
    }

    public String toString() {
	String currentAnswer;

	if (this.getResponsesUser().isEmpty()) {
	    currentAnswer = "";
	} else {
	    currentAnswer = this.getResponsesUser().get(0).toString();
	}

	String str = "[Essay]\t\t" + this.getPrompt() + " : ";

	if (!this.getResponsesUser().isEmpty()) {
	    str += "\n\t\"" + currentAnswer + '"';
	}

	return str;
    }
};