/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id3azptecproject;

/**
 *
 * @author asus
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class ID3AzptecProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args != null) {
            File carpeta = new File("C:\\Cinch Solutions\\Cinch Audio Recorder\\music - copia");
            renameFilesInFolder(carpeta);
        } else {
            System.out.println("Favor de especificar carpeta");
        }

    }

    public static void renameFilesInFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                renameFilesInFolder(fileEntry);
            } else {

                String title = getProperty(fileEntry.getPath(), "TITLE");
                String artist = getProperty(fileEntry.getPath(), "ARTIST");
                String year = getProperty(fileEntry.getPath(), "YEAR");
                String album = getProperty(fileEntry.getPath(), "ALBUM");
                String parentFile = fileEntry.getParent();
                //  String fileName = fileEntry.getName();

                title = title.replaceAll("[\\W]", "");
                artist = artist.replaceAll("[\\W]", "");
                album = album.replaceAll("[\\W]", "");
                //  fileName = fileName.replaceAll(".mp3", "");

                String sourcePath = fileEntry.getPath();
                //String targetPath = String.format("%s\\[%s][%s.%s][%s][%s].mp3", parentFile, artist, year, album, title, fileName);
                String targetPath = String.format("%s\\%s.%s.%s.%s", parentFile, artist, year, album, title);

                //System.out.println(sourcePath);
                //System.out.println(targetPath);
                //if (fileName.matches("^Track[\\d]+$")) //System.out.println("Si pasó");
                //  {
                renameFile(sourcePath, targetPath, 1);
                //  } else {
                //      System.out.println("No se cambiará el nombre, solo nombres de archivo que coincidan con \"^(Track)[0-9]+(.mp3)$\"");
                // }
            }
        }
    }

    public static String getProperty(String path, String type) {

        try {
            MP3File mp3file = new MP3File(path);

            ID3v1 tag = mp3file.getID3v1Tag();

            if (type.equals("TITLE")) {
                return tag.getTitle();
            }
            if (type.equals("ARTIST")) {
                return tag.getArtist();
            }
            if (type.equals("YEAR")) {
                return tag.getYear();
            }
            if (type.equals("ALBUM")) {
                return tag.getAlbum();
            }

        } catch (IOException | TagException ex) {
            Logger.getLogger(ID3AzptecProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException nullPointerException) {
            //Logger.getLogger(ID3AzptecProject.class.getName()).log(Level.SEVERE, null, nullPointerException);
            return "NONE";
        }

        return "NONE";
    }

    public static void renameFile(String source, String target, int copyNumber) {

        // File fsource = new File(source);
        // File ftarget = new File(target);
        if (new File(target + ".mp3").exists()) {

            target = String.format("%s%02d", target, copyNumber);
            renameFile(source, target, ++copyNumber);
        } else if (new File(source).renameTo(new File(target + ".mp3"))) {
            System.out.println("File is moved successful!");
        } else {
            System.out.println("File is failed to move! I tried with name: " + target);
        }

    }

}
