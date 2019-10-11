package com.docmosis.sdk;

import java.io.File;

import org.junit.Test;

import com.docmosis.sdk.convert.Converter;
import com.docmosis.sdk.convert.ConverterException;
import com.docmosis.sdk.convert.ConverterResponse;
import com.docmosis.sdk.environment.Endpoint;
import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.file.DeleteFilesResponse;
import com.docmosis.sdk.file.FileException;
import com.docmosis.sdk.file.FileStorage;
import com.docmosis.sdk.file.GetFileResponse;
import com.docmosis.sdk.file.ListFilesResponse;
import com.docmosis.sdk.file.PutFileResponse;
import com.docmosis.sdk.file.RenameFilesResponse;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.image.DeleteImageResponse;
import com.docmosis.sdk.image.GetImageResponse;
import com.docmosis.sdk.image.Image;
import com.docmosis.sdk.image.ImageException;
import com.docmosis.sdk.image.ListImagesResponse;
import com.docmosis.sdk.image.UploadImageResponse;
import com.docmosis.sdk.ping.Ping;
import com.docmosis.sdk.render.RenderResponse;
import com.docmosis.sdk.render.Renderer;
import com.docmosis.sdk.render.RendererException;
import com.docmosis.sdk.rendertags.GetRenderTagsResponse;
import com.docmosis.sdk.rendertags.RenderTags;
import com.docmosis.sdk.rendertags.RenderTagsException;
import com.docmosis.sdk.template.DeleteTemplateResponse;
import com.docmosis.sdk.template.GetSampleDataResponse;
import com.docmosis.sdk.template.GetTemplateDetailsResponse;
import com.docmosis.sdk.template.GetTemplateResponse;
import com.docmosis.sdk.template.GetTemplateStructureResponse;
import com.docmosis.sdk.template.ListTemplatesResponse;
import com.docmosis.sdk.template.Template;
import com.docmosis.sdk.template.TemplateException;
import com.docmosis.sdk.template.UploadTemplateResponse;

import junit.framework.TestCase;

public class TestAll extends TestCase {
	
	private static final String DEFAULT_TEMPLATE_NAME = "samples/WelcomeTemplate.docx";
	private static final String ACCESS_KEY = "XXX";
	private static final String FILE_TO_UPLOAD = "src/test/java/testFiles/myTemplateFile.docx";
	private static final String FILE_TO_UPLOAD2 = "src/test/java/testFiles/myTemplateFile2.docx";
	private static final String FILE_GET = "myTemplateFile.docx";
	private static final String FILE_NAME = "myTemplateFile.docx";
	private static final String FILE_NAME2 = "myTemplateFile2.docx";
	private static final String IMAGE_TO_UPLOAD = "src/test/java/testFiles/Image1.png";
	private static final String IMAGE_TO_UPLOAD2 = "src/test/java/testFiles/Image2.jpg";
	private static final String IMAGE_NAME = "Image1.png";
	private static final String IMAGE_NAME2 = "Image2.jpg";
	private static final String FILE_RENAME1 = "myDocument.docx";
	private static final String FILE_RENAME2 = "myDocument2.docx";
	
	private static final String NONEXISTENT_FILE_TO_UPLOAD = "src/test/java/testFiles/myNonExistentTemplateFile.docx";
	private static final String NONEXISTENT_FILE_NAME = "myNonExistentTemplateFile.docx";
	private static final String NONEXISTENT_IMAGE_TO_UPLOAD = "src/test/java/testFiles/myNonExistentImage1.png";
	private static final String NONEXISTENT_IMAGE_NAME = "myNonExistentImage1.png";
	
	//Send returned files here
	private static final String OUT = "src/test/java/testFiles/output/Out";

	public TestAll( String testName )
    {
        super( testName );
        Environment.setDefaults(Endpoint.DWS_VERSION_3_AUS, ACCESS_KEY);
    }

/*
  ******************
  * Renderer Tests *
  ******************
*/

