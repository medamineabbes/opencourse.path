package com.opencourse.path.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Service
public class QrCodeService {
    
    public ByteArrayOutputStream generateQrCode(Long userId,String pathId) throws WriterException, IOException{
        String data=userId + "-" + pathId;
        int width=120;
        int height=120;
        BitMatrix matrix=new MultiFormatWriter().encode(data,BarcodeFormat.QR_CODE,height,width);
        ByteArrayOutputStream generatedImage=new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix,"png", generatedImage);
        return generatedImage;
    }
}
