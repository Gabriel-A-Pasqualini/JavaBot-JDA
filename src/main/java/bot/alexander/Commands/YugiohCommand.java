package bot.alexander.Commands;
import org.json.JSONArray;
import org.json.JSONObject;

import bot.alexander.alexander;
import bot.alexander.apis.YugiohApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class YugiohCommand extends ListenerAdapter{
    
    String prefix = alexander.prefix;

    //@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            String message = event.getMessage().getContentRaw();
            String card = message.split("card")[1];

            if (event.getMessage().getContentRaw().equalsIgnoreCase(prefix+"card"+card)) {
                System.out.println("YuGiOh! java");

                String jsonResponse = YugiohApi.searchCard(card.replaceFirst(" ", ""));

                JSONObject obj = new JSONObject(jsonResponse);

                System.out.println(obj);

                JSONArray objaux = obj.getJSONArray("data"); 
                        
                JSONObject f = objaux.getJSONObject(0);

                JSONArray imagem = f.getJSONArray("card_images");
                
                JSONObject image_url = imagem.getJSONObject(0);

                String image_url_small = image_url.getString("image_url");               

                // o problema esta aqui
                Integer atk = f.getInt("atk");
                Integer def = f.getInt("def");
                Integer lvl = f.getInt("level"); 

                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("🃏 Card Name: "+f.getString("name"));  

                System.out.println(lvl.toString());
                if(lvl.toString() != null){
                    info.addField("Level",lvl.toString() , true); 
                } 

                //System.out.println(f.getString("attribute"));
                //if(f.getString("attribute") != null){                                            
                //    info.addField("Attribute", f.getString("attribute"), true);                  
                //}
                
                System.out.println(f.getString("type"));
                info.addField("Type", f.getString("type"), false);
                
                System.out.println(atk.toString());
                if (atk.toString() != null ){
                    info.addField("Atk", atk.toString(), true);
                }

                System.out.println(def.toString());
                if (def.toString() != null ){
                    info.addField("Def", def.toString(), true);
                }

                System.out.println(f.getString("race"));
                if (f.getString("race") != null ){
                    info.addField("Race", f.getString("race"), false);  
                }

                System.out.println(f.getString("desc"));
                info.addField("Description", f.getString("desc"), false);

                System.out.println(image_url_small);
                info.setThumbnail(image_url_small);
               
                info.setColor(0xab0a1d);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).complete();
                info.clear();
            }
        }catch (Exception e) {

        }
    }
}