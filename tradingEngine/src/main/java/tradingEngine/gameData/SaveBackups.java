package main.java.tradingEngine.gameData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility class use for backing up a save file upon loading it. It also permits to delete old unused backups.
 * @author Julien Ait azzouzene
 * @see SaveFile
 */
public abstract class SaveBackups {
    private static final String BACKUP_DIRECTORY = "./backups/";
    private static final Path BACKUP_PATH = Paths.get(BACKUP_DIRECTORY);

    /**
     * Backups a save file only if there isn't any file with identical name, checksum and trainer id.
     * @param saveFile the save file that has to be backed up
     * @throws IOException if any problem relating to the file system is encountered
     * (like no permissions, or if a file named "backups" already exists)
     */
    public static void backup(SaveFile saveFile) throws IOException {
        if(! Files.exists(BACKUP_PATH)){
            firstBackup(saveFile);
            return;
        }

        if(! (new File(BACKUP_DIRECTORY)).isDirectory()){
            throw new IOException("there is already a file named \"backups\" in the application folder");
        }

        if(isAlreadyBackedUp(saveFile)){
            return;
        }

        copySaveFile(saveFile, getCopyFileName(saveFile));
    }

    /**
     * Deletes all backups files in the "backups" folder that are older than 7 days.
     * Keeps one file if there is no recent file for a particular save file.
     */
    public static void deleteOldBackups(){
        if(! Files.exists(BACKUP_PATH) || ! (new File(BACKUP_DIRECTORY).isDirectory())){
            return;
        }

        File[] backupFiles = (new File(BACKUP_DIRECTORY)).listFiles();
        if(backupFiles == null){
            throw new RuntimeException("backup directory is not a directory");
        }
        HashMap<String, ArrayList<File>> saveFilesGroups = new HashMap<>();

        for(File file : backupFiles){
            int bracketIndex = file.getName().lastIndexOf('[');
            if(bracketIndex == -1){
                continue;
            }
            String saveName = file.getName().substring(0, bracketIndex);
            String trainerId = file.getName().substring(bracketIndex+ 1, bracketIndex + 26).split("-")[0];

            String key = saveName + trainerId;
            if(! saveFilesGroups.containsKey(key)){
                saveFilesGroups.put(key, new ArrayList<>());
            }
            saveFilesGroups.get(key).add(file);
        }

        for(String key : saveFilesGroups.keySet()){
            ArrayList<File> files = saveFilesGroups.get(key);
            if(containsRecentSave(files)){
                deleteAllOldSaves(files);
            }
            else{
                deleteAllButMostRecent(files);
            }
        }
    }

    private static void firstBackup(SaveFile saveFile) throws IOException {
        Files.createDirectory(BACKUP_PATH);
        copySaveFile(saveFile, getCopyFileName(saveFile));
    }

    private static String getCopyFileName(SaveFile saveFile){
        String baseFilePath = saveFile.getPath();
        String baseFileName = baseFilePath.substring(baseFilePath.lastIndexOf('/') + 1);

        int extensionIndex = baseFileName.lastIndexOf('.');
        String extension = baseFileName.substring(extensionIndex);
        String fileNameWithoutExtension = baseFileName.substring(0, extensionIndex);

        LocalDateTime currentTimeO = LocalDateTime.now();
        String currentTimeS = String.format("%04d%02d%02d-%02d%02d%02d",
                currentTimeO.getYear(), currentTimeO.getMonthValue(), currentTimeO.getDayOfMonth(),
                currentTimeO.getHour(), currentTimeO.getMinute(), currentTimeO.getSecond());

        return String.format("%s[%05d-%s]%s", fileNameWithoutExtension, saveFile.getTrainerId(),
                currentTimeS, extension);
    }

    private static void copySaveFile(SaveFile saveFile, String copyFileName) throws IOException {
        Files.copy(Paths.get(saveFile.getPath()),
                Paths.get(BACKUP_DIRECTORY + copyFileName),
                StandardCopyOption.REPLACE_EXISTING);
    }

    private static boolean isAlreadyBackedUp(SaveFile saveFile) throws IOException {
        File backupFolder = new File(BACKUP_DIRECTORY);
        File[] backupFiles = backupFolder.listFiles();

        if (backupFiles == null) throw new RuntimeException("the file at backup folder's path is not a folder");
        for(File file : backupFiles){
            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            String baseName = fileName.substring(0, fileName.lastIndexOf('['));

            String saveFileName = saveFile.getPath().substring(saveFile.getPath().lastIndexOf('/') + 1);

            if(saveFileName.equals(baseName + extension)){
                SaveFile backupFile = SaveFile.openSaveFile(BACKUP_DIRECTORY + fileName);
                if(backupFile.getChecksum() == saveFile.getChecksum()
                    && backupFile.getTrainerId() == saveFile.getTrainerId()){
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean containsRecentSave(ArrayList<File> files){
        for(File file : files){
            if(isRecent(file)){
                return true;
            }
        }
        return false;
    }

    private static boolean isRecent(File file){
        String fileName = file.getName();
        int leftBracketIndex = fileName.lastIndexOf('[');
        int rightBracketIndex = fileName.lastIndexOf(']');
        String[] fileInfos = fileName.substring(leftBracketIndex + 1, rightBracketIndex).split("-");
        LocalDate currentDate = LocalDate.now();
        int currentDayNumber = currentDate.getYear() * 365 + currentDate.getDayOfMonth() * 30 + currentDate.getDayOfMonth();
        int fileDayNumber = Integer.parseInt(fileInfos[1].substring(0, 4)) * 365
                + Integer.parseInt(fileInfos[1].substring(4, 6)) * 30
                + Integer.parseInt(fileInfos[1].substring(6, 8));

        return currentDayNumber <= fileDayNumber + 7;
    }

    private static void deleteAllOldSaves(ArrayList<File> files){
        for(File file : files){
            if(! isRecent(file)){
                file.delete();
            }
        }
    }

    private static void deleteAllButMostRecent(ArrayList<File> files){
        int maximumDayNumber = 0;
        File mostRecent = null;

        for(File file : files){
            String fileName = file.getName();
            int leftBracketIndex = fileName.lastIndexOf('[');
            int rightBracketIndex = fileName.lastIndexOf(']');
            String[] fileInfos = fileName.substring(leftBracketIndex + 1, rightBracketIndex).split("-");
            int fileDayNumber = Integer.parseInt(fileInfos[1].substring(0, 4)) * 365
                    + Integer.parseInt(fileInfos[1].substring(4, 6)) * 30
                    + Integer.parseInt(fileInfos[1].substring(6, 8));

            if(fileDayNumber > maximumDayNumber){
                maximumDayNumber = fileDayNumber;
                mostRecent = file;
            }
        }

        files.remove(mostRecent);
        deleteAllOldSaves(files);
    }
}
