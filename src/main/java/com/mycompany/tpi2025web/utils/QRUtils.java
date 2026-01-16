
package com.mycompany.tpi2025web.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class QRUtils {

    public static String generarQRBase64(String texto) throws WriterException, IOException {

        int width = 200;
        int height = 200;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // 🔹 Configuración para español (tildes, ñ, etc.)
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(
                texto,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);

        byte[] bytes = baos.toByteArray();

        return Base64.getEncoder().encodeToString(bytes);
    }
}
