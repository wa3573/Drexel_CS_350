/*******************************************************************************
 * Copyright (C) 2018 William J. Anderson
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package io.billanderson.surveyapp;

import java.util.ArrayList;

import io.billanderson.surveyapp.MenuCreateSurvey;

/************************************************************/
/**
 * TODO: implement editing menu.
 */
public class MenuEdit extends MenuCreateSurvey {

    public MenuEdit() {
	MenuChoice choice1 = new MenuChoice("Open", 1);
	MenuChoice choice2 = new MenuChoice("Set survey/test", 2);
	MenuChoice choice3 = new MenuChoice("Set author", 3);
	MenuChoice choice4 = new MenuChoice("Set title", 4);
	MenuChoice choice5 = new MenuChoice("Add question", 5);
	MenuChoice choice6 = new MenuChoice("Save", 6);
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

	Survey survey = new Survey();
	this.setSurvey(survey);
	getSurveyManager().setSaved(true);
    }

    /**
     * 
     * @param filePath
     */
    public void open(String filePath) {
    }
};
