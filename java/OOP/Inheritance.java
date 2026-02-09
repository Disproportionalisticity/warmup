class Life {
    protected boolean homeostatis = true;
    protected boolean nucleus;

    public String getHomeostatisInfo() {
        String homeostatisStatus = (this.homeostatis) ? "present" : "absent";
        return "Homeostatis is " + homeostatisStatus;
    }

    public String getNucleusInfo() {
        String nucleusStatus = (this.nucleus) ? "present" : "absent";
        return "Nucleus is " + nucleusStatus;
    }

    public void grow() {
        System.out.println("The process of growing...");
    }

    public void reproduce() {
        System.out.println("The process of reproduction...");
    }
}

class Prokaryota extends Life {
    protected boolean nucleus = false;
}

class Eukaryota extends Life {
    protected boolean nucleus = true;
}

class Bacteria extends Prokaryota {
    public void chorumSending () {
        System.out.println("Sending chorum to communicate...");
    }
}

class Plantae extends Eukaryota {
    public void autotrophy() {
        System.out.println("Initiate autotrophy to produce food...");
    }
}

class Fungi extends Eukaryota {
    protected boolean decentralizedIntelligence = true;

    protected boolean externalDigestion = true;

    public void solveProblem() {
        String intelligenceType = (this.decentralizedIntelligence) ? "decentralized" : "centralized";
        System.out.println("Solving essential survival problems in a " + intelligenceType + ": find shortest route to food...");
    }
}

class Animalia extends Eukaryota {
    public void communicateViaWords() {
        System.out.println("Initiate communication process via words...");
    }

    public void communicateViaSongs() {
        System.out.println("Initiate communication process via songs...");
    }
    
    public void communicateViaDance() {
        System.out.println("Initiate communication process via dance...");
    }
}

class Inheritance {
    public static void main(String[] args) {
        System.out.println("Bacteria behavior");
        Bacteria bacteria = new Bacteria();
        System.out.println(bacteria.getHomeostatisInfo());
        System.out.println(bacteria.getNucleusInfo());
        bacteria.grow();
        bacteria.reproduce();
        bacteria.chorumSending();
        System.out.println("");
        
        System.out.println("Plantae behavior");
        Plantae plantae = new Plantae();
        System.out.println(plantae.getHomeostatisInfo());
        System.out.println(plantae.getNucleusInfo());
        plantae.grow();
        plantae.reproduce();
        plantae.autotrophy();
        System.out.println("");

        System.out.println("Fungi behavior");
        Fungi fungi = new Fungi();
        System.out.println(fungi.getHomeostatisInfo());
        System.out.println(fungi.getNucleusInfo());
        fungi.grow();
        fungi.reproduce();
        fungi.solveProblem();
        System.out.println("");

        System.out.println("Animalia behavior");
        Animalia animalia = new Animalia();
        System.out.println(animalia.getHomeostatisInfo());
        System.out.println(animalia.getNucleusInfo());
        animalia.grow();
        animalia.reproduce();
        animalia.communicateViaDance();
        animalia.communicateViaSongs();
        animalia.communicateViaWords();
        System.out.println(""); 

        /* Output:
            Bacteria behavior
            Homeostatis is present
            Nucleus is absent
            The process of growing...
            The process of reproduction...
            Sending chorum to communicate...

            Plantae behavior
            Homeostatis is present
            Nucleus is absent
            The process of growing...
            The process of reproduction...
            Initiate autotrophy to produce food...

            Fungi behavior
            Homeostatis is present
            Nucleus is absent
            The process of growing...
            The process of reproduction...
            Solving essential survival problems in a decentralized: find shortest route to food...

            Animalia behavior
            Homeostatis is present
            Nucleus is absent
            The process of growing...
            The process of reproduction...
            Initiate communication process via dance...
            Initiate communication process via songs...
            Initiate communication process via words...
         */
    }
}