package contradiction.controller.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 8:19 PM
 */
public abstract class TemplateController<T> {

    private final static Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @ExceptionHandler({NotSupportedException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String handleNotSupported(Exception ex) {
        return "Operation Not Supported";
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleValidation(Exception ex) {
        return "Validation exception";
    }

    /*
    *  Error handling
    */
    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundExceptions(Exception ex) {
        return "Resource Not Found";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception ex) {
        logger.error("Exception: ", ex);
        return "Internal Server Error";
    }


    /*
     *  Controller mapping
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public T add(@Valid @RequestBody T record) throws NotSupportedException{
        throw new NotSupportedException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public T update(@PathVariable("id") String id, @RequestBody @Valid T record) throws NotSupportedException, EmptyResultDataAccessException{
        throw new NotSupportedException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public T get(@PathVariable("id") String id) throws EmptyResultDataAccessException, NotSupportedException{
        throw new NotSupportedException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void delete(@PathVariable("id") String id) throws EmptyResultDataAccessException, NotSupportedException{
        throw new NotSupportedException();
    }

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<T> getAll() throws NotSupportedException{
        throw new NotSupportedException();
    }



}
