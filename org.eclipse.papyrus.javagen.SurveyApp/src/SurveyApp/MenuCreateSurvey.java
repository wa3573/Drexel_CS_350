// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package SurveyApp;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.*;

import SurveyApp.Menu;
import SurveyApp.Survey;
import SurveyApp.Test;

/************************************************************/
/**
 * 
 */
public class MenuCreateSurvey extends Menu {

	private String filePath;

	private Survey survey;
	private ArrayList<Question> questions;
	private Boolean isGradable;
	private Boolean isSaved;
	
	private Scanner reader = new Scanner(System.in);
	private static final Pattern ALPHANUMERIC = Pattern.compile("[^a-zA-Z0-9]");
	
	public MenuCreateSurvey() {
		MenuChoice choice1 = new MenuChoice("Add a new T/F question", 1);
		MenuChoice choice2 = new MenuChoice("Add a new multiple choice question", 2);
		MenuChoice choice3 = new MenuChoice("Add a new short answer question", 3);
		MenuChoice choice4 = new MenuChoice("Add a new essay question", 4);
		MenuChoice choice5 = new MenuChoice("Add a new ranking question", 5);
		MenuChoice choice6 = new MenuChoice("Add a new matching question", 6);
		MenuChoice choice7 = new MenuChoice("Return", 7);
		
		ArrayList<MenuChoice> choices = new ArrayList<MenuChoice>();
		
		choices.add(choice1);
		choices.add(choice2);
		choices.add(choice3);
		choices.add(choice4);
		choices.add(choice5);
		choices.add(choice6);
		choices.add(choice7);
		
		this.setChoices(choices);
		
		this.survey = new Survey();
		this.questions = new ArrayList<Question>();
		
		this.isSaved = false;
	}
	
	public void promptFilePath() {
		String userResponse = "";
		
		System.out.print("Enter filename (without extension): ");
		userResponse = reader.nextLine();
		boolean isNotAlphanumeric = ALPHANUMERIC.matcher(userResponse).find();
		
		while (isNotAlphanumeric) {
			System.out.print("Please enter only alphanumeric characters. "
					+ "Enter filename (without extension): ");
			userResponse = reader.nextLine();
			isNotAlphanumeric = ALPHANUMERIC.matcher(userResponse).find();
		}
		
		this.filePath = userResponse + ".survey";
	}
	
	public boolean promptBoolean(String prompt) {
		System.out.print(prompt + " ('y' or 'n'): ");
		char userResponse = reader.nextLine().charAt(0);
				
		while (userResponse != 'y' && userResponse != 'n') {
			System.out.print("Please enter a valid choice."
					+ prompt + " ('y' or 'n'): ");
			userResponse = reader.nextLine().charAt(0);
		}
		
		if (userResponse == 'y') {
			return true;
		} else {
			return false;
		}
	}

