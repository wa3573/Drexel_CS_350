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

import java.util.*;

/************************************************************/

/**
 * MenuManager is a singleton class. When it is needed by other classes, it is
 * obtained by the method MenuManager.getInstance(), this allows the manager to
 * have singular control over the display of menus to the terminal, and to
 * direct the flow of the user interface and therefore program in general.
 */
public class MenuManager {

    private static MenuManager instance = null;

    /*
     * Since menus are purely functional and do not have memory past there life-span
     * the menu manager needs only keep track of which menu is currently active. The
     * survey manager is responsible for holding active surveys to be accessed by
     * separate menus.
     */
    private Menu menuActive;
    private Scanner reader;

    private MenuManager() {
	this.reader = new Scanner(System.in);
    }

    public static MenuManager getInstance() {
	if (instance == null) {
	    instance = new MenuManager();
	}

	return instance;
    }

    /*
     * After the instance is obtained by the main method, start() should be called
     * to begin the menu loop.
     */
    public void start() {
	Menu menuHome = new MenuHome();
	this.setMenuActive(menuHome);
	/* Print the current active menu, the Home menu */
	System.out.println("\n" + this.getMenuActive());

	/*
	 * Continue to acquire and display new menus until the next active menu is null,
	 * at which point the program is exited.
	 */
	while (this.getMenuActive() != null) {
	    this.getNewMenu();

	    if (this.getMenuActive() != null) {
		System.out.println("\n" + this.getMenuActive());
	    } else {
		System.out.println("Exitting...");
		System.exit(0);
	    }
	}
    }

    public Menu getMenuActive() {
	return menuActive;
    }

    public void setMenuActive(Menu menuActive) {
	this.menuActive = menuActive;
    }

    protected int promptForInteger(String prompt) {
	System.out.println(prompt);
	int result = 0;

	while (result == 0) {
	    try {
		result = Integer.parseInt(this.reader.nextLine());
	    } catch (NumberFormatException e) {
		// e.printStackTrace();
		System.out.println("Please enter an integer");
	    }
	}

	return result;
    }

    /*
     * Prompt user and only accept integers within the range of selections present.
     */
    private int promptForMenuInteger(String prompt) {
	int result = this.promptForInteger(prompt);
	int numChoices = this.menuActive.getNumberChoices();

	/* Make sure the user response is within the range of possible choices. */
	while (result < 1 | result > numChoices) {
	    result = this.promptForInteger("Please enter a valid selection:");
	}

	return result;
    }

    /*
     * Prompt user for a menu selection, and set the new active menu from the return
     * of the current active menu's selectChoice() method.
     */
    private void getNewMenu() {
	int userResponse = this.promptForMenuInteger("Please select a choice:");
	this.menuActive = this.menuActive.selectChoice(userResponse);
    }

};
