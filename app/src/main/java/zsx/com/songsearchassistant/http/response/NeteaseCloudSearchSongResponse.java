package zsx.com.songsearchassistant.http.response;

import java.util.List;

public class NeteaseCloudSearchSongResponse {
    public Result result;

    public static class Result {
        public List<SongItem> songs;

        public static class SongItem {
            public String name;
            public int id;
            public Album al;
            public List<ArtistItem> ar;
            public Privilege privilege;

            public static class Album {
                public String name;
                public String picUrl;
            }

            public static class ArtistItem {
                public String name;
            }

            public static class Privilege {
                public int st;
            }
        }
    }
}
