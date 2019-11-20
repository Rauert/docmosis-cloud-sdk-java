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
package com.docmosis.sdk.render;

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a render request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
 * 
 * <pre>
 *   RenderResponse response = Renderer
 *                              .render()
 *                              .templateName(TemplateName)
 *                              .outputName(outputFileName)
 *                              .sendTo(outputFileOrStream)
 *                              .data(dataString)
 *                              .execute();
 *   if (response.hasSucceeded()) {
 *       //File rendered and saved to outputFileOrStream
 *	 }
 *   ...
 * </pre>
 */
public class RenderResponse extends DocmosisCloudResponse
{
	protected String requestId;
	protected int pagesRendered;
	
	protected RenderResponse() {
		super();
	}
	
	protected RenderResponse(RenderResponse other) {
		super(other);
		this.requestId = other.requestId;
		this.pagesRendered = other.pagesRendered;
	}

	/**
	 * If the requestId was set in the render, it will be returned in this response.
	 * This helps asynchronous processing determine which response relates to which request.
	 *  
	 * @return null if not set
	 */
	public String getRequestId()
	{
		return requestId;
	}
	
	/**
	 * Get the number of pages rendered on success.
	 * 
	 * @return 0 if unknown
	 */
	public int getPagesRendered()
	{
		return pagesRendered;
	}
}
