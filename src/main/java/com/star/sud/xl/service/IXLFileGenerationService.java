/**
 * 
 */
package com.star.sud.xl.service;

import com.star.sud.xl.dto.UserDetails;
import com.star.sud.xl.status.XLFileGenStatus;

/**
 * @author Sudarshan
 *
 */
public interface IXLFileGenerationService {

	/**
	 * @param userDetails
	 * @return
	 * @throws Exception
	 */
	XLFileGenStatus generateXlFile(UserDetails userDetails);

	/**
	 * @param filePath
	 */
	void downloadXLFile(String filePath);

}
