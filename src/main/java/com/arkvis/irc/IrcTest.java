package com.arkvis.irc;

import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.domain.IRCServer;
import com.ircclouds.irc.api.domain.messages.*;
import com.ircclouds.irc.api.domain.messages.interfaces.IMessage;
import com.ircclouds.irc.api.listeners.IVariousMessageListener;
import com.ircclouds.irc.api.state.IIRCState;

import java.util.Arrays;
import java.util.List;

public class IrcTest {

    public static void main(String[] args) {
        IRCApi ircApi = new IRCApiImpl(true);

        ircApi.connect(getServerParams("nick", Arrays.asList("altNick1", "altNick2"), "IRC api", "ident", "irc.libera.chat", true), new Callback<>() {
            @Override
            public void onSuccess(IIRCState aObject) {
                System.out.println("Connected to server");
                ircApi.joinChannel("test123");
            }

            @Override
            public void onFailure(Exception aExc) {
                throw new RuntimeException(aExc);

            }
        });

        ircApi.addListener(new IVariousMessageListener() {
            @Override
            public void onChannelMessage(ChannelPrivMsg aMsg) {
                System.out.println(aMsg.getChannelName());
                System.out.println(aMsg.getSource());
                System.out.println(aMsg.getText());

                ircApi.message("#test123", "Got message " + aMsg.getText());
            }

            @Override
            public void onChannelJoin(ChanJoinMessage aMsg) {
                System.out.println("Joined channel " + aMsg.getChannelName());

            }

            @Override
            public void onChannelPart(ChanPartMessage aMsg) {

            }

            @Override
            public void onChannelNotice(ChannelNotice aMsg) {

            }

            @Override
            public void onChannelAction(ChannelActionMsg aMsg) {

            }

            @Override
            public void onChannelKick(ChannelKick aMsg) {

            }

            @Override
            public void onTopicChange(TopicMessage aMsg) {

            }

            @Override
            public void onUserPrivMessage(UserPrivMsg aMsg) {
                System.out.println("Private message");
                System.out.println(aMsg.getSource());
                System.out.println(aMsg.getToUser());
                System.out.println(aMsg.getText());
            }

            @Override
            public void onUserNotice(UserNotice aMsg) {

            }

            @Override
            public void onUserAction(UserActionMsg aMsg) {

            }

            @Override
            public void onServerNumericMessage(ServerNumericMessage aMsg) {

            }

            @Override
            public void onServerNotice(ServerNotice aMsg) {

            }

            @Override
            public void onNickChange(NickMessage aMsg) {

            }

            @Override
            public void onUserQuit(QuitMessage aMsg) {

            }

            @Override
            public void onError(ErrorMessage aMsg) {

            }

            @Override
            public void onChannelMode(ChannelModeMessage aMsg) {

            }

            @Override
            public void onUserPing(UserPing aMsg) {
                System.out.println("Ping");
                System.out.println(aMsg.getSource());
                System.out.println(aMsg.getText());
                System.out.println(aMsg.getToUser());

            }

            @Override
            public void onUserVersion(UserVersion aMsg) {

            }

            @Override
            public void onServerPing(ServerPing aMsg) {

            }

            @Override
            public void onMessage(IMessage aMessage) {

            }
        });

    }

    private static IServerParameters getServerParams(String nick, List<String> altNicks, String realName, String identity, String serverName, boolean sslServer) {
        return new IServerParameters() {
            @Override
            public String getNickname() {
                return nick;
            }

            @Override
            public List<String> getAlternativeNicknames() {
                return altNicks;
            }

            @Override
            public String getIdent() {
                return identity;
            }

            @Override
            public String getRealname() {
                return realName;
            }

            @Override
            public IRCServer getServer() {
                return new IRCServer(serverName, sslServer);
            }
        };
    }
}
