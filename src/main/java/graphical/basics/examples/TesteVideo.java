package graphical.basics.examples;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoStreamDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.Extension;
import com.github.kiulian.downloader.model.Filter;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class TesteVideo {
    public static void main(String[] args) {
        YoutubeDownloader downloader = new YoutubeDownloader();
        String videoId = "4_cHyUqik-U"; // for url https://www.youtube.com/watch?v=abc12345

// sync parsing
        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();



// video details
        VideoDetails details = video.details();
        System.out.println(details.title());
        System.out.println(details.viewCount());
        details.thumbnails().forEach(image -> System.out.println("Thumbnail: " + image));

// HLS url only for live videos and streams
        if (video.details().isLive()) {
            System.out.println("Live Stream HLS URL: " + video.details().liveUrl());
        }

// get videos formats only with audio
        List<VideoWithAudioFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        videoWithAudioFormats.forEach(it -> {
            System.out.println(it.audioQuality() + ", " + it.videoQuality() + " : " + it.url());
        });

// get all videos formats (may contain better quality but without audio)
        List<VideoFormat> videoFormats = video.videoFormats();
        videoFormats.forEach(it -> {
            System.out.println(it.videoQuality() + " : " + it.url());
        });

// get audio formats
        List<AudioFormat> audioFormats = video.audioFormats();
        audioFormats.forEach(it -> {
            System.out.println(it.audioQuality() + " : " + it.url());
        });

// get best format
        video.bestVideoWithAudioFormat();
        video.bestVideoFormat();
        video.bestAudioFormat();

// filtering formats
        List<Format> formats = video.findFormats(new Filter<Format>() {
            @Override
            public boolean test(Format format) {
                return format.extension() == Extension.WEBM;
            }
        });

// itags can be found here - https://gist.github.com/sidneys/7095afe4da4ae58694d128b1034e01e2
        Format formatByItag = video.findFormatByItag(18); // return null if not found
        if (formatByItag != null) {
            System.out.println(formatByItag.url());
        }

        File outputDir = new File("my_videos");
      //  Format format = videoFormats.get(4);
        Format format = audioFormats.get(0);

// sync downloading
        RequestVideoFileDownload request2 = new RequestVideoFileDownload(format)
                // optional params
                .saveTo(outputDir) // by default "videos" directory
                .renameTo("infinityPatterns") // by default file name will be same as video title on youtube
                .overwriteIfExists(true); // if false and file with such name already exits sufix will be added video(1).mp4
        Response<File> response2 = downloader.downloadVideoFile(request2);
        File data = response2.data();

    }


}