	public void save() {
		if (this.filePath == null) {
			this.promptFilePath();
		} else {
			String prompt = "Current path is '" + this.filePath
					+ "', keep current path?";
			
			if (!this.promptBoolean(prompt)) {
				this.promptFilePath();
			}
		}
		
		try {
			FileOutputStream file = new FileOutputStream(this.filePath);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(this.survey);
			
			out.close();
			file.close();
			
			System.out.println("File succesfully written as: " + this.filePath);
			this.isSaved = true;
			
		} catch (IOException e) {
			System.err.println("save(): IOException");
		}
		
		
	}
	
	
	public Menu selectChoice(int index) {
		Menu newMenu;
		boolean isNotAlphanumeric;
		String userResponse1 = "";
		Question newQuestion;
		Prompt newPrompt;
		CorrectResponse newResponse;
		ArrayList<CorrectResponse> newResponses;
		int numberNewResponses;
		
		
		switch (index) {
		case 1:
			/* True/False */
			System.out.println("Enter the prompt for your True/False question:");
			userResponse1 = this.reader.nextLine();
			newPrompt = new PromptString(userResponse1);
			newQuestion = new TrueFalse();
			newQuestion.setPrompt(newPrompt);
			
			this.questions.add(newQuestion);
			newMenu = this;
			break;
		case 2:
			/* Multiple Choice */
			System.out.println("Enter the prompt for your multiple choice question:");
			userResponse1 = this.reader.nextLine();
			newPrompt = new PromptString(userResponse1);
			MultipleChoice newQuestionMC = new MultipleChoice();
			newQuestionMC.setPrompt(newPrompt);
			
			newResponses = new ArrayList<CorrectResponse>();
			System.out.println("Enter the number of choices for your multiple choice question:");
			numberNewResponses = this.reader.nextInt();
			
			for (int i = 0; i < numberNewResponses; i++) {
				System.out.println("Enter Choice #" + (i + 1) + ":");
				userResponse1 = this.reader.nextLine();
				
				newResponse = new CorrectResponseString(userResponse1);
				newResponses.add(newResponse);
			}
			
			newQuestionMC.setResponsesPossible(newResponses);
			
			this.questions.add(newQuestionMC);
			newMenu = this;
			break;
		case 3:
			/* Short answer */

			System.out.println("Enter the prompt for your short answer question:");
			userResponse1 = this.reader.nextLine();
			newPrompt = new PromptString(userResponse1);
			newQuestion = new ShortAnswer();
			newQuestion.setPrompt(newPrompt);
			
			this.questions.add(newQuestion);
			newMenu = this;
			break;
		case 4:
			/* Essay */
			System.out.println("Enter the prompt for your essay question:");
			userResponse1 = this.reader.nextLine();
			newPrompt = new PromptString(userResponse1);
			newQuestion = new Essay();
			newQuestion.setPrompt(newPrompt);
			
			this.questions.add(newQuestion);
			newMenu = this;
			break;
		case 5:
			/* Ranking */
			System.out.println("Enter the prompt for your ranking question:");
			userResponse1 = this.reader.nextLine();
			newPrompt = new PromptString(userResponse1);
			Ranking newQuestionR = new Ranking();
			newQuestionR.setPrompt(newPrompt);
			
			newResponses = new ArrayList<CorrectResponse>();
			System.out.println("Enter the number of choices for your ranking question:");
			numberNewResponses = this.reader.nextInt();
			
			for (int i = 0; i < numberNewResponses; i++) {
				System.out.println("Enter Choice #" + (i + 1) + ":");
				userResponse1 = this.reader.nextLine();
				
				newResponse = new CorrectResponseString(userResponse1);
				newResponses.add(newResponse);
			}
			
			newQuestionR.setResponsesLeftSystem(newResponses);
			
			this.questions.add(newQuestionR);
			newMenu = this;
			break;
		case 6:
			/* Matching */
			System.out.println("Enter the prompt for your matching question:");
			userResponse1 = this.reader.nextLine();
			newPrompt = new PromptString(userResponse1);
			Matching newQuestionM = new Matching();
			newQuestionM.setPrompt(newPrompt);
			
			newResponses = new ArrayList<CorrectResponse>();
			System.out.println("Enter the number of choices for your matching question:");
			numberNewResponses = this.reader.nextInt();
			
			for (int i = 0; i < numberNewResponses; i++) {
				System.out.println("Enter Left Choice #" + (i + 1) + ":");
				userResponse1 = this.reader.nextLine();
				
				newResponse = new CorrectResponseString(userResponse1);
				newResponses.add(newResponse);
			}
			
			newQuestionM.setResponsesLeftSystem(newResponses);
			
			newResponses = new ArrayList<CorrectResponse>();
			
			for (int i = 0; i < numberNewResponses; i++) {
				System.out.println("Enter Right Choice #" + (i + 1) + ":");
				userResponse1 = this.reader.nextLine();
				
				newResponse = new CorrectResponseString(userResponse1);
				newResponses.add(newResponse);
			}
			
			newQuestionM.setResponsesRight(newResponses);
			
			this.questions.add(newQuestionM);
			newMenu = this;
			break;
		case 7:
			/* Return to home menu */
			newMenu = new MenuHome();
			break;
//		case 1:
//			int userResponse1 = 0;
//			newMenu = this;
//			
//			System.out.print("Enter '1' for a survey, or '2' for a test: ");
//			userResponse1 = reader.nextInt();
//			
//			while (userResponse1 != 1 && userResponse1 != 2) {
//				System.out.print("Please enter a valid response. "
//						+ "Enter '1' for a survey, or '2' for a test: ");
//				userResponse1 = reader.nextInt();
//			}
//			
//			if (userResponse1 == 0) {
//				if (this.isGradable) {
//					String prompt1 = "Work item is currently a test, all "
//							+ "questions will be removed, continue?";
//					
//					if (!this.promptBoolean(prompt1)) {
//						break;
//					}
//					
//					this.survey = new Survey();
//					this.isSaved = false;
//				}
//				
//				this.isGradable = false;
//			} else {
//				if (!this.isGradable) {
//					String prompt1 = "Work item is currently a survey, all "
//							+ "questions will be removed, continue?";
//					
//					if (!this.promptBoolean(prompt1)) {
//						break;
//					}
//					
//					this.survey = new Test();
//					this.isSaved = false;
//				}
//				
//				this.isGradable = true;
//			}
//			
//			break;
//		case 2:
//			String userResponse2 = "";
//			
//			System.out.print("Enter author name: ");
//			userResponse2 = reader.nextLine();
//			isNotAlphanumeric = ALPHANUMERIC.matcher(userResponse2).find();
//			
//			while (isNotAlphanumeric) {
//				System.out.print("Please enter only alphanumeric characters. "
//						+ "Enter author name: ");
//				userResponse2 = reader.nextLine();
//				isNotAlphanumeric = ALPHANUMERIC.matcher(userResponse2).find();
//			}
//			
//			this.survey.setAuthor(userResponse2);
//			this.isSaved = false;
//			
//			newMenu = this;
//			break;
//		case 3:
//			String userResponse3 = "";
//			
//			System.out.print("Enter title: ");
//			userResponse3 = reader.nextLine();
//			isNotAlphanumeric = ALPHANUMERIC.matcher(userResponse3).find();
//			
//			while (isNotAlphanumeric) {
//				System.out.print("Please enter only alphanumeric characters. "
//						+ "Enter title: ");
//				userResponse3 = reader.nextLine();
//				isNotAlphanumeric = ALPHANUMERIC.matcher(userResponse3).find();
//			}
//			
//			this.survey.setTitle(userResponse3);
//			this.isSaved = false;
//			
//			newMenu = this;
//			break;
//		case 4:
//			if (isGradable) {
//				newMenu = new MenuAddQuestionTest();
//			} else {
//				newMenu = new MenuAddQuestion();
//			}
//			
//			break;
//		case 5:
//			newMenu = new MenuSave();
//			break;
//		case 6:
//			String prompt1 = "The current work item is not saved, would "
//					+ "you like to save it now?";
//			
//			if (!isSaved) {
//				
//				if (this.promptBoolean(prompt1)) {
//					this.save();
//				}
//			} 
//
//			newMenu = new MenuHome();
//			break;
		default:
			newMenu = null;
		}
		
		this.survey.setQuestions(this.questions);
		
		System.out.println("Current survey: \n" + this.survey);
		
		return newMenu;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Boolean getIsGradable() {
		return isGradable;
	}

	public void setIsGradable(Boolean isGradable) {
		this.isGradable = isGradable;
	}

	public Boolean getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}
	
};
