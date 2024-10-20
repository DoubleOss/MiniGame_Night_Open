package com.doubleos.night.util;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;



@SideOnly(Side.CLIENT)
public class PNGInfo {
    int w;

    int h;

    public int id;

    public BufferedImage image;
    public PNGInfo(String url) {
        try {
            //BufferedImage im;
            URL link = new URL(url);
            //im = ImageIO.read(link);

            final HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
            image = ImageIO.read(connection.getInputStream());
            this.w = image.getWidth();
            this.h = image.getHeight();
            //this.id = (new DynamicTexture(image)).getGlTextureId();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int getId() {
        return this.id;
    }

    public int getHeight() {
        return this.h;
    }

    public int getWidth() {
        return this.w;
    }
}
