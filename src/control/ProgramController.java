package control;

import model.GameObjects.*;
import model.MapBuildingObject.*;
import mapObjects.*;
import control.framework.UIController;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    // Attribute

    // Referenzen
    private UIController uiController;  // diese Referenz soll auf ein Objekt der Klasse uiController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private MapBuilder mapBuilder;
    private Grass[][] grass;
    private Tree[][] tree;
    private Barn[][] barns;
    private Plant[][] plants;
    private Cat cat;
    private Animal[] freeAnimals = new Animal[10];
    private Bird bird;
    private Eichhörnchen eichhörnchen;
    private Fox fox;
    private Goat goat;
    private Hirsch hirsch;
    private Rabbit rabbit;
    private Schnecke schnecke;
    private WildPig wildPig;
    private Worm worm;
    private UserInterface userInterface;
    private SQLHandler sqlHandler;
    private XButton xButton;
    private shop shop;
    private MenuButton[] buttons;
    private TitleScreen ts;
    private int animalCount;
    private BarnInfo barnInfo;
    private Inventory inventory;
    private Knebi knebi;
    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse UIController. Diese wird als Parameter übergeben.
     * @param uiController das UIController-Objekt des Programms
     */
    public ProgramController(UIController uiController){
        this.uiController = uiController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     */


    public void startProgram(){
        ts = new TitleScreen(0,0,this);
        uiController.drawObject(ts);
    }

    public void startGame(){
        uiController.removeObject(ts);
        userInterface = new UserInterface(0,0);
        uiController.drawObject(userInterface);
        grass = new Grass[13][28];
        tree = new Tree[13][28];
        barns = new Barn[13][28];

        mapBuilder = new MapBuilder(grass,tree,uiController,this,barnInfo);
        sqlHandler = new SQLHandler(mapBuilder, userInterface);
        sqlHandler.handleSQL();
        cat = new Cat(200,500,this, "katze",-1,uiController);
        plants = new Plant[13][28];
        uiController.drawObject(cat);
        xButton = new XButton(1050,5,this);
        uiController.drawObject(xButton);
        uiController.drawObject(shop);
        inventory = new Inventory(550,50,this,sqlHandler);
        uiController.drawObject(inventory);


        buttons = new MenuButton[5];
        buttons[0] = new AxeButton(1000,5,grass,this);
        buttons[1] = new InventoryButton(950,5,this);
        buttons[2] = new ShopButton(900,5,this);
        buttons[3] = new PickUpButton(850,5,this);

        for(MenuButton b : buttons){
            if(b != null){
                uiController.drawObject(b);
            }
        }

        ((ShopButton)buttons[2]).addBuyButtonsToDrawingPanel();

        knebi = new Knebi(1250,0,this);
        uiController.drawObject(knebi);

    }

    public void setKnebiVisible(boolean a){
        knebi.setVisible(a);
    }

    public SQLHandler getSQLhandler(){
        return sqlHandler;
    }

    public void setAnimalsKlickable(boolean a){
        for(int i = 0;i < freeAnimals.length; i++){
            if(freeAnimals[i]!=null)freeAnimals[i].setKlickable(a);
        }
    }

    public void setInventoryVisible(boolean a){
        inventory.setVisibility(a);
    }

    public void setBarnsKlickable(boolean a){
        for (int i = 0; i < grass.length; i++) {
            for (int e = 0; e < grass[0].length; e++) {
                if(grass[i][e].getCoveringObject()instanceof Barn){
                    ((Barn) grass[i][e].getCoveringObject()).setKlickable(a);
                }
            }
        }
    }

    public void putAnimalIntoBarn(Animal a){
        int barnCounter = 0;
        Barn[] alotBarns = new Barn[100];
        boolean done = false;
        for (int i = 0; i < grass.length; i++) {
            for (int e = 0; e < grass[0].length; e++) {
                if(grass[i][e].getCoveringObject()instanceof Barn){
                    alotBarns[barnCounter] = (Barn)grass[i][e].getCoveringObject();
                    barnCounter = barnCounter + 1;
                }
            }
        }
        boolean foundPlace = false;
        for(int j = 0;j<alotBarns.length&&!done;j++){
            if(alotBarns[j]!=null){
                if(alotBarns[j].getArt().equals(a.getArt())){
                    for(int k = 0; k < alotBarns[j].getAnimals().length&&!foundPlace;k++){
                        if(alotBarns[j].getAnimals()[k]==null){
                            foundPlace = true;
                            done = true;

                        }
                    }
                    if(foundPlace) {
                        for (int i = 0; i < freeAnimals.length; i++) {
                            if (freeAnimals[i] == a) {
                                freeAnimals[i] = null;
                            }
                        }
                        uiController.removeObject(a);
                        alotBarns[j].addAnimal(a);
                        setAnimalsKlickable(false);
                        deactivateButton();
                        sqlHandler.updateAnimalBarn(a.getDatabaseID(), alotBarns[j]);
                    }
                }
            }
        }
    }


    public void removePlant(Plant plant){
        uiController.removeObject(plant);
        for (int i = 0; i < grass.length; i++) {
            for (int e = 0; e < grass[0].length; e++) {
               if(grass[i][e].getx()==plant.getx()&&grass[i][e].gety()==plant.gety()){
                   grass[i][e].setCoveringObject(null);
                   sqlHandler.removePlantFromDatabase(i+1,e);
               }
            }
        }
    }

    public void removeBarn(Barn barn){
        uiController.removeObject(barn);
        sqlHandler.removeBarn(barn);
        for (int i = 0; i < grass.length; i++) {
            for (int e = 0; e < grass[0].length; e++) {
                if(grass[i][e].getx()==barn.getX()&&grass[i][e].gety()==barn.getY()){
                    grass[i][e].setCoveringObject(null);
                }
            }
        }
    }




    public void build(int index, int price, int woodprice, boolean tree){
    addCash(-price);
    addWood(-woodprice);
    if(tree) {
        for (int i = 0; i < grass.length; i++) {
            for (int e = 0; e < grass[0].length; e++) {
                if(grass[i][e].getTreePlantable())grass[i][e].setKlickable(true, index);
            }
        }
    }else if(!tree) {
        for (int i = 0; i < grass.length; i++) {
            for (int e = 0; e < grass[0].length; e++) {
                if(!grass[i][e].getTreePlantable())grass[i][e].setKlickable(true, index);
            }
        }
    }




    }

    public void addBuilding(Grass g, int index){

        switch(index) {
            case 1:
                if(g.plant(new Tree(g.getx(),g.gety(),"T",this,50))) {
                    uiController.drawObject(g.getCoveringObject());
                    sqlHandler.addTree(g.gety()/50,g.getx()/50,"T",false,50);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 2:
                if(g.plant(new Tree(g.getx(),g.gety(),"B",this,50))) {
                    uiController.drawObject(g.getCoveringObject());
                    sqlHandler.addTree(g.gety()/50,g.getx()/50,"B",false,50);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 3:
                if(g.plant(new Plant("wheat",g.getx(),g.gety(),this,25,sqlHandler))) {
                    uiController.drawObject(g.getCoveringObject());
                    sqlHandler.addPlant("wheat",g.gety()/50,g.getx()/50);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 4:
                if(g.plant(new Plant("carrot",g.getx(),g.gety(),this,25,sqlHandler))) {
                    uiController.drawObject(g.getCoveringObject());
                    sqlHandler.addPlant("carrot",g.gety()/50,g.getx()/50);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 5:
                if(g.plant(new Plant("cherry",g.getx(),g.gety(),this,25,sqlHandler))) {
                    uiController.drawObject(g.getCoveringObject());
                    sqlHandler.addPlant("cherry",g.gety()/50,g.getx()/50);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 6:

                Barn a = new Barn("wurm", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(a)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "wurm");
                    uiController.drawObject(a);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 7:
                Barn b = new Barn("eichhoernchen", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(b)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "eichhoernchen");
                    uiController.drawObject(b);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 8:
                Barn c = new Barn("fuchs", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(c)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "fuchs");
                    uiController.drawObject(c);
                    redrawShop();
                    redrawInventory();

                }
                break;
            case 9:
                Barn d = new Barn("hase", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(d)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "hase");
                    uiController.drawObject(d);
                    redrawShop();
                    redrawInventory();

                }                break;
            case 10:
                Barn e = new Barn("hirsch", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(e)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "hirsch");
                    uiController.drawObject(e);
                    redrawShop();
                    redrawInventory();

                }                break;
            case 11:
                Barn f = new Barn("schnecke", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(f)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "schnecke");
                    uiController.drawObject(f);
                    redrawShop();
                    redrawInventory();

                }                break;
            case 12:
                Barn h = new Barn("vogel", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(h)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "vogel");
                    uiController.drawObject(h);
                    redrawShop();
                    redrawInventory();

                }                break;
            case 13:
                Barn i = new Barn("wildschwein", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(i)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "wildschwein");
                    uiController.drawObject(i);
                    redrawShop();
                    redrawInventory();

                }                break;
            case 14:
                Barn j = new Barn("ziege", g.getx(),g.gety(),uiController,this,61,600);
                if(g.plant(j)) {
                    sqlHandler.addBarn(g.gety() / 50, g.getx() / 50, "ziege");
                    uiController.drawObject(j);
                    redrawShop();
                    redrawInventory();
                }                break;
        }
    }


    public void setBarnInfoVisibility(boolean a){

    }

    public void addWood(int amount){
        userInterface.addWood(amount);
        sqlHandler.updateWood();
    }

    public void addCash(int amount){
        userInterface.addCash(amount);
        sqlHandler.updateCash();
    }

    public void setShop(boolean b){
        shop.setVisible(b);
    }

    public void activateButton(MenuButton b){
        for(MenuButton m : buttons){
            if(m != null){
                m.setVisible(false);
            }
        }
        b.setActive(true);
        xButton.setVisible(true);
    }

    public void deactivateButton(){
        for(MenuButton b : buttons){
            if(b != null){
                b.setActive(false);
                b.setVisible(true);
            }
        }
        xButton.setVisible(false);
    }

    public boolean checkFreeAnimalPlaces(){
        for(int i = 0; i < freeAnimals.length;i++){
            if(freeAnimals[i] == null){
                return true;
            }
        }
        return false;
    }

    public void freeAnimal(Animal a, BarnInfo b){
        boolean setFree = false;
        for(int i = 0; i < freeAnimals.length && !setFree; i++){
            if(freeAnimals[i] == null){
                freeAnimals[i] = a;
                setFree = true;
                uiController.drawObject(a);
                redrawBarnInfo(b);
                sqlHandler.freeAnimal(a.getDatabaseID());
            }
        }
    }

    public void addAnimal(int index,int price){
        boolean finished = false;
        for(int i = 0; i < freeAnimals.length&&!finished; i++){
            if(freeAnimals[i]==null){
                finished = true;
                animalCount++;
                switch(index){
                    case 1:
                        freeAnimals[i] = new Worm(500,300,this,"wurm",sqlHandler.addAnimal("wurm",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 2:
                        freeAnimals[i] = new Eichhörnchen(500,300,this,"eichhoernchen",sqlHandler.addAnimal("eichhoernchen",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 3:
                        freeAnimals[i] = new Fox(500,300,this,"fuchs",sqlHandler.addAnimal("fuchs",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 4:
                        freeAnimals[i] = new Rabbit(500,300,this,"hase",sqlHandler.addAnimal("hase",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 5:
                        freeAnimals[i] = new Hirsch(500,300,this,"hirsch",sqlHandler.addAnimal("hirsch",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 6:
                        freeAnimals[i] = new Schnecke(500,300,this,"schnecke",sqlHandler.addAnimal("schnecke",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 7:
                        freeAnimals[i] = new Bird(500,300,this,"vogel",sqlHandler.addAnimal("vogel",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 8:
                        freeAnimals[i] = new WildPig(500,300,this,"wildschwein",sqlHandler.addAnimal("wildschwein",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                    case 9:
                        freeAnimals[i] = new Goat(500,300,this,"ziege",sqlHandler.addAnimal("ziege",null),uiController);
                        uiController.drawObject(freeAnimals[i]);

                        break;
                }
                if(price != 0) {
                    redrawShop();
                    sqlHandler.updateCash();
                    userInterface.addCash(-price);
                }
            }
        }
    }

    public void addAnimalFromDatabase(String type, int ID){
        boolean finished = false;
        for(int i = 0; i < freeAnimals.length&&!finished; i++){
            if(freeAnimals[i]==null){
                finished = true;
                animalCount++;
                switch(type){
                    case "wurm":
                        freeAnimals[i] = new Worm(500,300,this,"wurm",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "eichhoernchen":
                        freeAnimals[i] = new Eichhörnchen(500,300,this,"eichhoernchen",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "fuchs":
                        freeAnimals[i] = new Fox(500,300,this,"fuchs",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "hase":
                        freeAnimals[i] = new Rabbit(500,300,this,"hase",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "hirsch":
                        freeAnimals[i] = new Hirsch(500,300,this,"hirsch",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "schnecke":
                        freeAnimals[i] = new Schnecke(500,300,this,"schnecke",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "vogel":
                        freeAnimals[i] = new Bird(500,300,this,"vogel",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "wildschwein":
                        freeAnimals[i] = new WildPig(500,300,this,"wildschwein",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case "ziege":
                        freeAnimals[i] = new Goat(500,300,this,"ziege",ID,uiController);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                }
            }
        }
    }

    private void redrawShop(){
        uiController.removeObject(buttons[2]);
        uiController.drawObject(buttons[2]);
        ((ShopButton)buttons[2]).resetButtonsOnDrawingPanel();
    }

    public void redrawBarnInfo(BarnInfo barnInfo){
        uiController.removeObject(barnInfo);
        uiController.drawObject(barnInfo);
    }
    public void redrawInventory(){
        uiController.removeObject(inventory);
        uiController.drawObject(inventory);
    }

    /**
     * Diese Methode wird wiederholt automatisch aufgerufen und zwar für jede Frame einmal, d.h. über 25 mal pro Sekunde.
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt){
        // Hier passiert noch nichts, das Programm läuft friedlich vor sich hin
    }

    public void removeTree(Tree t){
        boolean treeRemoved = false;
        for(int i = 0; i < grass.length && !treeRemoved; i++){
            for(int j = 0; j < grass[i].length && !treeRemoved; j++){
                if(grass[i][j].getCoveringObject() == t){
                    treeRemoved = true;
                    grass[i][j].setCoveringObject(null);
                    uiController.removeObject(t);
                    sqlHandler.removeTreeFromDatabase(i,j);
                }
            }
        }
    }
    public void removeKlickableGrass(){
        for(int i = 0; i < grass.length; i++){
            for(int e = 0; e < grass[0].length; e++){
                grass[i][e].setKlickable(false, -1);
            }
        }
    }
    public int getWood(){
        return userInterface.getWood();
    }

    public int getCash(){
        return userInterface.getCash();
    }

    public int getHarmony(){
        return userInterface.getHarmony();
    }

    public SQLHandler getSqlHandler() {
        return sqlHandler;
    }

    public UIController getUiController() {
        return uiController;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(int animalCount) {
        this.animalCount = animalCount;
    }
}
