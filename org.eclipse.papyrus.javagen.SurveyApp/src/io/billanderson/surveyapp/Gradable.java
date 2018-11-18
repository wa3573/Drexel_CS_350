package io.billanderson.surveyapp;

import java.util.ArrayList;

/* 
 * Gradable provides a standard interface for questions which can be graded.
 * Specifically, all implementing classes should provide methods for getting
 * the system (correct) and user responses, as well as the isCorrect() method.
 */
public interface Gradable {
    public boolean isCorrect();
    public ArrayList<CorrectResponse> getResponsesSystem();
    public ArrayList<CorrectResponse> getResponsesUser();
    public void setResponsesSystem(ArrayList<CorrectResponse> responsesSystem);
    public void setResponsesUser(ArrayList<CorrectResponse> responsesUser);
}
