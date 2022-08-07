import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static List<String> listOfSaves = new ArrayList();

    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 22, 11, 2);
        GameProgress progress2 = new GameProgress(22, 33, 44, 66);
        GameProgress progress3 = new GameProgress(123, 44, 245, 77);
        saveGame(progress1);
        saveGame(progress2);
        saveGame(progress3);
        zipFiles("C:\\Games\\savegames\\zip.zip");
        deleteFiles();

    }

    public static void saveGame(GameProgress progress) {
        String path = "C:\\Games\\savegames\\";
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmS.ddMMyy");
        Calendar calendar = new GregorianCalendar();
        String name = formatter.format(calendar.getTime());
        try (FileOutputStream fos = new FileOutputStream(path + name + ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        listOfSaves.add(path + name + ".dat");
    }

    public static void zipFiles(String path) {
        if (listOfSaves.size() == 0) {
            System.out.println("Нет файлов для архивации");
            return;
        }
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));
             FileInputStream fis = new FileInputStream(path)) {
            for (String save : listOfSaves) {
                ZipEntry entry = new ZipEntry(save);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                zout.write(buffer);
            }
            zout.closeEntry();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Архив создан");
    }

    private static void deleteFiles() {
        for (String save : listOfSaves) {
            File file1 = new File(save);
            file1.delete();
            System.out.println("Файлы удалены");
        }
    }



}



