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
        startGame();
    }

    public void startGame(){
        userInterface = new UserInterface(0,0);
        uiController.drawObject(userInterface);
        grass = new Grass[13][28];
        tree = new Tree[13][28];
        barns = new Barn[13][28];
        mapBuilder = new MapBuilder(grass,tree,uiController,this);
        sqlHandler = new SQLHandler(mapBuilder, userInterface);
        sqlHandler.handleSQL();
        cat = new Cat(200,500);
        uiController.drawObject(cat);
        xButton = new XButton(1050,5,this);
        uiController.drawObject(xButton);
        uiController.drawObject(shop);

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

    public void addBarn(Grass g, int index){

        System.out.println(g.getx()/50);
        switch(index) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                sqlHandler.addBarn(g.gety()/50,g.getx()/50,"wurm");
                Barn b = new Barn("wurm", g.getx(),g.gety());
                g.setCoveringObject(b);
                uiController.drawObject(b);
                break;
            case 7:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"eichhoernchen");
                break;
            case 8:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"fuchs");
                break;
            case 9:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"a");
                break;
            case 10:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"a");
                break;
            case 11:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"a");
                break;
            case 12:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"a");
                break;
            case 13:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"a");
                break;
            case 14:
                sqlHandler.addBarn(g.getx()/50,g.gety()/50,"a");
                break;
        }
    }

    public void addWood(int amount){
        userInterface.addWood(amount);
        sqlHandler.updateWood();
    }

    public void addCash(int amount){
        userInterface.addCash(amount);
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

    public void addAnimal(int index,int price){
        boolean finished = false;
        for(int i = 0; i < freeAnimals.length&&!finished; i++){
            if(freeAnimals[i]==null){
                finished = true;
                userInterface.addCash(-price);
                switch(index){
                    case 1:
                        freeAnimals[i] = new Worm(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        sqlHandler.addAnimal("wurm", null);
                        break;
                    case 2:
                        freeAnimals[i] = new Eichhörnchen(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 3:
                        freeAnimals[i] = new Fox(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 4:
                        freeAnimals[i] = new Rabbit(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 5:
                        freeAnimals[i] = new Hirsch(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 6:
                        freeAnimals[i] = new Schnecke(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 7:
                        freeAnimals[i] = new Bird(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 8:
                        freeAnimals[i] = new WildPig(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 9:
                        freeAnimals[i] = new Goat(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                }
                uiController.removeObject(buttons[2]);
                uiController.drawObject(buttons[2]);
                ((ShopButton)buttons[2]).resetButtonsOnDrawingPanel();
                sqlHandler.updateCash();
            }
        }
    }

    public void addAnimalFromDatabase(int index){
        boolean finished = false;
        for(int i = 0; i < freeAnimals.length&&!finished; i++) {
            if (freeAnimals[i] == null) {
                finished = true;
                switch(index){
                    case 1:
                        freeAnimals[i] = new Worm(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 2:
                        freeAnimals[i] = new Eichhörnchen(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 3:
                        freeAnimals[i] = new Fox(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 4:
                        freeAnimals[i] = new Rabbit(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 5:
                        freeAnimals[i] = new Hirsch(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 6:
                        freeAnimals[i] = new Schnecke(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 7:
                        freeAnimals[i] = new Bird(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 8:
                        freeAnimals[i] = new WildPig(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                    case 9:
                        freeAnimals[i] = new Goat(500,300);
                        uiController.drawObject(freeAnimals[i]);
                        break;
                }
            }
        }
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
}
