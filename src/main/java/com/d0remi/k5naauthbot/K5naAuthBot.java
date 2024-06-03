package com.d0remi.k5naauthbot;

import com.d0remi.k5naauthbot.command.commandManager;
import com.d0remi.k5naauthbot.wikiSearch.gitbookSearch;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class K5naAuthBot {

    private final ShardManager shardManager;

    public K5naAuthBot() throws LoginException {

        //Build shard manager
        String token = "";
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("코나 서버"));
        shardManager = builder.build();

        //Register listeners
        shardManager.addEventListener(new commandManager());
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] arguments) {
        try {
            K5naAuthBot bot = new K5naAuthBot();
        } catch (LoginException e){
            System.out.println("오류: 알 수 없는 토큰");
        }
    }
}