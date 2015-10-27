package forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import cache.models.ModelCache;
import cache.models.ModelCacheType;
import engines.SpecialTagEngine;
import factories.QuestionFactory;
import models.Admin;
import models.Grade;
import models.Question;
import models.Subject;
import models.enums.QuestionType;
import models.questions.ConnectCorrectQuestion;
import models.questions.InputAnswerQuestion;
import models.questions.MultipleAnswerQuestion;
import models.questions.MultipleChoiceQuestion;
import models.questions.TrueFalseQuestion;
import play.data.validation.Constraints.Required;

/**
 * Form that stores question fields from user input and contains methods that create <code>Question</code> 
 * derivative instance from question fields.
 * 
 * @author Luka Ruklic
 *
 */

public class QuestionForm {
	/**
	 * Question id, hidden on form.
	 */
	public String id;
	/**
	 * Question text, required for every question-
	 */
	@Required
	public String questionText;
	/**
	 * Question type, required for every question.
	 */
	@Required 
	public QuestionType questionType;
	/**
	 * Question grade, required for every question.
	 */
	public Grade grade;
	/**
	 * Question subject, required for every question.
	 */
	public Subject subject;
	
	public String gradeName;
	public String subjectName;
	/**
 	 * Question difficulty from 1 to 5, required for every question.
	 */
	@Required
	public int difficulty;
	/**
	 * Chapters for question.
	 */
	public List<String>	chapters;
	/**
	 * Question content.
	 */
	public String subjectContent;
	
	/**
	 * Number of answers; relevant for MULTIPLE_CHOICE i MULTIPLE_ANSWER.
	 */
	public int numberOfAnswers;
	
	public List<String> multiple;
	
	public List<String> multipleTrue;
	
	/**
	 * Correct answer; relevant for MULTIPLE_CHOICE.
	 */
	public String multipleCorrect;
	/**
	 * List of incorrect answers; relevant for MULTIPLE_CHOICE.
	 */
	public List<String> incorrect;
	
	/**
	 * Answer for input question; relevant for INPUT_ANSWER.
	 */
	public String inputCorrect;
	/**
	 * True or false value; relevant for TRUE_FALSE.
	 */
	public String trueFalse;
	
	public List<String> termColumn1;
	
	public List<String> termColumn2;
	
	public String finalExam;
	
	public String finalsYear;
	
	public String competition;
	
	public String explanation;
	
	public String competitionYear;
	
	public String competitionLevel;
	
	public Question createQuestion(Admin admin) {
		
		this.subject = (Subject) ModelCache.getInstance().getSet(ModelCacheType.SUBJECT, subjectName);
		this.grade = (Grade) ModelCache.getInstance().getSet(ModelCacheType.GRADE, gradeName);
		
		Question question = QuestionFactory.createQuestion(this, admin);
		
		if(id != null) {
			try {
				question.id = Long.parseLong(id.replaceAll("/", ""));
			} catch (NumberFormatException ex) {
				// id is non existant, do something?
			}
		}

		return question;
	}
	
	public void fillForm(Question question) {
		
		this.questionText = question.questionText;
		this.questionType = question.questionType;
		this.grade = question.grade;
		this.subject = question.subject;
		this.subjectContent = question.subjectContent;
		this.difficulty = question.difficulty;
		this.explanation = question.explanation;
		
		SpecialTagEngine.fillQuestionForm(question, this);
		
		if (question.chapters != null) {
			List<String> chaptersList = Arrays.asList(question.chapters.split(";"));
			this.chapters = chaptersList;
		}

		switch(questionType) {
			case MULTIPLE_CHOICE:
				MultipleChoiceQuestion mq = ((MultipleChoiceQuestion)question);
				this.multipleCorrect = mq.correctAnswer;
				this.numberOfAnswers = mq.getNumberOfAnswers();
				this.incorrect = mq.getIncorrectAnswers();
				break;
			case MULTIPLE_ANSWER:
				MultipleAnswerQuestion maq = ((MultipleAnswerQuestion) question);
				
				List<String> answers = new ArrayList<>(maq.getCorrectAnswers());
				answers.removeAll(Arrays.asList("", null));
				int correctAnswersSize = answers.size();
				answers.addAll(maq.getIncorrectAnswers());
				
				this.numberOfAnswers = maq.getNumberOfAnswers();
				this.multiple = answers;
				
				List<String> multipleTrue = new ArrayList<>();
				for (int i = 0; i < correctAnswersSize; i++) {
					multipleTrue.add(String.valueOf(i));
				}
				this.multipleTrue = multipleTrue;
							
				// TODO fill form
				break;
			case TRUE_FALSE:
				if (((TrueFalseQuestion) question).answer) {
					trueFalse = "on";
				} else {
					trueFalse = "off";
				}
				break;
			case INPUT_ANSWER:
				this.inputCorrect = ((InputAnswerQuestion) question).answer;
				break;
			case CONNECT_CORRECT:
				List<String> termColumn1 = new ArrayList<>();
				List<String> termColumn2 = new ArrayList<>();
				ConnectCorrectQuestion ccq = ((ConnectCorrectQuestion) question);
				for (Entry<String, String> entry : ccq.answerPairs.entrySet()) {
					if (!entry.getKey().startsWith("EMPTY_STRING")) {
						termColumn1.add(entry.getKey());
						termColumn2.add(entry.getValue()); 
					}
				}
				
				for (Entry<String, String> entry : ccq.answerPairs.entrySet()) {
					if (entry.getKey().startsWith("EMPTY_STRING")) {
						termColumn2.add(entry.getValue()); 
					}
				}
				
				this.termColumn1 = termColumn1;
				this.termColumn2 = termColumn2;
				break;
		}
			
	}

}
