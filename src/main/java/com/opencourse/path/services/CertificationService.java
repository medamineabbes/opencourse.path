package com.opencourse.path.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;
import com.opencourse.path.dtos.UserInfoDto;
import com.opencourse.path.entities.Certificate;
import com.opencourse.path.entities.Path;
import com.opencourse.path.entities.Project;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.externalServices.UserService;
import com.opencourse.path.repos.CertificateRepo;
import com.opencourse.path.repos.PathRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificationService {
    
    private final CertificateRepo certificateRepo;
    private final PathRepo pathRepo;
    private final UserService userService;
    private final CertificateGenerationService genService;

    public byte[] getCertificate(String pathId,Long userId) throws WriterException, IOException{
        
        //make sure path existss
        Path path=pathRepo.findById(pathId)
        .orElseThrow(()->new PathNotFoundException(pathId));

        //make sure path is active
        if(!path.getActive())
        throw new PathNotFoundException(pathId);

        //get userInfo
        UserInfoDto userInfoDto=userService.getUserInfo(userId);
        //certificateTemplate
        String certificateTemplate="Certificate";

        //certificate details
        Map<String,Object> details=new HashMap<>();
        details.put("firstname", userInfoDto.getFirstname());
        details.put("lastname", userInfoDto.getLastname());
        details.put("dateofbirth", userInfoDto.getDateOfBirth());
        details.put("pathtitle", path.getTitle());
        
        Optional<Certificate> opCert=certificateRepo
        .findByUserIdAndPathId(userId, pathId);

        if(opCert.isPresent()){//user has certificate

            Certificate cert=opCert.get();

            //add certificate date to pdf
            details.put("date", cert.getDate().toString());
            //return bytes
        }else{

            boolean isFinished=true;
            //check if user finished all projects 
            for(int i=0;i<path.getProjects().size();i++){
                Project project=path.getProjects().get(i);
                if(
                    project.getFinished()
                    .stream()
                    .filter(finished->finished.getUserId().equals(userId))
                    .findAny()
                    .isEmpty()
                ){
                    isFinished=false;
                    break;
                }
            }
            //if user finished all projects
            if(isFinished){
                //add certificate to database
                Certificate certificate=new Certificate();
                certificate.setDate(LocalDateTime.now());
                certificate.setPath(path);
                certificate.setUserId(userId);
                certificateRepo.save(certificate);

                //add certificate date to pdf
                details.put("date", certificate.getDate().toString());
            }else{
                //add certificate date to pdf
                details.put("date", "--/--/----");
                //set certificate template to example certificate
                certificateTemplate="CertificateExample";

            }
        }
        ByteArrayOutputStream pdfStream=genService.generateCertificate(details, certificateTemplate,userId,pathId);
        return pdfStream.toByteArray();
    }

}
