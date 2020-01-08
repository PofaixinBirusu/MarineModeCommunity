package xurong.marinemode.community.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xurong.marinemode.community.exception.CustomizeException;
import xurong.marinemode.community.utils.KeyHelper;

import javax.servlet.http.HttpServletRequest;

/* 所有的Controller异常都会走handle经过，这样异常就不会满控制台的瞎鸡吧乱飞了 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        if (ex instanceof CustomizeException) {
            model.addAttribute(KeyHelper.ErrorMessageKey, ex.getMessage());
        } else {
            model.addAttribute(KeyHelper.ErrorMessageKey, KeyHelper.UnknowExceptionMessage);
            ex.printStackTrace();
        }
        return new ModelAndView(KeyHelper.ErrorPageKey);
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
