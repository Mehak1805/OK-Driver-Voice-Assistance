package com.okdriver.voiceassistant;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ConversationDao_Impl implements ConversationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ConversationEntity> __insertionAdapterOfConversationEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public ConversationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfConversationEntity = new EntityInsertionAdapter<ConversationEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `conversations` (`id`,`query`,`response`,`t1`,`t2`,`t3`,`t4`,`totalLatency`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ConversationEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getQuery() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getQuery());
        }
        if (entity.getResponse() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getResponse());
        }
        statement.bindLong(4, entity.getT1());
        statement.bindLong(5, entity.getT2());
        statement.bindLong(6, entity.getT3());
        statement.bindLong(7, entity.getT4());
        statement.bindLong(8, entity.getTotalLatency());
        statement.bindLong(9, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM conversations";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ConversationEntity conversation,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfConversationEntity.insert(conversation);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getLast10(final Continuation<? super List<ConversationEntity>> $completion) {
    final String _sql = "SELECT * FROM conversations ORDER BY timestamp DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ConversationEntity>>() {
      @Override
      @NonNull
      public List<ConversationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "response");
          final int _cursorIndexOfT1 = CursorUtil.getColumnIndexOrThrow(_cursor, "t1");
          final int _cursorIndexOfT2 = CursorUtil.getColumnIndexOrThrow(_cursor, "t2");
          final int _cursorIndexOfT3 = CursorUtil.getColumnIndexOrThrow(_cursor, "t3");
          final int _cursorIndexOfT4 = CursorUtil.getColumnIndexOrThrow(_cursor, "t4");
          final int _cursorIndexOfTotalLatency = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLatency");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ConversationEntity> _result = new ArrayList<ConversationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ConversationEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final String _tmpResponse;
            if (_cursor.isNull(_cursorIndexOfResponse)) {
              _tmpResponse = null;
            } else {
              _tmpResponse = _cursor.getString(_cursorIndexOfResponse);
            }
            final long _tmpT1;
            _tmpT1 = _cursor.getLong(_cursorIndexOfT1);
            final long _tmpT2;
            _tmpT2 = _cursor.getLong(_cursorIndexOfT2);
            final long _tmpT3;
            _tmpT3 = _cursor.getLong(_cursorIndexOfT3);
            final long _tmpT4;
            _tmpT4 = _cursor.getLong(_cursorIndexOfT4);
            final long _tmpTotalLatency;
            _tmpTotalLatency = _cursor.getLong(_cursorIndexOfTotalLatency);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ConversationEntity(_tmpId,_tmpQuery,_tmpResponse,_tmpT1,_tmpT2,_tmpT3,_tmpT4,_tmpTotalLatency,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
