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
package com.docmosis.sdk.file;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Rename Files service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the FileStorage class to get an instance of this class, 
 * then set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *  RenameFileResponse renameFile = FileStorage
 *                                   .rename()
 *                                   .fromPath(oldName)
 *                                   .toPath(newName)
 *                                   .execute();
 *   if (renameFile.hasSucceeded()) {
 *       System.out.println(oldName + " renamed to " + newName);
 *  }
 * </pre>
 */
public class RenameFilesRequest extends DocmosisCloudRequest<RenameFilesRequest> {

	private static final String SERVICE_PATH = "renameFiles";
	private String fromPath;
	private String toPath;

	public RenameFilesRequest() {
		super(SERVICE_PATH);
	}

	public RenameFilesRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	/**
	 * The original name of the file or folder.
	 * @return original file name
	 */
	public String getFromPath() {
		return fromPath;
	}

	/**
	 * Set the original name of the file or folder.
	 * @param fromPath original file name
	 */
	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

	/**
	 * Set the original name of the file or folder.
	 * @param fromPath original file name
	 * @return this request for method chaining
	 */
	public RenameFilesRequest fromPath(String fromPath) {
		this.fromPath = fromPath;
		return getThis();
	}

	/**
	 * The new name for the file or folder.
	 * @return new file name
	 */
	public String getToPath() {
		return toPath;
	}

	/**
	 * Set the new name for the file or folder.
	 * @param toPath new file name
	 */
	public void setToPath(String toPath) {
		this.toPath = toPath;
	}

	/**
	 * Set the new name for the file or folder.
	 * @param toPath new file name
	 * @return this request for method chaining
	 */
	public RenameFilesRequest toPath(String toPath) {
		this.toPath = toPath;
		return getThis();
	}

	@Override
	public RenameFilesResponse execute() throws DocmosisException {
		return FileStorage.executeRenameFiles(getThis());
	}
	
	@Override
	public RenameFilesResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return FileStorage.executeRenameFiles(getThis());
	}
	
	@Override
	public RenameFilesResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return FileStorage.executeRenameFiles(getThis());
	}

	@Override
	public RenameFilesResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return FileStorage.executeRenameFiles(getThis());
	}

	@Override
	protected RenameFilesRequest getThis()
	{
		return this;
	}

	@Override
	public String toString() {
		return "RenameFileRequest [fromPath=" + fromPath + ", toPath=" + toPath + ", " + super.toString() + "]";
	}
}
