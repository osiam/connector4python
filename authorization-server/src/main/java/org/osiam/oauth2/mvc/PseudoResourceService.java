package org.osiam.oauth2.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class PseudoResourceService {

	@RequestMapping("/secured/get/resource")
    @ResponseBody
	public String getAccessConfirmation(Map<String, Object> model) throws Exception {
		return "hallo!";
	}

    @RequestMapping("/get/hach")
    @ResponseBody
    public String hach(Map<String, Object> model) throws Exception {
        return "hach!";
    }


}
