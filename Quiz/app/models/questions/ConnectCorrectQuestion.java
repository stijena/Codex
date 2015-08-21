package models.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import models.Admin;
import models.Question;
import models.enums.Grade;
import models.enums.QuestionType;
import models.enums.Subject;

/**
 * <p> Question where user has to connect a term from first column with the appropriate
 * term from the second column.</p> 
 * <p> First column can have more terms then the second column;
 * not all terms from the first column must have appropriate pair in the second column, 
 * some can be a distraction.</p>
 * 
 * @author Luka Ruklic
 *
 */

@Entity
@Table(name = "connect_correct")
@PrimaryKeyJoinColumn(name="id")
public class ConnectCorrectQuestion extends Question {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Map that contains term from one column with the term from the second column.
	 */
	@ElementCollection
	public Map<String, String> answerPairs;		// instead of map, how about a custom AnswerPairs class?

	/**
	 * Empty constructor.
	 */
	public ConnectCorrectQuestion() {
	}
	
	/**
	 * Constructor for connect correct question.
	 * 
	 * @param questionText question text
	 * @param questionType question type, for this instance it should always be CONNECT_CORRECT
	 * @param grade school grade (class) where this question content is mentioned
	 * @param subject subject where this question content is mentioned
	 * @param chapters chapters which this question references; usually equivalent to official literature chapters 
	 * @param subjectContent tags that more precisely define question content
	 * @param specialTags tags that define if this question appeared on some high school competition or national leaving exam 
	 * @param difficulty question difficulty
	 * @param explanation explanation why is the correct answer correct and/or how to solve the question
	 * @param admin administrator that added the question
	 * @param answerPairs map containing both columns with terms that have to be joined to solve the question
	 */
	public ConnectCorrectQuestion(String questionText, QuestionType questionType, Grade grade, Subject subject, String chapters,
			String subjectContent, String specialTags, int difficulty, String explanation, Admin admin, Map<String, String> answerPairs) {
		
		super(questionText, questionType, grade, subject, chapters, subjectContent, specialTags, difficulty, explanation, admin);
		this.answerPairs = answerPairs;
	}
	
	/**
	 * Method to get all first column terms (keys) of question in random order.
	 * @return list containing first column terms
	 */
	public List<String> getKeysMixed() {
		return getValues(true);
	}
	
	/**
	 * Method to get all second column terms (values) of question in random order.
	 * @return list containing second column terms
	 */
	public List<String> getValuesMixed() {
		return getValues(false);
	}
	
	private List<String> getValues(boolean areKeys) {
		List<String> elements = null;
		
		if (areKeys) {
			elements = getAllPairKeys();
		} else {
			elements = getAllPairValues();
		}
		
		long seed = System.nanoTime();
		Collections.shuffle(elements, new Random(seed));
		
		return elements;
		
	}
	
	private List<String> getAllPairKeys() {
		List<String> pairKeys = new ArrayList<>();
		pairKeys.addAll(answerPairs.keySet());
		
		for (Iterator<String> iter = pairKeys.listIterator(); iter.hasNext(); ) {
		    String key = iter.next();
		    if (key.startsWith("EMPTY_STRING")) {
		        iter.remove();
		    }
		}
		
		return pairKeys;
	}
	
	private List<String> getAllPairValues() {
		List<String> pairValues = new ArrayList<>();
		pairValues.addAll(answerPairs.values());
		return pairValues;
	}

	public Map<String, String> getAnswerPairs() {
		return answerPairs;
	}
	
}
