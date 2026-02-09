package Creational;

interface FurnitureInterface {
    public void assemble();
}

abstract class FurnitureAbstract implements FurnitureInterface {
    protected String materialType;
    protected String specificMaterial;

    public FurnitureAbstract(String specificMaterial) {
        this.specificMaterial = specificMaterial;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getSpecificMaterial() {
        return specificMaterial;
    }

    public void setSpecificMaterial(String specificMaterial) {
        this.specificMaterial = specificMaterial;
    }

    @Override
    public String toString() {
        return "Material used to fabricare furniture: " + this.materialType + ", specifically " + this.specificMaterial;
    }
}

abstract class WoodenFurnitureAbstract extends FurnitureAbstract {
    public WoodenFurnitureAbstract(String specificMaterial) {
        super(specificMaterial);
        this.materialType = "wood";
    }
}

class WoodenChair extends WoodenFurnitureAbstract {
    public WoodenChair(String specificMaterial) {
        super(specificMaterial);
    }

    @Override
    public void assemble() {
        System.out.println("Assembling wooden chair from " + this.specificMaterial);
    }
}

class WoodenTable extends WoodenFurnitureAbstract {
    public WoodenTable(String specificMaterial) {
        super(specificMaterial);
    }

    @Override
    public void assemble() {
        System.out.println("Assembling wooden table from " + this.specificMaterial);
    }
}

abstract class PlasticFurnitureAbstract extends FurnitureAbstract {
    public PlasticFurnitureAbstract(String specificMaterial) {
        super(specificMaterial);
        this.materialType = "plastic";
    }
}

class PlasticChair extends PlasticFurnitureAbstract {
    public PlasticChair(String specificMaterial) {
        super(specificMaterial);
    }

    @Override
    public void assemble() {
        System.out.println("Assembling plastic chair from " + this.specificMaterial);
    }
}

class PlasticTable extends PlasticFurnitureAbstract {
    public PlasticTable(String specificMaterial) {
        super(specificMaterial);
    }

    @Override
    public void assemble() {
        System.out.println("Assembling plastic table from " + this.specificMaterial);
    }
}

abstract class FurnitureFactory {
    public abstract WoodenFurnitureAbstract createWooden(String specificMaterial);

    public abstract PlasticFurnitureAbstract createPlastic(String specificMaterial);
}

class ChairFurnitureFactory extends FurnitureFactory {
    public ChairFurnitureFactory() {}

    @Override
    public WoodenFurnitureAbstract createWooden(String specificMaterial) { 
        return new WoodenChair(specificMaterial);
    }

    @Override
    public PlasticFurnitureAbstract createPlastic(String specificMaterial) {
        return new PlasticChair(specificMaterial);
    }
}

class TableFurnitureFactory extends FurnitureFactory {
    public TableFurnitureFactory() {}

    @Override
    public WoodenFurnitureAbstract createWooden(String specificMaterial) {
        return new WoodenTable(specificMaterial);
    }

    @Override
    public PlasticFurnitureAbstract createPlastic(String specificMaterial) {
        return new PlasticTable(specificMaterial);
    }
}

class AbstractFactoryTest {
    public static void main(String[] args) {
        FurnitureFactory chairFactory = new ChairFurnitureFactory();
        FurnitureFactory tableFactory = new TableFurnitureFactory();

        WoodenFurnitureAbstract woodenChair = chairFactory.createWooden("marple");
        PlasticFurnitureAbstract plasticChair = chairFactory.createPlastic("HDPE");

        WoodenFurnitureAbstract woodenTable = tableFactory.createWooden("cherry");
        PlasticFurnitureAbstract plasticTable = tableFactory.createPlastic("Acrylic");

        woodenChair.assemble();
        woodenTable.assemble();
        plasticChair.assemble();
        plasticTable.assemble();
    }
}
