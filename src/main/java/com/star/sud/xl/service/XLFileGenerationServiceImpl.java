/**
 * 
 */
package com.star.sud.xl.service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.sud.xl.dto.UserDetails;
import com.star.sud.xl.status.XLFileGenStatus;
import com.star.sud.xl.status.XLFileGenStatus.STATUS;
import com.star.sud.xl.util.XLFileUtil;

/**
 * @author Sudarshan
 *
 */
@Service("xlFileGenerationService")
public class XLFileGenerationServiceImpl implements IXLFileGenerationService {

	// Static Attributes
	//////////////////////
	private static Logger log = Logger.getLogger(XLFileGenerationServiceImpl.class);

	// Attributes
	//////////////////////
	@Autowired
	protected HttpServletResponse response;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.star.sud.xl.service.IXLFileGenerationService#generateXlFile(com.star.sud.
	 * xl.dto.UserDetails)
	 */
	public XLFileGenStatus generateXlFile(UserDetails userDetails) {
		log.debug("generateXlFile");
		XLFileGenStatus genStatus = new XLFileGenStatus();
		try {

			String[] columns = { "ID", "First Name", "Last Name", "Creation Date", "Contact", "Age", "Address" };

			List<UserDetails> userDetails2 = new ArrayList<UserDetails>();
			userDetails2.add(userDetails);

			String filepath = XLFileUtil.writeExcelFile(columns, userDetails2);

			genStatus.setStatus(STATUS.SUCCESS);
			genStatus.setResult(filepath);

		} catch (Exception e) {
			log.error("generateXlFile", e);
			genStatus.setStatus(STATUS.FAILED);
		}

		return genStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.star.sud.xl.service.IXLFileGenerationService#downloadXLFile(java.lang.
	 * String)
	 */
	public void downloadXLFile(String filePath) {

		ServletOutputStream os = null;
		try {

			if (null == filePath)
				throw new Exception("filePath para is null or empty");

			File file = new File(filePath);

			os = response.getOutputStream();

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName() + ".xlsx");

			byte[] imgByArry = Files.readAllBytes(file.toPath());
			os.write(imgByArry);

		} catch (Exception e) {
			log.error("downloadCsvFile", e);
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (Exception ex) {
					log.error("error closing output stream");
				}
			}

		}
	}

}
