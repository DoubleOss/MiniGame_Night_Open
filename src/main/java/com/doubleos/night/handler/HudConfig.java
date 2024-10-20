package com.doubleos.night.handler;


import com.doubleos.night.variable.Variable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;

public class HudConfig
{
    public static Configuration m_config;


    public static void init(File file)
    {
        if(m_config == null)
        {
            File hudConfigDic = new File(file.getPath(), "/night");
            hudConfigDic.mkdir();
            m_config = new Configuration(new File(hudConfigDic, "night.cfg"));
            initConfig();

        }
        else
        {
            initConfig();
        }
    }

    public static void writeConfig(String category, String key, int value)
    {
        Configuration config = m_config;
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, String value) {
        Configuration config = m_config;
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }
    public static void writeConfig(String category, String key, float value) {
        Configuration config = m_config;
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(Double.valueOf(value));
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }
    public static void writeConfig(String category, String key, long value) {
        Configuration config = m_config;
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static long getLong(String category, String key) {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0l).getLong();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
            System.out.println(e);
        } finally {
            config.save();
        }
        return 0l;
    }


    public static int getInt(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0;
    }

    public static void initConfig()
    {

        Variable variable = Variable.Instance();

        if( getFloat("night", "question_time_player_chair_1_pos_x") == 0f )
        {

            writeConfig("night", "question_time_player_chair_1_pos_x", 0f);
            writeConfig("night", "question_time_player_chair_1_pos_y", 0f);
            writeConfig("night", "question_time_player_chair_1_pos_z", 0f);

            writeConfig("night", "question_time_player_chair_2_pos_x", 0f);
            writeConfig("night", "question_time_player_chair_2_pos_y", 0f);
            writeConfig("night", "question_time_player_chair_2_pos_z", 0f);

            writeConfig("night", "question_time_player_chair_3_pos_x", 0f);
            writeConfig("night", "question_time_player_chair_3_pos_y", 0f);
            writeConfig("night", "question_time_player_chair_3_pos_z", 0f);

            writeConfig("night", "question_time_player_chair_4_pos_x", 0f);
            writeConfig("night", "question_time_player_chair_4_pos_y", 0f);
            writeConfig("night", "question_time_player_chair_4_pos_z", 0f);

            writeConfig("night", "question_time_player_chair_5_pos_x", 0f);
            writeConfig("night", "question_time_player_chair_5_pos_y", 0f);
            writeConfig("night", "question_time_player_chair_5_pos_z", 0f);

            writeConfig("night", "question_time_player_chair_6_pos_x", 0f);
            writeConfig("night", "question_time_player_chair_6_pos_y", 0f);
            writeConfig("night", "question_time_player_chair_6_pos_z", 0f);


        }
        else
        {

            for (int i = 1; i < 7; i++)
            {
                float x,y,z;
                x = getFloat("night", "question_time_player_chair_"+i+"_pos_x");
                y = getFloat("night", "question_time_player_chair_"+i+"_pos_y");
                z = getFloat("night", "question_time_player_chair_"+i+"_pos_z");

                variable.questionTimePlayerChairLocList.set(i-1, new BlockPos(x, y, z));
                System.out.println(" 작탁 의자 위치 설정 완료  ");

            }

        }
        if (getFloat("night", "prison_loc_list_1_pos_x") == 0f)
        {
            writeConfig("night", "prison_loc_list_1_pos_x", 0f);
            writeConfig("night", "prison_loc_list_1_pos_y", 0f);
            writeConfig("night", "prison_loc_list_1_pos_z", 0f);

            writeConfig("night", "prison_loc_list_2_pos_x", 0f);
            writeConfig("night", "prison_loc_list_2_pos_y", 0f);
            writeConfig("night", "prison_loc_list_2_pos_z", 0f);

            writeConfig("night", "prison_loc_list_3_pos_x", 0f);
            writeConfig("night", "prison_loc_list_3_pos_y", 0f);
            writeConfig("night", "prison_loc_list_3_pos_z", 0f);

            writeConfig("night", "prison_loc_list_4_pos_x", 0f);
            writeConfig("night", "prison_loc_list_4_pos_y", 0f);
            writeConfig("night", "prison_loc_list_4_pos_z", 0f);

            writeConfig("night", "prison_loc_list_5_pos_x", 0f);
            writeConfig("night", "prison_loc_list_5_pos_y", 0f);
            writeConfig("night", "prison_loc_list_5_pos_z", 0f);

            writeConfig("night", "prison_loc_list_6_pos_x", 0f);
            writeConfig("night", "prison_loc_list_6_pos_y", 0f);
            writeConfig("night", "prison_loc_list_6_pos_z", 0f);



        }
        else
        {
            for(int i = 1; i<7; i++)
            {
                float x,y,z;
                x = getFloat("night", "prison_loc_list_"+i+"_pos_x");
                y = getFloat("night", "prison_loc_list_"+i+"_pos_y");
                z = getFloat("night", "prison_loc_list_"+i+"_pos_z");
                variable.prisonLocList.set(i-1, new BlockPos(x, y, z));
            }


        }
        if (getFloat("night", "result_loc_x") == 0f)
        {
            writeConfig("night", "result_loc_x", 0f);
            writeConfig("night", "result_loc_y", 0f);
            writeConfig("night", "result_loc_z", 0f);
        }
        else
        {

            float x,y,z;
            x = getFloat("night", "result_loc_x");
            y = getFloat("night", "result_loc_y");
            z = getFloat("night", "result_loc_z");

            variable.resultPos = new BlockPos(x, y, z);
        }

        if (getFloat("night", "police_loc_x") == 0f)
        {
            writeConfig("night", "police_loc_x", 0f);
            writeConfig("night", "police_loc_y", 0f);
            writeConfig("night", "police_loc_z", 0f);
        }
        else
        {

            float x,y,z;
            x = getFloat("night", "police_loc_x");
            y = getFloat("night", "police_loc_y");
            z = getFloat("night", "police_loc_z");

            variable.guard = new BlockPos(x, y, z);


        }

        if( getFloat("night", "question_time_player_chair_1_pos_x") == 0f )
        {

            writeConfig("night", "prison_card_entity_loc_list_1_pos_x", 0f);
            writeConfig("night", "prison_card_entity_loc_list_1_pos_y", 0f);
            writeConfig("night", "prison_card_entity_loc_list_1_pos_z", 0f);

            writeConfig("night", "prison_card_entity_loc_list_2_pos_x", 0f);
            writeConfig("night", "prison_card_entity_loc_list_2_pos_y", 0f);
            writeConfig("night", "prison_card_entity_loc_list_2_pos_z", 0f);

            writeConfig("night", "prison_card_entity_loc_list_3_pos_x", 0f);
            writeConfig("night", "prison_card_entity_loc_list_3_pos_y", 0f);
            writeConfig("night", "prison_card_entity_loc_list_3_pos_z", 0f);

            writeConfig("night", "prison_card_entity_loc_list_4_pos_x", 0f);
            writeConfig("night", "prison_card_entity_loc_list_4_pos_y", 0f);
            writeConfig("night", "prison_card_entity_loc_list_4_pos_z", 0f);

            writeConfig("night", "prison_card_entity_loc_list_5_pos_x", 0f);
            writeConfig("night", "prison_card_entity_loc_list_5_pos_y", 0f);
            writeConfig("night", "prison_card_entity_loc_list_5_pos_z", 0f);

            writeConfig("night", "prison_card_entity_loc_list_6_pos_x", 0f);
            writeConfig("night", "prison_card_entity_loc_list_6_pos_y", 0f);
            writeConfig("night", "prison_card_entity_loc_list_6_pos_z", 0f);


        }
        else
        {
            for(int i = 1; i<7; i++)
            {
                float x,y,z;
                x = getFloat("night", "prison_card_entity_loc_list_"+i+"_pos_x");
                y = getFloat("night", "prison_card_entity_loc_list_"+i+"_pos_y");
                z = getFloat("night", "prison_card_entity_loc_list_"+i+"_pos_z");
                variable.cardEntityList.set(i-1, new BlockPos(x, y, z));
            }

        }

    }


    public static float getFloat(String category, String key) {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return (float)config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0f;
    }
    public static double getDouble(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0D;
    }

    public static String getString(String category, String key)
    {
        Configuration config = m_config;
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return "";
    }
    public static void removeResetClickWrite()
    {

    }
    public static void removeResetInterBlockWrite()
    {

    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {

    }
}
