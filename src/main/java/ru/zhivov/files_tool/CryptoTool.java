package ru.zhivov.files_tool;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CryptoTool {

    //ключ расшифровки
    private String key;

    //константа основной директории
    private final String mainDir = "data/secret/";
    //константа именени файла с зашифрованным паролем
    private final String pFile = mainDir + "1Qdwfw@d.filesupersecret";
    //константа именени файла с ключем расшифровки пароля
    private final String kFile = mainDir + "key.seq";
    //константа именени файла с получателем письма
    private final String tFile = mainDir + "TO.mail";
    //константа именени файла с отправителем письма
    private final String fFile = mainDir + "FROM.mail";
    //константа именени файла с адресом для теневой копии
    private final String bFile = mainDir + "BCC.mail";


    public CryptoTool() {
        this.key = getKeyFromFile();
    }

    //метод получение ключа расшифровки из файла
    private String getKeyFromFile() {
        File file = new File(kFile);
        String strFromFile = "";
        //читаем файл и складываем прочитанное в стрингу
        try (FileReader reader = new FileReader(file)) {
            char[] buffer = new char[16];
            //реализовано для исключения варнинга
            int read = reader.read(buffer);
            strFromFile = String.valueOf(buffer).trim();
        } catch (IOException e) {
            System.out.println("Не найден ключ расшифровки " + kFile);
            System.out.println("Дальнейшая работа невозможна");
            doExit();
        }
        return strFromFile;
    }

    //методы шифровки файла
    public void encrypt(String key, File inputFile, File outputFile) {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public String encrypt(String key, File inputFile) {
        return doCrypto(Cipher.ENCRYPT_MODE, key, inputFile);
    }

    //методы расшифровки файла
    public void decrypt(String key, File inputFile, File outputFile) {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public String decrypt(String key, File inputFile) {
        return doCrypto(Cipher.DECRYPT_MODE, key, inputFile);
    }


    private void doCrypto(int cipherMode, String key, File inputFile, File outputFile) {
        try (
                //открываем поток чтения
                FileInputStream inputStream = new FileInputStream(inputFile);
                //открываем поток записи
                FileOutputStream outputStream = new FileOutputStream(outputFile)
        ) {
            //устанавлием спецификацию шифрования и передаем сам ключ
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            //получаем экземпляр шифратора AES
            Cipher cipher = Cipher.getInstance("AES");
            //инициализируем шифрование с указанием:
            //cipherMode - режим шифровка/расшифровка
            //secretKey - ключ согласно указанной выше спецификации
            cipher.init(cipherMode, secretKey);

            //записываем данные считанные потоком в массив байт
            byte[] inputBytes = new byte[(int) inputFile.length()];
            int read = inputStream.read(inputBytes);

            //выполняем конечное шифрование и записываем в массив байт
            byte[] outputBytes = cipher.doFinal(inputBytes);

            //записываем зашифрованный массив байт в файл внутри ФС
            outputStream.write(outputBytes);


        } catch (BadPaddingException e) {
            System.out.println("Неверный ключ расшифровки");
            //e.printStackTrace();
            doExit();
        } catch (
                NoSuchPaddingException
                        | NoSuchAlgorithmException
                        | InvalidKeyException
                        | IllegalBlockSizeException
                        | IOException e
        ) {
            System.out.println("Ошибка механизма шифрования/расшифровки");
            //e.printStackTrace();
            doExit();
        }
    }

    //перегруженный метод
    private String doCrypto(int cipherMode, String key, File inputFile) {
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {

            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);


            byte[] inputBytes = new byte[(int) inputFile.length()];

            int read = inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            return new String(outputBytes);

        } catch (BadPaddingException e) {
            System.out.println("Неверный ключ расшифровки");
            doExit();
        } catch (
                NoSuchPaddingException
                        | NoSuchAlgorithmException
                        | InvalidKeyException
                        | IllegalBlockSizeException
                        | IOException e
        ) {
            System.out.println("Ошибка механизма шифрования/расшифровки");
            doExit();
        }
        return "";
    }

    //получаем расшифрованный пароль
    public String getPss() {
        return decrypt(key, new File(pFile)).trim();
    }

    //получаем расшифрованный адрес отправителя
    public String getSender() {
        return decrypt(key, new File(fFile)).trim();
    }

    //получаем расшифрованный адрес получателя
    public String getRecipient() {
        return decrypt(key, new File(tFile)).trim();
    }

    //получаем расшифрованный адресс для теневой копии
    public String getBCC() {
        return decrypt(key, new File(bFile)).trim();
    }

    private void doExit() {
        System.exit(1);
    }
}
