/**
 * 
 */
package com.star.sud.xl.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.star.sud.xl.dto.UserDetails;
import com.star.sud.xl.service.IXLFileGenerationService;
import com.star.sud.xl.status.XLFileGenStatus;
import com.star.sud.xl.status.XLFileGenStatus.STATUS;

/**
 * @author Sudarshan
 *
 */
@Controller
public class XLFileGenerationController {

	// Static Attributes
	//////////////////////
	private static final Logger log = Logger.getLogger(XLFileGenerationController.class);

	// Attributes
	///////////////
	@Autowired
	@Qualifier("xlFileGenerationService")
	private IXLFileGenerationService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getLandingPage(Model model) {
		log.debug("getLandingPage");
		try {

			model.addAttribute("userDetails", new UserDetails());

		} catch (Exception e) {
			log.error("getLandingPage", e);
		}
		return "home";
	}

	@RequestMapping(value = "/generateXl", method = RequestMethod.POST)
	public String generateXlFile(Model model, @ModelAttribute UserDetails userDetails) {
		log.debug("generateXlFile");
		try {
			if (null == userDetails)
				throw new Exception("userDetails param is null or empty");

			XLFileGenStatus status = service.generateXlFile(userDetails);
			if (status != null && status.getStatus().equals(STATUS.SUCCESS)) {
				model.addAttribute("msgsuccess", "Successfully generated!!");
				model.addAttribute("isDisabled", Boolean.TRUE);
				model.addAttribute("filePath", status.getResult());
			} else {
				model.addAttribute("msgdanger", "Failed to generate QR code!!");
				model.addAttribute("isDisabled", Boolean.FALSE);
			}

			model.addAttribute("userDetails", userDetails);

		} catch (Exception e) {
			log.error("generateXlFile", e);
		}
		return "home";

	}

	@RequestMapping(value = "/download")
	public String downloadFile(Model model, @ModelAttribute UserDetails userDetails) {
		log.debug("downloadFile");
		try {

			if (userDetails != null && userDetails.getFilePath() != null)
				service.downloadXLFile(userDetails.getFilePath());
			else {
				model.addAttribute("msgdanger", "Failed to generate QR code!!");
				model.addAttribute("isDisabled", Boolean.FALSE);
			}
			model.addAttribute("userDetails", userDetails);

		} catch (Exception e) {
			log.error("downloadFile", e);
		}
		return "home";
	}

}
