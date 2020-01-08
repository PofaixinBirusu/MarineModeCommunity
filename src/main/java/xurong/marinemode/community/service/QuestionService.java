package xurong.marinemode.community.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xurong.marinemode.community.dto.PaginationDTO;
import xurong.marinemode.community.dto.QuestionDTO;
import xurong.marinemode.community.exception.CustomizeException;
import xurong.marinemode.community.mapper.CommentMapper;
import xurong.marinemode.community.mapper.QuestionMapper;
import xurong.marinemode.community.mapper.UserMapper;
import xurong.marinemode.community.model.Question;
import xurong.marinemode.community.utils.KeyHelper;

import java.util.*;

@Service
public class QuestionService {
    @Autowired(required=false)
    private QuestionMapper questionDao;
    @Autowired(required=false)
    private UserMapper userDao;
    @Autowired(required=false)
    private CommentMapper commentDao;
    public QuestionDTO getQuestionDTOById(int questionId){
        Question question = questionDao.findQuestionById(questionId);
        question.setCommentCount(commentDao.countCommentByQuestionID(question.getId()));
        if (question == null) {
            throw new CustomizeException(KeyHelper.QuestionNotExitMessage);
        }
        return new QuestionDTO(question, userDao.findUserById(question.getUserId()));
    }
    public int readCountInc(int questionId) {
        return questionDao.questionReadCountInc(questionId);
    }
    public Question getQuestionById(int questionId) {
        return questionDao.findQuestionById(questionId);
    }
    public int doPublish(String title, String description, String tag, int userId) {
        Question question = new Question();
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setTitle(title);
        question.setDescription(description);
        question.setLabels(tag);
        question.setUserId(userId);
        return questionDao.insertQuestion(question);
    }
    public void doUpdate(String title, String description, String tag, int questionId) {
        Question question = questionDao.findQuestionById(questionId);
        question.setGmtModified(System.currentTimeMillis());
        question.setTitle(title);
        question.setDescription(description);
        question.setLabels(tag);
        if(questionDao.updateQuestionByNewQuestion(question)!= 1) {
            throw new CustomizeException(KeyHelper.QuestionNotExitMessage);
        }
    }

    public PaginationDTO getPagination(int page, int size, String searchKey) {
        if (StringUtils.isBlank(searchKey)) {
            return getPagination(page, size);
        } else {
            List<String> searchKeys = Arrays.asList(searchKey.split(" "));
            for (int i = 0; i < searchKeys.size();) {
                if (StringUtils.isBlank(searchKeys.get(i))) {
                    searchKeys.remove(i);
                } else {
                    i++;
                }
            }
            PaginationDTO pagination =
                    new PaginationDTO(page, size, questionDao.countQuestionBySearchKeys(searchKeys));
            int offset = (pagination.getCurrentPage() - 1) * size;
            Map<String, Object> params = new HashMap<>();
            params.put(KeyHelper.SearchKeysKey, searchKeys);
            params.put(KeyHelper.OffsetKey, offset);
            params.put(KeyHelper.SizeKey, size);
            List<Question> questionList = questionDao.findQuestionBySearchKeyList(params);
            List<QuestionDTO> questionDTOList = makeQuestionDTOList(questionList);
            pagination.setQuestionList(questionDTOList);
            return pagination;
        }
    }
    public PaginationDTO getPagination(int page, int size) {
        int totleQuestionNumber = questionDao.allQuestionNumber();
        page = Math.max(page, 1);
        page = Math.min(page, (totleQuestionNumber/size)+((totleQuestionNumber%size) == 0 ? 0 : 1));
        int offset = (page - 1) * size;
        List<Question> questionList = questionDao.findQuestionsByOffsetAndSize(offset, size);
        List<QuestionDTO> questionDTOList = makeQuestionDTOList(questionList);
        PaginationDTO pagination = new PaginationDTO(page, size, totleQuestionNumber);
        pagination.setQuestionList(questionDTOList);
        return pagination;
    }
    public PaginationDTO getPagination(int page, int size, int userId) {
        PaginationDTO pagination = new PaginationDTO(page, size, questionDao.countQuestionByUserId(userId));
        int offset = (pagination.getCurrentPage() - 1) * size;
        List<Question> questionList = questionDao.findQuestionsByUserIdAndOffsetAndSize(userId, offset, size);
        List<QuestionDTO> questionDTOList = makeQuestionDTOList(questionList);
        pagination.setQuestionList(questionDTOList);
        return pagination;
    }

    private List<QuestionDTO> makeQuestionDTOList(List<Question> questionList) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questionList) {
            question.setCommentCount(commentDao.countCommentByQuestionID(question.getId()));
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(userDao.findUserById(question.getUserId()));
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public List<Question> relateQuestions(Question question) {
        List<Question> relativeQuestionList = questionDao
                .findQuestionByLabelList(Arrays.asList(question.getLabels().split("，")));
        List<Question> result = new ArrayList<>();
        if (relativeQuestionList != null) {
            /* 相关问题中不要把自己查出来 */
            for (Question relativeQuestion: relativeQuestionList)
                if (relativeQuestion.getId() != question.getId())
                    result.add(relativeQuestion);
        }
        return result;
    }
}
