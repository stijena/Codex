package models.enums;

/**
 * Enum that defines quiz question types.
 * 
 * @author Luka Ruklic
 *
 */

public enum QuestionType {
	/**
	 * Player chooses one correct from multiple offered answers.
	 */
	MULTIPLE_CHOICE,
	/**
	 * Player chooses which anwers are correct from multiple offered answers.
	 */
	MULTIPLE_ANSWER,
	/**
	 * Player chooses if the question statement is TRUE or FALSE.
	 */
	TRUE_FALSE,
	/**
	 * Player inputs answer in given textbox.
	 */
	INPUT_ANSWER

	
}
