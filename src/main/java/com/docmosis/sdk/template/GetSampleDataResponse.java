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

import org.w3c.dom.Document;

import com.docmosis.sdk.response.DocmosisCloudResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class GetSampleDataResponse extends DocmosisCloudResponse {

	private JsonObject sampleDataJson = null;
	private Document sampleDataXml = null;
	private boolean isJson;
	
	public GetSampleDataResponse() {
		super();
	}

	public JsonObject getSampleDataJson() {
		return sampleDataJson;
	}

	public void setSampleDataJson(JsonObject sampleDataJson) {
		this.sampleDataJson = sampleDataJson;
	}

	public Document getSampleDataXml() {
		return sampleDataXml;
	}

	public void setSampleDataXml(Document sampleDataXml) {
		this.sampleDataXml = sampleDataXml;
	}

	public boolean isJson() {
		return isJson;
	}

	public void setJson(boolean isJson) {
		this.isJson = isJson;
	}

	@Override
	public String toString() {
		if (isJson()) { //Build formatted Json String to return.
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			return gson.toJson(sampleDataJson);
		}
		else { //Build formatted xml String to return.
			return getAsXMLPretty(sampleDataXml);
		}
	}
}
