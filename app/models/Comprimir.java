package models;

import com.avaje.ebean.Ebean;

import java.io.*;

import java.util.zip.*;

/**
 * Created by tharyckVasconcelos on 03/10/16.
 */
public class Comprimir {

    private ZipOutputStream zip;
    private GZIPOutputStream gzip;

    public GZIPOutputStream getGzip() {
        return gzip;
    }

    public ZipOutputStream getZip() {
        return zip;
    }

    public void setZip(String id){
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        Arquivo arqTemp = new Arquivo();
        String texto = arq.getContent();
        ZipOutputStream out = null;

        try {

            arqTemp.setExtension("ZIP");

            FileOutputStream fileOut = new FileOutputStream(arqTemp.getExtension());
            out = new ZipOutputStream(fileOut);

            out.putNextEntry( new ZipEntry(arq.getContent()) );
            out.write( texto.getBytes() );
            out.closeEntry();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        zip = out;
    }

    public void setGzip(String id){
        Arquivo arq = Ebean.createQuery(Arquivo.class).where().idEq(id).findUnique();
        Arquivo arqTemp = new Arquivo();
        String texto = arq.getContent();
        GZIPOutputStream out = null;

        try {

            arqTemp.setExtension("ZIP");

            FileOutputStream fileOut = new FileOutputStream(arqTemp.getExtension());
            out = new GZIPOutputStream(fileOut);

            out.write( texto.getBytes() );
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        gzip = out;
    }
}