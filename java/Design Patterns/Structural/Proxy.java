package Structural;

import java.util.HashMap;

interface IVideoService {
    String getVideo(String videoName);
}

class RealVideoService implements IVideoService{
    public RealVideoService() {}

    private String getVideoFromAPI(String videoName) {
        // Simulating API behavior
        return String.valueOf(videoName.hashCode());
    }

    @Override
    public String getVideo(String videoName) {
        return this.getVideoFromAPI(videoName);
    }
}

class VideoServiceProxy implements IVideoService {
    private IVideoService videoService;
    private HashMap<String, String> videos;

    public VideoServiceProxy(IVideoService videoService) {
        this.videoService = videoService;
        this.videos = new HashMap<String, String>();
    }

    @Override
    public String getVideo(String videoName) {
        if (!this.videos.containsKey(videoName)) {
            this.videos.put(videoName, this.videoService.getVideo(videoName));
        }
        return this.videos.get(videoName);
    }

    
}

class ProxyTest {
    public static void main(String[] args) {
        IVideoService videoProxy = new VideoServiceProxy(new RealVideoService());

        System.out.println(videoProxy.getVideo("abra"));
        System.out.println(videoProxy.getVideo("cadabra"));
        System.out.println(videoProxy.getVideo("abra"));
        System.out.println(videoProxy.getVideo("cadabra"));
    }
}
