package pl.isa.autoparts.questions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class FunctionsWeb {
    Logger logger = LoggerFactory.getLogger(FunctionsWeb.class.getName());


    protected List<Question> giveQuestionGrupWeb(List<QuestionGroup> questions, String checkValue)
            throws IOException {

        for (QuestionGroup question : questions) {
            if (question.getName().equals(checkValue)) {
                return question.getQuestions();
            }
        }
        return null;
    }

    protected List<BreakDown> giveQuestionWeb(List<Question> questions, String checkValue) throws IOException {

        for (Question question : questions) {
            if (question.getDescripton().equals(checkValue)) {
                logger.info("JEE: checkValue find correctly for tag \'breakDown\'");
                return question.getBreakDown();
            }
        }
        return null;
    }

}
