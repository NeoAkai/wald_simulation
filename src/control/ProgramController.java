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
    private Cat cat;
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
    private SQLHandler sqlCreator;
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
        mapBuilder = new MapBuilder(grass,tree,uiController,this);
        sqlCreator = new SQLHandler(mapBuilder);
        sqlCreator.handleSQL();
        cat = new Cat(200,500);
        uiController.drawObject(cat);
        bird = new Bird(300,400);
        uiController.drawObject(bird);
        eichhörnchen = new Eichhörnchen(700,500);
        uiController.drawObject(eichhörnchen);
        fox = new Fox(473,507);
        uiController.drawObject(fox);
        goat = new Goat(1000,395);
        uiController.drawObject(goat);
        hirsch = new Hirsch(933,698);
        uiController.drawObject(hirsch);
        rabbit = new Rabbit(655,555);
        uiController.drawObject(rabbit);
        schnecke = new Schnecke(823,222);
        uiController.drawObject(schnecke);
        wildPig = new WildPig(539,111);
        uiController.drawObject(wildPig);
        worm = new Worm(666,283);
        uiController.drawObject(worm);
        xButton = new XButton(1050,5,this);
        uiController.drawObject(xButton);
        shop = new shop(50,50);
        uiController.drawObject(shop);


        buttons = new MenuButton[5];
        buttons[0] = new AxeButton(1000,5,grass,this);
        buttons[1] = new InventoryButton(950,5,this);
        buttons[2] = new ShopButton(900,5,this);

        for(MenuButton b : buttons){
            if(b != null){
                uiController.drawObject(b);
            }
        }

        ((ShopButton)buttons[2]).addBuyButtonsToDrawingPanel();

    }


    public void addWood(int amount){
        userInterface.addWood(amount);
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
                    sqlCreator.removeTreeFromDatabase(i,j);
                }
            }
        }
    }

    public SQLHandler getSqlCreator() {
        return sqlCreator;
    }

    public UIController getUiController() {
        return uiController;
    }
}
