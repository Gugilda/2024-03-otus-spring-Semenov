package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        int questionCounter = 1;

        for (var question : questions) {
            ioService.printLine(questionCounter++ + ". " + question.text());
            int answerCounter = 1;
            for (var answer : question.answers()) {
                ioService.printLine("\t" + answerCounter++ + ". " + answer.text());
            }
            int answerIndex = ioService.readIntForRangeLocalized(1, question.answers().size(),
                    "TestService.answer.incorrect");
            var isAnswerValid = question.answers().get(answerIndex - 1).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

}
