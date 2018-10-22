// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package SurveyApp;

import java.util.*;

/************************************************************/

/**
 * MenuManager is a singleton class. When it is needed by other classes, it is
 * obtained by the method MenuManager.getInstance(), this allows the manager to
 * have singular control over the display of menus to the terminal, and to
 * direct the flow of the user interface.
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
    private Scanner reader = new Scanner(System.in);

    /**
     * 
     */
    private MenuManager() {
	Menu menuHome = new MenuHome();
	this.setMenuActive(menuHome);
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

    public static MenuManager getInstance() {
	if (instance == null) {
	    instance = new MenuManager();
	}

	return instance;
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

	while (result < 1 | result > numChoices) {
	    result = this.promptForInteger("Please enter a valid selection:");
	}

	return result;
    }

    private void getNewMenu() {
	int userResponse = this.promptForMenuInteger("Please select a choice:");
	this.menuActive = this.menuActive.selectChoice(userResponse);
    }

};
