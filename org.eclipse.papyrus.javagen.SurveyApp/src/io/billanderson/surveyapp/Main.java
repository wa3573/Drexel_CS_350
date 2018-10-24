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

import io.billanderson.surveyapp.MenuManager;
import io.billanderson.surveyapp.SurveyManager;

/************************************************************/
/**
 * - Survey and Test Utility -
 *
 * By: William J. Anderson
 * 
 * Current Version: 0.15
 * 
 * Updated: 10/24/2018
 * 
 * Provides simple capability for writing, editing, filling, saving and loading
 * surveys and tests.
 * 
 * Tested on: Java 1.8.0_161
 */
public class Main {
    /* Header divider is (80 * '~') */
    public static String DIVIDER_HEADER = "~~~~~~~~~~~~~~~~~~~~" + "~~~~~~~~~~~~~~~~~~~~" + "~~~~~~~~~~~~~~~~~~~~"
	    + "~~~~~~~~~~~~~~~~~~~~";

    public static String APPLICATION_TITLE = "Survey and Test Utility v0.15";
    public static String AUTHOR_NAME = "William J. Anderson";

    public static void printHeaderDivider() {
	System.out.println(DIVIDER_HEADER);
    }

    public static void printHeader() {
	printHeaderDivider();
	System.out.println("\t\t\t" + APPLICATION_TITLE);
	System.out.println("\t\t\tBy: " + AUTHOR_NAME);
	printHeaderDivider();
    }

    private static SurveyManager surveyManager;
    private static MenuManager menuManager;

    /**
     * 
     * @param args no functionality at the moment.
     */
    public static void main(String[] args) {
	printHeader();

	surveyManager = SurveyManager.getInstance();
	menuManager = MenuManager.getInstance();

	surveyManager.start();
	/*
	 * As this is not a threaded program, menuManager.start() needs to be the last
	 * command called directly by the main function. This is because the menu
	 * manager operates by entering a while loop which it only breaks when the user
	 * chooses to exit from the home menu. Therefore, the stack frame for
	 * menuManager.start() will remain the absolute floor of the call stack until
	 * the program is exited.
	 */
	menuManager.start();

    }
};
