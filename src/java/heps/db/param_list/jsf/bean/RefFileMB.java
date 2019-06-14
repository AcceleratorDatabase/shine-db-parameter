/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.jsf.bean;

import heps.db.param_list.db.ejb.DataFacade;
import heps.db.param_list.db.ejb.HistoryDataFacade;
import heps.db.param_list.db.entity.Data;
import heps.db.param_list.db.entity.HistoryData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Lvhuihui
 */
@ManagedBean
@ApplicationScoped
public class RefFileMB implements Serializable {

    private Data data;
    private DataFacade ejbFacade;
    private StreamedContent refFile = null;
    private Boolean sign = true;
    private UploadedFile uploadFile;

    public UploadedFile getFile() {
        return uploadFile;
    }

    public void setFile(UploadedFile file) {
        this.uploadFile = file;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public RefFileMB() {
        ejbFacade = new DataFacade();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {

        this.data = data;
    }

    public void putRefFile(Data data) {
        if (data != null) {
            if (data.getAttachment() != null) {
                FileInputStream stream = null;
                try {
                    String name = data.getAttachment();
                    HttpSession session = (HttpSession) (FacesContext.getCurrentInstance().getExternalContext().getSession(true));
                    String serverPath = session.getServletContext().getRealPath("/");
                    String parentPath = new File(serverPath).getParent();
                    String rootPath = new File(parentPath).getParent();
                    String path = rootPath + "\\attachment\\" + name;
                    File file = new File(path);

                    stream = new FileInputStream(file);
                    String type = Files.probeContentType(Paths.get(path));
                    String prefix = name.substring(name.lastIndexOf("."));
                    this.refFile = new DefaultStreamedContent(stream, type, "download" + prefix);

                    //    System.out.println(file.getPath());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RefFileMB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RefFileMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void uploadRefFile(FileUploadEvent event) {
        System.out.println("-------" + this.sign);
        UploadedFile file = event.getFile();
        this.uploadFile = file;
        String fileName = file.getFileName();
        HttpSession session = (HttpSession) (FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        String serverPath = session.getServletContext().getRealPath("/");
        String parentPath = new File(serverPath).getParent();
        String rootPath = new File(parentPath).getParent();
        File realPath = new File(rootPath + "\\attachment\\");
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        File[] files = realPath.listFiles();
        Boolean isExist = false;
        for (File f : files) {
            if (file.getFileName().equals(f.getName())) {
                isExist = true;

            }
        }
        String msg = "";
        if (isExist) {
            msg = "The file with the same name has been uploaded. Do you want to replace it?";
            this.sign = false;

        } else {
            this.sign = true;
            File saveFile = new File(realPath, file.getFileName());
            byte[] buffer = file.getContents();
            //System.out.println(fileName);
            try {
                FileOutputStream outStream = new FileOutputStream(saveFile);
                outStream.write(buffer);
                outStream.close();
                data.setAttachment(fileName);
                ejbFacade.updateData(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
            msg = "upload successful";
        }
        FacesContext.getCurrentInstance().addMessage("saveRefDialog", new FacesMessage(msg));

    }

    public void confirmUpload() {
        this.sign = true;
        HttpSession session = (HttpSession) (FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        String serverPath = session.getServletContext().getRealPath("/");
        String parentPath = new File(serverPath).getParent();
        String rootPath = new File(parentPath).getParent();
        File realPath = new File(rootPath + "\\attachment\\");
        File saveFile = new File(realPath, this.uploadFile.getFileName());
        byte[] buffer = this.uploadFile.getContents();
        try {
            FileOutputStream outStream = new FileOutputStream(saveFile);
            outStream.write(buffer);
            outStream.close();
            data.setAttachment(this.uploadFile.getFileName());
            ejbFacade.updateData(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String msg = "Has been replaced with the new file.";

        FacesContext.getCurrentInstance().addMessage("saveRefDialog", new FacesMessage(msg));

    }


    public StreamedContent getRefFile() {
        if (this.refFile != null) {
            return this.refFile;
        }
        return null;
    }
}
