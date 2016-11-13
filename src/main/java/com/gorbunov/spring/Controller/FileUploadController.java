package com.gorbunov.spring.Controller;

import com.gorbunov.spring.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.gorbunov.spring.model.FileUpload;
/**
 * Created by Vl on 06.11.2016.
 */
@Controller
public class FileUploadController {
    @Autowired
    private ImportService importService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("uploadForm") FileUpload uploadForm, Model model, BindingResult result) {
        MultipartFile multipartFile = uploadForm.getFile();
        String fileName = "";
        if (multipartFile != null) {
            fileName = multipartFile.getOriginalFilename();
            importService.importFile(uploadForm);
        }
        model.addAttribute("fileName", fileName);
        return "redirect:/humans";
    }
}
