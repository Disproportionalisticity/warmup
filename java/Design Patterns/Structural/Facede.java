package Structural;

interface IAmplifier {
    String turnOn();
    String turnOff();
    String setVolume(int level);
}

class ArcamAmplifier implements IAmplifier {
    public ArcamAmplifier() {}

    @Override
    public String turnOn() {
        return "Arcam Aplifier Turned On";
    }

    @Override
    public String turnOff() {
        return "Arcam Amplifier Turned Off";
    }

    @Override
    public String setVolume(int level) {
        if (level < 0 || level > 10) {
            return "Invalid value for level. Level should be an int between 0 and 10";
        } else {
            return "Set Arcam Amplifier volume to " + level;
        }
    }
}

interface IProjector {
    String turnOn();
    String setBrightness(int level);
    String startMovie(String movieName);
    String endMovie();
    String turnOff();
    String turnOnAmplifier();
    String turnOffAmplifier();
    String setVolumeAmplifier(int level);
}

class XiaomiProjector implements IProjector {
    protected String movieName;
    protected IAmplifier amplifier;

    public XiaomiProjector(IAmplifier amplifier) {
        this.amplifier = amplifier;
    }

    @Override
    public String turnOnAmplifier() {
        return this.amplifier.turnOn();
    }

    @Override
    public String turnOffAmplifier() {
        return this.amplifier.turnOff();
    }

    @Override
    public String setVolumeAmplifier(int level) {
        return this.amplifier.setVolume(level);
    }

    @Override
    public String turnOn() {
        return "Xiaomi Projector Turned On";
    }

    @Override
    public String turnOff() {
        return "Xiaomi Projector Turned Off";
    }

    @Override
    public String setBrightness(int level) {
        if (level < 0 || level > 10) {
            return "Invalid value for level. Level should be an int between 0 and 10";
        } else {
            return "Set Xiaomi Projector brightness to " + level;
        }
    }

    @Override
    public String startMovie(String movieName) {
        this.movieName = movieName;
        return "Start movie " + this.movieName;
    }

    @Override
    public String endMovie() {
        String returnString = "End movie " + this.movieName;
        this.movieName = "... no movie loaded ...";
        return returnString;
    }
}

interface ILights {
    String turnOn();
    String turnOff();
    String setBrightness(int level);
}

class XiaomiLights implements ILights {
    public XiaomiLights() {}

    @Override
    public String turnOn() {
        return "Xiaomi Lights Turned On";
    }

    @Override
    public String turnOff() {
        return "Xiaomi Lights Turned Off";
    }

    @Override
    public String setBrightness(int level) {
        if (level < 0 || level > 10) {
            return "Invalid value for level. Level should be an int between 0 and 10";
        } else {
            return "Set Xiaomi Lights brightness to " + level;
        }
    }
}

interface IPopcornMaker {
    String makePopcorn(int portions);
}

class BukatePopcornMaker implements IPopcornMaker {
    public BukatePopcornMaker() {}

    @Override
    public String makePopcorn(int portions) {
        if (portions < 0) {
            return "Impossible to make negative numbers worth of popcorn.";
        } else if (portions > 20) {
            return "Too many portions. Get a life.";
        } else {
            return "Bukate Popcorn Maker made " + portions + " portions of popcorn";
        }
    }
}

interface ICinema {
    String watchMovie(int numberOfViewers, String movieName);
    String endMovie();
}

class HomeCinemaFacade implements ICinema {
    private IProjector projector;
    private ILights lights;
    private IPopcornMaker popcornMaker;

    public HomeCinemaFacade(IProjector projector, ILights lights, IPopcornMaker popcornMaker) {
        this.projector = projector;
        this.lights = lights;
        this.popcornMaker = popcornMaker;
    }

    private String prepareEnvironment() {
        String finalString = "Prepare environment: ";
        finalString += this.lights.turnOff() + ", ";
        finalString += this.projector.turnOn() + ", ";
        finalString += this.projector.setVolumeAmplifier(7) + ", ";
        finalString += this.projector.setBrightness(7) + ", ";
        finalString += this.projector.turnOnAmplifier() + ", ";
        return finalString;
    }

    @Override
    public String watchMovie(int numberOfViewers, String movieName) {
        String finalString = "[Home Cinema] Start Movie: ";
        finalString += this.prepareEnvironment();
        finalString += this.popcornMaker.makePopcorn(numberOfViewers) + ", ";
        finalString += this.projector.startMovie(movieName) + ".";
        return finalString;
    }

    @Override
    public String endMovie() {
        String finalString = "[Home Cinema] End movie: ";
        finalString += this.projector.endMovie() + ".";
        finalString += this.projector.turnOffAmplifier() + ", ";
        finalString += this.projector.turnOff() + ", ";
        finalString += this.lights.turnOn() + ", ";
        return finalString;
    }
}

class FacedeTest {
    public static void main(String[] args) {
        ICinema homeCinema = new HomeCinemaFacade(new XiaomiProjector(new ArcamAmplifier()), new XiaomiLights(), new BukatePopcornMaker());
        System.out.println(homeCinema.watchMovie(2, "Fight Club"));
        System.out.println(homeCinema.endMovie());
    }
}
