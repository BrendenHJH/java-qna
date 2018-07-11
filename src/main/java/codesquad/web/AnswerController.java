package codesquad.web;

import codesquad.domain.Answer;
import codesquad.exception.ForbiddenException;
import codesquad.repository.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.RedirectableException;
import codesquad.exception.UnauthorizedException;
import codesquad.repository.QuestionRepository;
import codesquad.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/questions/{questionId}/answers")
    @Transactional
    public String answer(@PathVariable long questionId, String contents, HttpSession session) {
        User writer = AuthenticationUtil.getUserFromSession(session).orElseThrow(UnauthorizedException::new);
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        question.addAnswer(new Answer(writer, contents));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    @Transactional
    public String delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        User user = AuthenticationUtil.getUserFromSession(session).orElseThrow(UnauthorizedException::new);
        answerRepository.delete(answerRepository.findById(answerId)
                .filter(correctAnswer -> correctAnswer.matchWriter(user))
                .orElseThrow(ForbiddenException::new));
        return "redirect:/questions/" + questionId;
    }

    @ExceptionHandler(RedirectableException.class)
    public String handleRedirectableException(RedirectableException exception) {
        return exception.getRedirectUrl();
    }
}
