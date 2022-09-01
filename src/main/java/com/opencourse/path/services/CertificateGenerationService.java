package com.opencourse.path.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.google.zxing.WriterException;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateGenerationService {
    
    private final QrCodeService qrCodeService;
    private final TemplateEngine templateEngine;

    public ByteArrayOutputStream generateCertificate(Map<String,Object> data,String templateName,Long userId,String pathId) throws WriterException, IOException{
        ByteArrayOutputStream qrCode=qrCodeService.generateQrCode(userId, pathId);
        //add qrcode to data
        data.put("qrcode", Base64.getEncoder().encodeToString(qrCode.toByteArray()));
        Context context =new Context();
        context.setVariables(data);
        String htmlContent=templateEngine.process(templateName, context);
        
        ByteArrayOutputStream targetPdf=new ByteArrayOutputStream();

        PdfDocument pdfDocument=new PdfDocument(new PdfWriter(targetPdf));
        pdfDocument.setDefaultPageSize(PageSize.LETTER.rotate());

        ByteArrayInputStream inputStream=new ByteArrayInputStream(htmlContent.getBytes());
        HtmlConverter.convertToPdf(inputStream, pdfDocument);
        return targetPdf;
    }

}
