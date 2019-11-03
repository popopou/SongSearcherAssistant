package zsx.com.songsearchassistant.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import zsx.com.songsearchassistant.activity.MainActivity;
import zsx.com.songsearchassistant.holder.SongViewHolder;
import zsx.com.songsearchassistant.http.handler.SimpleCallbackHandler;
import zsx.com.songsearchassistant.http.request.QQMusic;
import zsx.com.songsearchassistant.http.response.QQMusicPlaySongResponse;
import zsx.com.songsearchassistant.http.response.QQMusicSearchSongResponse;
import zsx.com.songsearchassistant.models.SongItem;
import okhttp3.Call;
import okhttp3.Request;

public class QQMusicSongAdapter extends RecyclerView.Adapter {

    private QQMusicSearchSongResponse searchResult;
    private Activity activity;

    public QQMusicSongAdapter(QQMusicSearchSongResponse searchResult, Activity activity) {
        this.searchResult = searchResult;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final QQMusicSearchSongResponse.Data.Song.SongItem song = searchResult.data.song.list.get(position);
        ((SongViewHolder)holder).name.setText(song.name);
        ((SongViewHolder)holder).singer.setText(song.singer.get(0).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic(song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResult.data.song.list.size();
    }

    private void playMusic(final QQMusicSearchSongResponse.Data.Song.SongItem song) {
        Request req = QQMusic.playSong(song.mid);
        final SongItem songItem = new SongItem();
        songItem.name = song.name;
        songItem.albumImage = QQMusic.getAlbumUrl(song.album.mid);
        songItem.artist = song.singer.get(0).name;
        songItem.extensionName = "m4a";
        songItem.album = song.album.name;
        ((MainActivity)activity).httpClient.newCall(req).enqueue(new SimpleCallbackHandler<QQMusicPlaySongResponse>(activity) {
            @Override
            public void onResult(Call call, QQMusicPlaySongResponse response) {
                if (response.code != 0 || response.req_0.data.sip.size() == 0 || response.req_0.data.midurlinfo.size() == 0) {
                    return;
                }
                songItem.downloadUrl = response.req_0.data.sip.get(0) + response.req_0.data.midurlinfo.get(0).purl;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)activity).playMusic(songItem);
                    }
                });
            }
        });
    }
}
