package com.tomclaw.appsend.main.dto;

import android.content.ContentValues;
import android.database.Cursor;

import com.tomclaw.appsend.core.GlobalProvider;
import com.tomclaw.appsend.dto.UserIcon;

import java.io.Serializable;
import java.util.Collections;

/**
 * Created by solkin on 17/04/16.
 */
public class Message implements Serializable {

    private static boolean columnsInitialized = false;

    private static int COLUMN_PREV_MSG_ID;
    private static int COLUMN_MSG_ID;
    private static int COLUMN_USER_ID;
    private static int COLUMN_TEXT;
    private static int COLUMN_TIME;
    private static int COLUMN_COOKIE;
    private static int COLUMN_TYPE;
    private static int COLUMN_DIRECTION;
    private static int COLUMN_PUSH_TIME;

    private final long prevMsgId;
    private final long msgId;
    private final long userId;
    private final UserIcon userIcon;
    private final String text;
    private final long time;
    private final String cookie;
    private final int type;
    private final int direction;
    private final int pushTime;

    public Message(long msgId, long prevMsgId) {
        this.msgId = msgId;
        this.prevMsgId = prevMsgId;
        this.userId = 0;
        this.userIcon = new UserIcon("", Collections.emptyMap(), "");
        this.text = "";
        this.time = 0;
        this.cookie = "";
        this.type = 0;
        this.direction = 0;
        this.pushTime = 0;
    }

    public Message(long userId, String text, String cookie, int type, int direction) {
        this.prevMsgId = -1;
        this.msgId = -1;
        this.userId = userId;
        this.userIcon = new UserIcon("", Collections.emptyMap(), "");
        this.text = text;
        this.time = Long.MAX_VALUE;
        this.cookie = cookie;
        this.type = type;
        this.direction = direction;
        this.pushTime = 0;
    }

    public Message(long userId, long prevMsgId, long msgId, String text, long time,
                   String cookie, int type, int direction, int pushTime) {
        this.prevMsgId = prevMsgId;
        this.userId = userId;
        this.userIcon = new UserIcon("", Collections.emptyMap(), "");
        this.msgId = msgId;
        this.text = text;
        this.time = time;
        this.cookie = cookie;
        this.type = type;
        this.direction = direction;
        this.pushTime = pushTime;
    }

    public Message(long userId, long msgId, long prevMsgId, long time, String cookie) {
        this.prevMsgId = prevMsgId;
        this.userId = userId;
        this.userIcon = new UserIcon("", Collections.emptyMap(), "");
        this.msgId = msgId;
        this.text = "";
        this.time = time;
        this.cookie = cookie;
        this.type = -1;
        this.direction = -1;
        this.pushTime = 0;
    }

    public static Message fromCursor(Cursor cursor) {
        if (!columnsInitialized) {
            COLUMN_USER_ID = cursor.getColumnIndex(GlobalProvider.MESSAGES_USER_ID);
            COLUMN_PREV_MSG_ID = cursor.getColumnIndex(GlobalProvider.MESSAGES_PREV_MSG_ID);
            COLUMN_MSG_ID = cursor.getColumnIndex(GlobalProvider.MESSAGES_MSG_ID);
            COLUMN_TEXT = cursor.getColumnIndex(GlobalProvider.MESSAGES_TEXT);
            COLUMN_TIME = cursor.getColumnIndex(GlobalProvider.MESSAGES_TIME);
            COLUMN_COOKIE = cursor.getColumnIndex(GlobalProvider.MESSAGES_COOKIE);
            COLUMN_TYPE = cursor.getColumnIndex(GlobalProvider.MESSAGES_TYPE);
            COLUMN_DIRECTION = cursor.getColumnIndex(GlobalProvider.MESSAGES_DIRECTION);
            COLUMN_PUSH_TIME = cursor.getColumnIndex(GlobalProvider.MESSAGES_PUSH_TIME);
            columnsInitialized = true;
        }

        return new Message(
                cursor.getLong(COLUMN_USER_ID),
                cursor.getLong(COLUMN_PREV_MSG_ID),
                cursor.getLong(COLUMN_MSG_ID),
                cursor.getString(COLUMN_TEXT),
                cursor.getLong(COLUMN_TIME),
                cursor.getString(COLUMN_COOKIE),
                cursor.getInt(COLUMN_TYPE),
                cursor.getInt(COLUMN_DIRECTION),
                cursor.getInt(COLUMN_PUSH_TIME)
        );
    }

    public long getUserId() {
        return userId;
    }

    public UserIcon getUserIcon() {
        return userIcon;
    }

    public long getPrevMsgId() {
        return prevMsgId;
    }

    public long getMsgId() {
        return msgId;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public String getCookie() {
        return cookie;
    }

    public int getType() {
        return type;
    }

    public int getDirection() {
        return direction;
    }

    public int getPushTime() {
        return pushTime;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(GlobalProvider.MESSAGES_USER_ID, getUserId());
        if (getMsgId() >= 0) {
            values.put(GlobalProvider.MESSAGES_MSG_ID, getMsgId());
        }
        if (getPrevMsgId() >= 0) {
            values.put(GlobalProvider.MESSAGES_PREV_MSG_ID, getPrevMsgId());
        }
        values.put(GlobalProvider.MESSAGES_TEXT, getText());
        values.put(GlobalProvider.MESSAGES_TIME, getTime());
        values.put(GlobalProvider.MESSAGES_COOKIE, getCookie());
        values.put(GlobalProvider.MESSAGES_TYPE, getType());
        values.put(GlobalProvider.MESSAGES_DIRECTION, getDirection());
        values.put(GlobalProvider.MESSAGES_PUSH_TIME, getPushTime());
        return values;
    }
}
