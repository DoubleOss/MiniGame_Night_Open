package com.hud;

import net.minecraft.launchwrapper.IClassTransformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ChnageClass implements IClassTransformer {
    String[] chagneClass = new String[] { "MovementInputFromOptions", "RenderPlayer", "GuiGameOver", "WeaponSpawnEntity", "Explosion", "TabCompleter", "GuiChat", "ScaledResolution", "GuiIngame"};

    String[] classes = new String[0];

    public byte[] transform(String arg0, String arg1, byte[] arg2)
    {
        if (check(arg1))
        {
            return patching(arg0, arg1, arg2, FakeModLoader.modFile);
        }

        return arg2;
    }

    private boolean check(String s) {
        for (String cl : this.chagneClass)
        {
            if (("net.minecraft.client.gui.inventory." + cl).equals(s))
                return true;
            else if(("net.minecraft.client.gui." + cl).equals(s))
            {
                System.out.println("ScaleResoultion 변경 완료");
                return true;
            }
            else if(("net.minecraft.client." + cl).equals(s))
            {
                System.out.println("Minecraft 변경 완료");
                return true;
            }
            else if(("net.minecraft.client.gui.inventory." + cl).equals(s))
            {
                System.out.println("GuiChest 변경 완료");
                return true;
            }
            else if(("net.minecraft.util." + cl).equals(s))
            {
                System.out.println("MovementInputFromOptions 변경 완료");
                return true;
            }
            else if(("net.minecraft.client.renderer.entity." + cl).equals(s))
            {
                System.out.println("RenderPlayer 변경 완료");
                return true;
            }
            else if(("com.vicmatskiv.weaponlib." + cl).equals(s))
            {
                System.out.println("WeaponSpawnEntity 변경 완료");
                return true;
            }
            else if(("net.minecraft.client.gui." + cl).equals(s))
            {
                System.out.println("GuiChat 변경 완료");
                return true;
            }
        }
        for (String cl : this.classes) {
            if (cl.equals(s))
                return true;
        }
        return false;
    }

    private byte[] patching(String rebname, String name, byte[] data, File f) {
        try {
            ZipFile zip = new ZipFile(f);
            ZipEntry e = zip.getEntry(rebname.replace('.', '/') + ".class");
            if (e == null) {
                e = zip.getEntry(name.replace('.', '/') + ".class");

                if (e == null) {
                    zip.close();
                    return data;
                }
            }
            InputStream is = zip.getInputStream(e);
            data = rwAll(is);
            is.close();
            zip.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private byte[] rwAll(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1)
            bos.write(buffer, 0, len);
        return bos.toByteArray();
    }
}
