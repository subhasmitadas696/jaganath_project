package com.csmtech.SJTA.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.csmtech.SJTA.dto.LandAppResponseStructureDTO;
import com.csmtech.SJTA.repository.LandApplicantNativeRepository;
import com.csmtech.SJTA.service.CommonPdfService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class CommonPdfServiceImpl implements CommonPdfService {
	@Autowired
	private LandApplicantNativeRepository nrepo;	
	
	
	public byte[] generatePdfSample(BigInteger id) {
		
		LandAppResponseStructureDTO respones=nrepo.getCombinedDataForApplicant(id);
		
	    byte[] pdfContent = null;

	    try {
	        // Create a new Document
	        Document document = new Document();

	        // Create a ByteArrayOutputStream to hold the generated PDF content
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	        // Create a new PdfWriter
	        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

	        // Open the Document
	        document.open();

	        // Add content to the Document
//	        document.add(new Paragraph("Hello, World! This is a PDF document."));
//	        document.add(new Paragraph("My Name Is Ranjan And Rajat"));
	        
	        document.add(new Paragraph("Applicant Name:"+respones.getAppdto().getApplicantName()));
	        document.add(new Paragraph("Applicant No :"+respones.getAppdto().getApplicantNo()));
	        document.add(new Paragraph("Document uploaded :"+respones.getAppdto().getDocType()));
	        document.add(new Paragraph("State:"+respones.getAppdto().getCurrStateId()));
	        document.add(new Paragraph("District  :"+respones.getAppdto().getCurrDistrictId()));
	        document.add(new Paragraph("Block/ULB:"+respones.getAppdto().getCurrBlockId()));
	        document.add(new Paragraph("GP/Ward Number  :"+respones.getAppdto().getCurrGpId()));
	        document.add(new Paragraph("Police Station:"+respones.getAppdto().getCurrPoliceStation()));
	        document.add(new Paragraph("Post Office  :"+respones.getAppdto().getCurrPostOffice()));
	        document.add(new Paragraph("Village:"+respones.getAppdto().getCurrVillageId()));
	        document.add(new Paragraph("Habitation/Street/Land Mark  :"+respones.getAppdto().getCurrStreetNo()));
	        document.add(new Paragraph("House No:"+respones.getAppdto().getCurrHouseNo()));
	        document.add(new Paragraph("Pin Code:"+respones.getAppdto().getCurrPinCode()));
	        document.add(new Paragraph("Tehsil Name :"+respones.getAppdto().getPlotTehsilId()));
	        document.add(new Paragraph("Mouza Name :"+respones.getAppdto().getPlotMouzaId()));
	        document.add(new Paragraph("Khata No. :"+respones.getAppdto().getPlotKhataNoId()));
	        document.add(new Paragraph("Plot Number:"+respones.getAppdto().getPlotKhataNoId()));

	        // Add a border to the content
	        PdfContentByte content = writer.getDirectContent();
	        Rectangle border = new Rectangle(document.left(), document.bottom(), document.right(), document.top());
	        border.setBorder(Rectangle.BOX);
	        border.setBorderWidth(1f); // Adjust border width
	        content.rectangle(border);

	        // Add a watermark
//	        Font watermarkFont = new Font(Font.FontFamily.HELVETICA, 48, Font.BOLD, BaseColor.GREEN);
//	        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, new Phrase("Rnjan SJTA", watermarkFont), 297, 421, 45);

	        // Add a header
	        PdfContentByte headerContent = writer.getDirectContentUnder();
	        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.DARK_GRAY);
	        ColumnText.showTextAligned(headerContent, Element.ALIGN_CENTER, new Phrase("Land Applicant Details", headerFont), 297, 810, 0);

	        // Close the Document
	        document.close();

	        // Get the generated PDF content as a byte array
	        pdfContent = outputStream.toByteArray();

	        // Create the response headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDispositionFormData("attachment", "example.pdf");

	        // Return the PDF as a ResponseEntity with appropriate headers
	        return pdfContent;

	    } catch (DocumentException e) {
	        e.printStackTrace();
	    }

	    return pdfContent;
	}


}
