/*
 *   Copyright 2019 Docmosis.com or its affiliates.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *   or in the LICENSE file accompanying this file.
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.docmosis.sdk.template;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Get Sample Data service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetSampleDataResponse templateSampleData = Template.getSampleData()
 *   												.url("https://dws2.docmosis.com/services/rs/getSampleData")
 *   												.templateName("MasterTemplates/MyMasterTemplate.docx")
 *   												.format("json")
 *   												.accessKey("XXX")
 *   												.execute();
 *   templateSampleData.toString();
 * </pre>
 */
public class GetSampleDataRequest extends DocmosisCloudRequest<GetSampleDataRequest> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	private String templateName;
	private String format = null;

	public GetSampleDataRequest() {
		super(GetSampleDataRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs/getSampleData"); //Default url
	}
	
	public GetSampleDataRequest(String url) {
		super(GetSampleDataRequest.class, url);
	}

	public GetSampleDataRequest(String url, String accessKey) {
		super(GetSampleDataRequest.class, url, accessKey);
	}

	/**
	 * Get the currently set templateName.
	 * 
	 * @return The name of the Template on the docmosis server.
	 */
	public String getTemplateName() {
		return templateName;
	}
	
	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public GetSampleDataRequest templateName(String templateName) {
		this.templateName = templateName;
		return self;
	}

	/**
	 * returns true if the docmosis server will return the Sample Data in json format.
	 */
	public boolean isFormatJson() {
		return (format == null || format == "" || format.equalsIgnoreCase("json"));
	}
	
	public String getFormat() {
		return format;
	}

	/**
	 * Set the format of the Sample Data to be returned.
	 * 
	 * @param format format of the response, should be either "json" or "xml". Defaults to json.
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Set the format of the Sample Data to be returned.
	 * 
	 * @param format format of the response, should be either "json" or "xml". Defaults to json.
	 */
	public GetSampleDataRequest format(String format) {
		this.format = format;
		return self;
	}
	
	@Override
	public String toString() {
		return "GetSampleDataRequest [" + super.toString() + ", templateName=" + templateName + ", format=" + format + "]";
	}

	@Override
	public GetSampleDataResponse execute() throws DocmosisException {
		return Template.executeGetSampleData(self);
	}
	
	@Override
	public GetSampleDataResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Template.executeGetSampleData(self);
	}
	
	@Override
	public GetSampleDataResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return Template.executeGetSampleData(self);
	}
}
