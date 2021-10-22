package com.feizai.mytoast.string;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringEncodeUtil {
    private static Map<String, String> specialEncode;

    static {
        specialEncode = new HashMap<>();
        specialEncode.put("IBM420_ltr", "ISO8859–11");
        specialEncode.put("UTF-16LE", "windows-1250");
        specialEncode.put("windows-1252", "windows-1257");
    }

    public static boolean isUTF8(CharSequence charSequence) {
        try {
            byte[] utf = ((String) charSequence).getBytes("ISO-8859-1");
            int ix = 0;
            while (ix < utf.length) {
                char c = (char) utf[ix];
                if ((c & 128) == 0) {
                    ix++;
                } else if ((c & 224) == 192) {
                    if (utf.length < 2 || (utf[ix + 1] & 192) != 128) {
                        return false;
                    }
                    ix += 2;
                } else if ((c & 240) == 224) {
                    if (utf.length < 3 || (utf[ix + 1] & 192) != 128 || (utf[ix + 2] & 192) != 128) {
                        return false;
                    }
                    ix += 3;
                } else if ((c & 248) == 240) {
                    if (utf.length < 4 || (utf[ix + 1] & 192) != 128 || (utf[ix + 2] & 192) != 128 || (utf[ix + 3] & 192) != 128) {
                        return false;
                    }
                    ix += 4;
                } else if ((c & 251) != 248 || utf.length < 5 || (utf[ix + 1] & 192) != 128 || (utf[ix + 2] & 192) != 128 || (utf[ix + 3] & 192) != 128 || (utf[ix + 4] & 192) != 128) {
                    return false;
                } else {
                    ix += 5;
                }
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    public static String convertString(CharSequence charSequence, String targetCharSet) {
        try {
            return new String(((String) charSequence).getBytes("ISO-8859-1"), targetCharSet);
        } catch (UnsupportedEncodingException e) {
            return (String) charSequence;
        }
    }

    public static String getEncode(String path) {
        if (path == null) return null;
        try {
            byte[] bytes = readFile(path);
            if (bytes == null) return null;
            CharsetDetector detector = new CharsetDetector();
            detector.setText(bytes);
            CharsetMatch match = detector.detect();
            String encode = match.getName();
            if (specialEncode.containsKey(encode)) {
                return specialEncode.get(encode);
            }
            return encode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] readFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) return null;
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = fileInputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        fileInputStream.close();
        return baos.toByteArray();
    }
}
