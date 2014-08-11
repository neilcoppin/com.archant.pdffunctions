package com.archant.pdffunctions;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;

import com.appiancorp.suiteapi.process.palette.PaletteInfo; 

@PaletteInfo(paletteCategory = "Custom Services", palette = "Document Services") 
public class PdfSize extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(PdfSize.class);
	private final SmartServiceContext smartServiceCtx;
	private String filePath;
	private String width;
	private String height;

	@Override
	public void run() throws SmartServiceException {
		try {
			checkSize(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void checkSize (String filePath) throws IOException{
		PDDocument pd;
		PDPage page;
		
		
		File pdf = new File(filePath);
		System.out.println("Delivered Path: " + filePath);
		System.out.println("Actual Path: " + pdf.getPath());
		System.out.println("Last Modified: " + pdf.lastModified());
		pd = PDDocument.load(pdf);
		
		System.out.println("Number of Pages: " + pd.getNumberOfPages());
        
        page = (PDPage) pd.getDocumentCatalog().getAllPages().get(0);
        //System.out.println(page.getCOSDictionary());
        Float widthFloat = page.findMediaBox().getWidth();
        Float heightFloat = page.findMediaBox().getHeight();
        
        width = String.valueOf(widthFloat.intValue()*0.352777778);
        height = String.valueOf(heightFloat.intValue()*0.352777778);
        
         
        //System.out.println(pd.getNumberOfPages());
        //System.out.println(pd.isEncrypted());
        System.out.println("Width: " + width + "mm");
        System.out.println("Height: " + height + "mm");
	}
	
	

	public PdfSize(SmartServiceContext smartServiceCtx) {
		super();
		this.smartServiceCtx = smartServiceCtx;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.OPTIONAL)
	@Name("filePath")
	public void setFilePath(String val) {
		this.filePath = val;
	}

	@Name("width")
	public String getWidth() {
		return width;
	}

	@Name("height")
	public String getHeight() {
		return height;
	}

}
