package com.gorbunov.spring.Controller;

import com.gorbunov.spring.model.HumanHospitalization;
import com.gorbunov.spring.service.HospitalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by Vl on 12.11.2016.
 */
@Controller
public class HumanController {

    private HospitalizationService hospitalizationService;

    @Autowired(required=true)
    @Qualifier(value="hospitalizationService")
    public void setHospitalizationService(HospitalizationService hs){
        this.hospitalizationService = hs;
    }

    @RequestMapping(value = "/humans", method = RequestMethod.GET)
    public String listHospitalizations(Model model, @RequestParam(value = "countOfBed", required = false, defaultValue = "0") Integer countBed
                                       ,@RequestParam(value = "fileName", required = false, defaultValue = "") String fileName) {
        Map<String, List<HumanHospitalization>> mapHospitalization=hospitalizationService.mapHospitalization(countBed);
        if (mapHospitalization!=null) {
            model.addAttribute("listForHospitalization", mapHospitalization.get("People for hospitalization"));
            model.addAttribute("listForNotHospitalization", mapHospitalization.get("People for not hospitalization"));
        }
        if (!fileName.trim().isEmpty())
            model.addAttribute("fileName", fileName);
        return "human";
    }

    @RequestMapping("/human/removeAll")
    public String removeHumans(){
        this.hospitalizationService.removeAll();
        return "redirect:/humans";
    }
}

