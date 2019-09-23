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

package com.docmosis.sdk.examples;

import java.io.IOException;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.image.Image;
import com.docmosis.sdk.image.ListImagesResponse;



/**
 * 
 * This example connects to the public Docmosis cloud server and returns 
 * details about all images stored on the server.
 * 
 * How to use:
 * 
 *  1) sign up to the Docmosis Cloud Services
 *  2) plug your ACCESS_KEY into this class
 *  3) run the class and see the result
 *  
 * You can find a lot more about the Docmosis conversion capability by reading
 * the Web Services Guide and the Docmosis Template guide in the support area
 * of the Docmosis web site (http://www.docmosis.com/support) 
 *  
 */
public class SimpleListImagesExample
{
	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = "XXX";

	public static void main(String args[]) throws DocmosisException, IOException
	{
		
		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}
		
		Environment.setDefaults(ACCESS_KEY);
		
		ListImagesResponse images = Image
									.list()
									.execute();

		if (images.hasSucceeded()) {
			System.out.println(images.toString());
//			List<ImageDetails> list = images.list();
//			for(ImageDetails id : list) {
//				System.out.println(id.getName());
//				//System.out.println(id.toString());
//			}
		} else {
			// something went wrong, tell the user
			System.err.println("List Images failed: status="
					+ images.getStatus()
					+ " shortMsg="
					+ images.getShortMsg()
					+ ((images.getLongMsg() == null) ? "" : " longMsg="
							+ images.getLongMsg()));
		}
	}
}