	@Test
	public void testPing() {
		try {
			assertTrue(Ping.execute());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testRender() {
		try {
			File outputFile = new File(OUT);
			RenderResponse rsp = Renderer.render().templateName(DEFAULT_TEMPLATE_NAME).outputName(OUT).outputFormat("pdf").sendTo(outputFile)
					.data("{\"title\":\"This is Docmosis Cloud\\nTue Aug 27 16:10:12 AWST 2019\",\"messages\":[{\"msg\":\"This cloud experience is better than I thought.\"},{\"msg\":\"The sun is shining.\"},{\"msg\":\"Right, now back to work.\"}]}")
					.execute();
			assertTrue(rsp.getShortMsg(), rsp.hasSucceeded());
			rsp = Renderer.render().templateName(DEFAULT_TEMPLATE_NAME).outputName(OUT).outputFormat("pdf").sendTo(outputFile)
					.data("<title>This is Docmosis Cloud\r\n" + 
							"Tue Aug 27 16:11:54 AWST 2019</title>")
					.execute();
			assertTrue(rsp.getShortMsg(), rsp.hasSucceeded());
			rsp = Renderer.render().templateName(NONEXISTENT_FILE_NAME).outputName(OUT).outputFormat("pdf").sendTo(outputFile)
					.data("{\"title\":\"This is Docmosis Cloud\\nTue Aug 27 16:10:12 AWST 2019\",\"messages\":[{\"msg\":\"This cloud experience is better than I thought.\"},{\"msg\":\"The sun is shining.\"},{\"msg\":\"Right, now back to work.\"}]}")
					.execute();
			assertFalse(rsp.getShortMsg(), rsp.hasSucceeded());
		} catch (Exception e) {
			//e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testRenderForm() {
		try {
			File outputFile = new File(OUT + "5");
			RenderResponse rsp = Renderer.renderForm().templateName(DEFAULT_TEMPLATE_NAME).outputName(OUT + "5").outputFormat("pdf").sendTo(outputFile)
					.data("title","This is Docmosis Cloud")
					.execute();
			assertTrue(rsp.getShortMsg(), rsp.hasSucceeded());
			rsp = Renderer.renderForm().templateName(NONEXISTENT_FILE_NAME).outputName(OUT + "5").outputFormat("pdf").sendTo(outputFile)
					.data("title","This is Docmosis Cloud")
					.execute();
			assertFalse(rsp.getShortMsg(), rsp.hasSucceeded());
		} catch (Exception e) {
			//e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testRenderMissingParams() {
		try {
			Renderer.render().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof RendererException);
		}
	}
	
	@Test
	public void testRenderFormMissingParams() {
		try {
			Renderer.renderForm().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof RendererException);
		}
	}

	/*
	  *********************
	  * Render Tags Tests *
	  *********************
	*/
	
	@Test
	public void testGetRenderTags() {
		try {
			GetRenderTagsResponse rsp = RenderTags.get().tags("test").padBlanks(true).execute();
			assertTrue(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetRenderTagsMissingParams() {
		try {
			RenderTags.get().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof RenderTagsException);
		}
	}

/*
  *****************
  * Convert Tests *
  *****************
*/


	@Test
	public void testConvert() {
		try {
			File convertFile = new File(FILE_TO_UPLOAD);
			File outputFile = new File(OUT + "1");
			ConverterResponse rsp = Converter.convert().fileToConvert(convertFile).outputName(OUT + "1" + ".pdf").sendTo(outputFile).execute();
			assertTrue(rsp.hasSucceeded());
			try {
				convertFile = new File(NONEXISTENT_FILE_NAME);
				rsp = Converter.convert().fileToConvert(convertFile).outputName(OUT + "1" + ".pdf").sendTo(outputFile).execute();
				assertFalse(rsp.hasSucceeded());
			} catch (Exception e1) {
				assertTrue(e1 instanceof ConverterException);
			}
		} catch (Exception e2) {
			fail();
		}
	}
	
	@Test
	public void testConverterMissingParams() {
		try {
			Converter.convert().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof ConverterException);
		}
	}


/*
  ******************
  * Template Tests *
  ******************
*/

	@Test
	public void testListTemplates() {
		try {
			ListTemplatesResponse rsp = Template.list().execute();
			assertTrue(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUploadDeleteTemplate() {
		try {
			File uploadFile = new File(FILE_TO_UPLOAD);
			UploadTemplateResponse rsp = Template.upload().templateFile(uploadFile).execute();
			assertTrue(rsp.hasSucceeded());
			
			DeleteTemplateResponse drsp = Template.delete().templateName(FILE_NAME).execute();
			assertTrue(drsp.hasSucceeded());
			try {
				uploadFile = new File(NONEXISTENT_FILE_TO_UPLOAD);
				rsp = Template.upload().templateFile(uploadFile).execute();
				fail();
			} catch (Exception e1) {
				assertTrue(e1 instanceof DocmosisException);
			}
		} catch (Exception e2) {
			fail();
		}
	}
	
	@Test
	public void testGetTemplateStructure() {
		try {
			GetTemplateStructureResponse rsp = Template.getStructure().templateName(DEFAULT_TEMPLATE_NAME).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getStructure().templateName(NONEXISTENT_FILE_NAME).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetTemplateDetails() {
		try {
			GetTemplateDetailsResponse rsp =  Template.getDetails().templateName(DEFAULT_TEMPLATE_NAME).execute();
			assertTrue(rsp.hasSucceeded());
			rsp =  Template.getDetails().templateName(NONEXISTENT_FILE_NAME).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetSampleData() {
		try {
			GetSampleDataResponse rsp = Template.getSampleData().templateName(DEFAULT_TEMPLATE_NAME).format("json").execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getSampleData().templateName(DEFAULT_TEMPLATE_NAME).format("xml").execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getSampleData().templateName(DEFAULT_TEMPLATE_NAME).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.getSampleData().templateName(NONEXISTENT_FILE_NAME).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetTemplate() {
		try {
			File outputFile = new File(OUT + "2");
			GetTemplateResponse rsp = Template.get().templateName(DEFAULT_TEMPLATE_NAME).sendTo(outputFile).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Template.get().templateName(NONEXISTENT_FILE_NAME).sendTo(outputFile).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testUploadTemplateMissingParams() {
		try {
			Template.upload().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof TemplateException);
		}
	}

	@Test
	public void testDeleteTemplateMissingParams() {
		try {
			Template.delete().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof TemplateException);
		}
	}

	@Test
	public void testGetTemplateStructureMissingParams() {
		try {
			Template.getStructure().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof TemplateException);
		}
	}

	@Test
	public void testGetTemplateDetailsMissingParams() {
		try {
			Template.getDetails().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof TemplateException);
		}
	}

	@Test
	public void testGetSampleDataMissingParams() {
		try {
			Template.getSampleData().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof TemplateException);
		}
	}

	@Test
	public void testGetTemplateMissingParams() {
		try {
			Template.get().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof TemplateException);
		}
	}

/*
  ***************
  * Image Tests *
  ***************
*/

	@Test
	public void testListImages() {
		try {
			ListImagesResponse rsp = Image.list().execute();
			assertTrue(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUploadDeleteImage() {
		try {
			File uploadFile = new File(IMAGE_TO_UPLOAD);
			UploadImageResponse rsp = Image.upload().imageFile(uploadFile).execute();
			assertTrue(rsp.hasSucceeded());
			DeleteImageResponse drsp = Image.delete().imageName(IMAGE_NAME).execute();
			assertTrue(drsp.hasSucceeded());
			try {
				uploadFile = new File(NONEXISTENT_IMAGE_TO_UPLOAD);
				rsp = Image.upload().imageFile(uploadFile).execute();
				fail();
			} catch (Exception e1) {
				assertTrue(e1 instanceof DocmosisException);
			}
		} catch (Exception e2) {
			fail();
		}
	}
	
	@Test
	public void testGetImage() {
		try {
			File uploadFile = new File(IMAGE_TO_UPLOAD2);
			UploadImageResponse ursp = Image.upload().imageFile(uploadFile).execute();
			assertTrue(ursp.hasSucceeded());
			File outputFile = new File(OUT + "3");
			GetImageResponse rsp = Image.get().imageName(IMAGE_NAME2).sendTo(outputFile).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = Image.get().imageName(NONEXISTENT_IMAGE_NAME).sendTo(outputFile).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUploadImageMissingParams() {
		try {
			Image.upload().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof ImageException);
		}
	}
	
	@Test
	public void testDeleteImageMissingParams() {
		try {
			Image.delete().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof ImageException);
		}
	}
	
	@Test
	public void testGetImageMissingParams() {
		try {
			Image.get().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof ImageException);
		}
	}

/*
  **************
  * File Tests *
  **************
*/

	@Test
	public void testListFiles() {
		try {
			ListFilesResponse rsp = FileStorage.list().execute();
			assertTrue(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testPutDeleteFile() {
		try {
			File uploadFile = new File(FILE_TO_UPLOAD2);
			PutFileResponse rsp = FileStorage.put().file(uploadFile).metaData("Test").execute();
			assertTrue(rsp.hasSucceeded());
			DeleteFilesResponse drsp = FileStorage.delete().path(FILE_NAME2).execute();
			assertTrue(drsp.hasSucceeded());
		
			try {
				uploadFile = new File(NONEXISTENT_FILE_TO_UPLOAD);
				rsp = FileStorage.put().file(uploadFile).metaData("Test").execute();
				fail();
			} catch (Exception e1) {
				assertTrue(e1 instanceof DocmosisException);
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testFileRename() {
		try {
			RenameFilesResponse rsp = FileStorage.rename().fromPath(FILE_RENAME1).toPath(FILE_RENAME2).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = FileStorage.rename().fromPath(FILE_RENAME2).toPath(FILE_RENAME1).execute();
			assertTrue(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetFile() {
		try {
			File uploadFile = new File(FILE_TO_UPLOAD);
			PutFileResponse ursp = FileStorage.put().file(uploadFile).metaData("Test").execute();
			assertTrue(ursp.hasSucceeded());
			File outputFile = new File(OUT + "4");
			GetFileResponse rsp = FileStorage.get().fileName(FILE_GET).sendTo(outputFile).execute();
			assertTrue(rsp.hasSucceeded());
			rsp = FileStorage.get().fileName(NONEXISTENT_FILE_NAME).sendTo(outputFile).execute();
			assertFalse(rsp.hasSucceeded());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testPutFileMissingParams() {
		try {
			FileStorage.put().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof FileException);
		}
	}

	@Test
	public void testDeleteFileMissingParams() {
		try {
			FileStorage.delete().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof FileException);
		}
	}

	@Test
	public void testRenameFileMissingParams() {
		try {
			FileStorage.rename().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof FileException);
		}
	}

	@Test
	public void testGetFileMissingParams() {
		try {
			FileStorage.get().execute();
			fail();
		} catch (Exception e1) {
			assertTrue(e1 instanceof FileException);
		}
	}
}
