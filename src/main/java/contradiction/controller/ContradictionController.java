package contradiction.controller;

import api.controller.dto.OpinionsDTO;
import api.model.Opinion;
import api.model.Contradiction;

import java.util.ArrayList;
import java.util.List;

import contradiction.client.ContradictionClient;
import contradiction.controller.template.TemplateController;
import contradiction.service.ContradictionDetectionService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/21/13
 * Time: 10:43 AM
 */
@Controller
@RequestMapping("/contradiction/opinion")
public class ContradictionController extends TemplateController<Opinion> {

    @Autowired
    ContradictionDetectionService contradictionDetectionService;

    @Autowired
    ContradictionClient contradictionClient;

    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Contradiction> getContradictingOpinions(@Valid @RequestBody OpinionsDTO opinions) {

        List<Contradiction> allContradictions = new ArrayList<Contradiction>();

        for (Opinion opinion: opinions) {
            List<Contradiction> contradictions = contradictionDetectionService.findContradictionsForOpinion(opinion);
            allContradictions = ListUtils.union(allContradictions,contradictions);
        }

        return allContradictions;
    }




}
